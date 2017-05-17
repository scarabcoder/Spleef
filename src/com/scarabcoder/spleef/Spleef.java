package com.scarabcoder.spleef;

import org.bukkit.plugin.java.JavaPlugin;

import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.game.Arena;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.manager.GameManager;

public class Spleef extends JavaPlugin {

	@Override
	public void onEnable(){
		
		Arena arena = new Arena("Spleef");
		
		Game spleefGame = new Game("Spleef", arena, GameStatus.WAITING);
		
		
		GameManager.registerGame(spleefGame, this);
		
	}
	
	
	
}
