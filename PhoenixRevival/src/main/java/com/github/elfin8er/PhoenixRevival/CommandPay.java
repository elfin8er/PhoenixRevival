package com.github.elfin8er.PhoenixRevival;

import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class CommandPay implements CommandExecutor {

	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {

		//Argument 1
		String player = (String) arguments.getOne("player").get();
		
		//Argument 2
		int amount = (Integer) arguments.getOne("amount").get();
		
		return CommandResult.success();
	}

}