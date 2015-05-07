package com.github.elfin8er.PhoenixRevival.Commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.github.elfin8er.PhoenixRevival.Phoenix;

public class CommandSendMail implements CommandExecutor{

	private Phoenix plugin;
	
	public CommandSendMail(Phoenix phoenix) {
        this.plugin = plugin;
	}

	public CommandResult execute(CommandSource sender, CommandContext arguments) throws CommandException {
		Player recipient = plugin.game.getServer().getPlayer(arguments.getOne("player").get().toString()).get();
		String message = (String) arguments.getOne("msg").orNull();
		
		//checks to see if message is null
        if (message == null || message == "") {
            return CommandResult.empty();
        }
        
        String mail = sender.getName() + ": " + message;
        this.plugin.sendMail((Player) sender, recipient, mail);
		return CommandResult.success();
	}
}
