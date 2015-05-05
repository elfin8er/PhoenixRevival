package com.github.elfin8er.PhoenixRevival;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class CommandMoney implements CommandExecutor {

	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			player.sendMessage(Texts.builder("Money: ₱100").build());
			
		}
		
		return CommandResult.success();
	}

}

