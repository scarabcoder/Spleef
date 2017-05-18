package com.scarabcoder.spleef;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.event.GameStartEvent;
import com.scarabcoder.gameapi.event.PlayerJoinGameEvent;
import com.scarabcoder.gameapi.event.PlayerLeaveAreaEvent;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.game.GamePlayer;

import net.md_5.bungee.api.ChatColor;

public class GameListeners implements Listener {
	
	@EventHandler
	public void onPlayerJoinGame(PlayerJoinGameEvent e){
		if(e.getGame().getRegisteringPlugin().equals(Spleef.getPlugin())){
			e.getGame().sendMessage(e.getPlayer().getOnlinePlayer().getDisplayName() + " joined the game!");
			Player player = e.getPlayer().getOnlinePlayer();
			player.teleport(e.getGame().getArena().getLobbySpawn());
			
		}
	}
	
	@EventHandler
	public void onGameStart(GameStartEvent e){
		if(e.getGame().getRegisteringPlugin().equals(Spleef.getPlugin())){
			e.getGame().getArena().getArenaSettings().setCanDestroy(true);
		}
	}
	
	
	@EventHandler
	public void onLeaveArea(PlayerLeaveAreaEvent e){
		if(e.getPlayer().getGame().getGameStatus().equals(GameStatus.INGAME)){
			if(e.getPlayer().getGame().getRegisteringPlugin().equals(Spleef.getPlugin())){
				if(e.getArea().getName().equals("gameArea")){
					if(!e.getPlayer().getGame().getGamePlayerType(e.getPlayer()).equals(GamePlayerType.SPECTATOR)){
						Game game = e.getPlayer().getGame();
						game.setPlayerMode(GamePlayerType.SPECTATOR, e.getPlayer());
						e.getPlayer().getOnlinePlayer().teleport(game.getArena().getSpectatorSpawn());
						e.getPlayer().getOnlinePlayer().getLocation().getWorld().strikeLightning(e.getPlayer().getOnlinePlayer().getLocation());
						game.sendMessage(ChatColor.GREEN + e.getPlayer().getPlayer().getName() + " " + ChatColor.RED + "died!");
						if(game.getGamePlayerByMode(GamePlayerType.PLAYER).size() <= 1){
							if(game.getGamePlayerByMode(GamePlayerType.PLAYER).size() == 1){
								GamePlayer winner = game.getGamePlayerByMode(GamePlayerType.PLAYER).get(0);
								game.sendMessage(ChatColor.GREEN + winner.getPlayer().getName() + " wins the game!");
								game.sendMessage(ChatColor.GREEN + "Game restarts in 10 seconds.");
								BukkitRunnable runnable = new BukkitRunnable(){
									
									private int count = 20;
									
									@Override
									public void run() {
										
										count--;
										if(count != 0){
											if(winner.isOnline()){
												Firework f = (Firework) winner.getOnlinePlayer().getWorld().spawnEntity(winner.getOnlinePlayer().getEyeLocation(), EntityType.FIREWORK);
												FireworkMeta m = f.getFireworkMeta();
												m.setPower(2);
												Random r = new Random();
									            List<Color> colors = (List<Color>) Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, 
									            		Color.GREEN, Color.LIME, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, 
									            		Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.YELLOW);
												FireworkEffect e = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(colors.get(r.nextInt(colors.size()))).with(Type.values()[r.nextInt(Type.values().length)]).build();
												m.addEffect(e);
												f.setFireworkMeta(m);
											}
										}else{
											game.endGame();
											this.cancel();
										}
									}
									
								};
								runnable.runTaskTimer(Spleef.getPlugin(), 0, 10);
								
							}else{
								game.endGame();
							}
						}
					}
				}
			}
		}
	}
	
}
