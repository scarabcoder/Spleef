package com.scarabcoder.spleef;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.game.Area;
import com.scarabcoder.gameapi.game.Arena;
import com.scarabcoder.gameapi.game.ArenaSettings;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.game.GameSettings;
import com.scarabcoder.gameapi.manager.GameManager;

import net.md_5.bungee.api.ChatColor;

public class Spleef extends JavaPlugin {
	
	private static Plugin plugin;

	@Override
	public void onEnable(){
		
		Arena arena = new Arena("Spleef");
		
		Game spleefGame = new Game("Spleef", arena, GameStatus.WAITING, this);
		arena.setLobbySpawn(new Location(arena.getWorld(), 1199.5d, 24, 219.5d));
		arena.setSpectatorSpawn(new Location(arena.getWorld(), 1198, 37, 219));
		GameSettings settings = spleefGame.getGameSettings();
		settings.setMOTD(true);
		settings.shouldSetListPlayerCount(true);
		settings.setLobbyLocation(new Location(Bukkit.getWorld("world"), 267, 77, 67));
		settings.setUsesBungee(false);
		settings.setCountdownTime(15);
		settings.setMinimumPlayers(1);
		settings.setMaximumPlayers(8);
		settings.setAutomaticCountdown(true);
		settings.setUsesBungee(true);
		settings.setLobbyServer("hub");
		settings.shouldLeavePlayerOnDisconnect(true);
		
		
		ArenaSettings arenaSettings = spleefGame.getArena().getArenaSettings();
		arenaSettings.setAllowChestAccess(false);
		arenaSettings.setCanBuild(false);
		arenaSettings.setCanDestroy(false);
		arenaSettings.setCanPvP(false);
		arenaSettings.setAllowPlayerInvincibility(true);
		arenaSettings.setAllowFireSpread(false);
		arenaSettings.setAllowDurabilityChange(false);
		arenaSettings.setKeepInventory(true);
		arenaSettings.setAllowFoodLevelChange(false);
		arenaSettings.setAllowMobSpawn(false);
		arenaSettings.setAllowBlockDrop(false);
		
		spleefGame.addSpawn(new Location(arena.getWorld(), 1199.5, 24, 208.5));
		spleefGame.addSpawn(new Location(arena.getWorld(), 1199.5, 24, 231.5));
		
		Area area = new Area(new Location(arena.getWorld(), 1213.5d, 30, 205.5d), new Location(arena.getWorld(), 1185.5d, 21, 233.5d), "gameArea");
		spleefGame.registerArea(area);
		
		spleefGame.setMessagePrefix(ChatColor.WHITE + "[" + ChatColor.AQUA + "Spleef" + ChatColor.WHITE + "]" + ChatColor.RESET);
		
		
		
		GameManager.registerGame(spleefGame);
		
		plugin = this;
		
		Bukkit.getPluginCommand("spleef").setExecutor(new SpleefCommand());
		
		Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
		
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
	
	
}
