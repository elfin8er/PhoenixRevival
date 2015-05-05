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

public class CommandTakeMoney implements CommandExecutor {

	private Phoenix plugin;
	
	public CommandTakeMoney(Phoenix plugin) {
        this.plugin = plugin;
	}
	
	public CommandResult execute(CommandSource taker, CommandContext arguments) throws CommandException {
		
		Player target = (Player) arguments.getOne("player").orNull();
		double amount = Double.parseDouble(arguments.getOne("amt").get().toString());
		
		//checks to make sure it's not negative
		if (amount <= 0){
			return CommandResult.empty();
		}
		
		//takes money
		this.plugin.takeMoney(target, (Player) taker, amount);
		
		//tells sender that the money has been sent
		taker.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " Taken from " + target.getName() + "."));

		return CommandResult.success();
	}
}
