package com.scarabcoder.spleef;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.event.GameStartEvent;
import com.scarabcoder.gameapi.event.PlayerJoinGameEvent;
import com.scarabcoder.gameapi.event.PlayerLeaveAreaEvent;
import com.scarabcoder.gameapi.game.Game;

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
	public void onBlockBreak(BlockBreakEvent e){
		
	}
	
	@EventHandler
	public void onLeaveArea(PlayerLeaveAreaEvent e){
		if(e.getPlayer().getGame().getRegisteringPlugin().equals(Spleef.getPlugin())){
			if(e.getArea().getName().equals("gameArea")){
				if(!e.getPlayer().getGame().getGamePlayerType(e.getPlayer()).equals(GamePlayerType.SPECTATOR)){
					Game game = e.getPlayer().getGame();
					game.setPlayerMode(GamePlayerType.SPECTATOR, e.getPlayer());
					e.getPlayer().getOnlinePlayer().teleport(game.getArena().getSpectatorSpawn());
					e.getPlayer().getOnlinePlayer().getLocation().getWorld().strikeLightning(e.getPlayer().getOnlinePlayer().getLocation());
					game.sendMessage(ChatColor.GREEN + e.getPlayer().getPlayer().getName() + " " + ChatColor.RED + "died!");
					if(game.getGamePlayerByMode(GamePlayerType.PLAYER).size() == 1){
						
					}
				}
			}
		}
	}
	
}
