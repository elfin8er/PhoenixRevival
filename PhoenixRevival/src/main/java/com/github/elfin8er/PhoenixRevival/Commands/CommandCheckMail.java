package com.github.elfin8er.PhoenixRevival.Commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.github.elfin8er.PhoenixRevival.Phoenix;

public class CommandCheckMail implements CommandExecutor{

	private Phoenix plugin;
	
	public CommandCheckMail(Phoenix phoenix) {
        this.plugin = plugin;
	}

	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {
        this.plugin.checkMail((Player) sender);
		return CommandResult.success();
	}
}
