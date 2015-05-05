package com.github.elfin8er.PhoenixRevival;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.github.elfin8er.PhoenixRevival.Commands.CommandGiveMoney;
import com.github.elfin8er.PhoenixRevival.Commands.CommandMoney;
import com.github.elfin8er.PhoenixRevival.Commands.CommandTakeMoney;
import com.google.inject.Inject;

@Plugin(name = "PhoenixRevival", id = "PhoenixRevival", version = "ALPHA")
public class Phoenix {
	
	@Inject
	Game game;
	
	@Inject
	Logger logger;
	
	HashMap<UUID, PhoenixPlayer> players = new HashMap<UUID, PhoenixPlayer>();
	
	@Subscribe
	public void onPreInitialization(PreInitializationEvent event){
		
		//build commands
        HashMap<List<String>, CommandSpec> subcommands = new HashMap<>();
        
        //give money
        subcommands.put(Arrays.asList("give", "donate", "pay"), CommandSpec.builder()
                .setPermission("phoenix.givemoney")
                .setDescription(Texts.of("Give money"))
                .setExtendedDescription(Texts.of("This will give the set amount to the player"))
                .setArguments(GenericArguments.seq(
                        GenericArguments.string(Texts.of("player")), // "string(...)" instead of "player(...)" to support offline players
                        GenericArguments.remainingJoinedStrings(Texts.of("amt"))))
                .setExecutor(new CommandGiveMoney(this))
                .build());
        
        //take money (admin only)
        subcommands.put(Arrays.asList("take"), CommandSpec.builder()
                .setPermission("phoenix.takemoney")
                .setDescription(Texts.of("Take money"))
                .setExtendedDescription(Texts.of("This will take from the player's account, admin use only"))
                .setArguments(GenericArguments.seq(
                        GenericArguments.string(Texts.of("player")), // "string(...)" instead of "player(...)" to support offline players
                        GenericArguments.remainingJoinedStrings(Texts.of("amt"))))
                .setExecutor(new CommandTakeMoney(this))
                .build());
        
        //check balance        
		CommandSpec moneyCommand = CommandSpec.builder()
			.setDescription(Texts.of("Displays how much money you currently have"))
			.setPermission("phoenix.balance")
			.setChildren(subcommands) // register subcommands
			//.setArguments(GenericArguments.optional(null))
			//.setExecutor(new CommandMoney(this))
			.build();
			
		game.getCommandDispatcher().register(this, moneyCommand, "money", "m");		
	}
	
	public void giveMoney(String recipient, Player sender, double amount){
		players.get(recipient).setMoney(+amount);
		players.get(sender).setMoney(-amount);
	}
	
	public void takeMoney(String target, Player taker, double amount){
		players.get(taker).setMoney(+amount);
		players.get(target).setMoney(-amount);
		
	}
	
	public void checkMoney(Player sender){
		sender.sendMessage(Texts.of("Current balance : " + players.get(sender.getUniqueId()).getMoney() + SETTINGS.CurrencySymbol));
	}
	
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		players.put(event.getPlayer().getUniqueId(), new PhoenixPlayer());
	}

}