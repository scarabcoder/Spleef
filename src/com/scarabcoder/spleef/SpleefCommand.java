package com.scarabcoder.spleef;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.GameManager;
import com.scarabcoder.gameapi.manager.PlayerManager;

import net.md_5.bungee.api.ChatColor;

public class SpleefCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player){
			GamePlayer player = PlayerManager.getGamePlayer((Player)sender);
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("join")){
					Game game = GameManager.getGame(args[1]);
					if(game != null){
						if(game.getRegisteringPlugin().equals(Spleef.getPlugin())){
							game.addPlayer(player);
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Game not found!");
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "Invalid argument usage.");
			}
		}else{
			sender.sendMessage(ChatColor.RED + "Player-only command.");
		}
		return true;
	}
	
}
