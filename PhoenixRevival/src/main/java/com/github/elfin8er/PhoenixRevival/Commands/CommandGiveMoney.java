package com.github.elfin8er.PhoenixRevival.Commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.github.elfin8er.PhoenixRevival.Phoenix;
import com.github.elfin8er.PhoenixRevival.SETTINGS;

public class CommandGiveMoney implements CommandExecutor {

	private Phoenix plugin;
	
	public CommandGiveMoney(Phoenix plugin) {
        this.plugin = plugin;
	}

	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {

		String recipient = (String) arguments.getOne("player").orNull();
		double amount = Double.valueOf((String) arguments.getOne("amt").orNull());
		
		//checks to make sure it's not negative
		if (amount <= 0){
			return CommandResult.empty();
		}
		
		//gives money
		this.plugin.giveMoney(recipient,(Player) sender, amount);
		
		//tells taker that the money has been taken
		sender.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " sent to " + recipient + "."));
		
		return CommandResult.success();
	}

}