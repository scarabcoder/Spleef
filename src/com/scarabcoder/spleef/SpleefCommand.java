package com.scarabcoder.spleef;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;

import net.md_5.bungee.api.ChatColor;

public class SpleefCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player){
			GamePlayer player = PlayerManager.getGamePlayer((Player)sender);
			if(!player.isInGame()){
				
			}else{
				player.getOnlinePlayer().sendMessage(ChatColor.RED + "You must leave your current game before joining a new one!");
			}
		}else{
			sender.sendMessage(ChatColor.RED + "Player-only command.");
		}
		return true;
	}
	
}
