package com.github.elfin8er.PhoenixRevival.Commands;

import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.github.elfin8er.PhoenixRevival.Phoenix;

public class CommandAuctionStart implements CommandExecutor {

	Phoenix plugin;
	
	public CommandAuctionStart(Phoenix phoenix) {
		this.plugin = phoenix;
	}

	@Override
	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {
		int quantity = Integer.parseInt(arguments.getOne("quantity").get().toString());
		int amt = Integer.parseInt(arguments.getOne("amt").get().toString()); //Starting bid
		
		return null;
	}

}
