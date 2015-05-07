package com.github.elfin8er.PhoenixRevival;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.github.elfin8er.PhoenixRevival.Commands.CommandAuctionStart;
import com.github.elfin8er.PhoenixRevival.Commands.CommandCheckMail;
import com.github.elfin8er.PhoenixRevival.Commands.CommandGiveMoney;
import com.github.elfin8er.PhoenixRevival.Commands.CommandMoney;
import com.github.elfin8er.PhoenixRevival.Commands.CommandSendMail;
import com.github.elfin8er.PhoenixRevival.Commands.CommandTakeMoney;
import com.google.inject.Inject;

@Plugin(name = "PhoenixRevival", id = "PhoenixRevival", version = "ALPHA")
public class Phoenix {
	
	@Inject
	public Game game;
	
	@Inject
	public Logger logger;
	
	HashMap<UUID, PhoenixPlayer> players = new HashMap<UUID, PhoenixPlayer>();
	ArrayList<PhoenixAuction> auctions = new ArrayList<PhoenixAuction>();
	
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
                        GenericArguments.integer(Texts.of("amt"))))
                .setExecutor(new CommandGiveMoney(this))
                .build());
        
        //take money (admin only)
        subcommands.put(Arrays.asList("take"), CommandSpec.builder()
                .setPermission("phoenix.takemoney")
                .setDescription(Texts.of("Take money"))
                .setExtendedDescription(Texts.of("This will take from the player's account, admin use only"))
                .setArguments(GenericArguments.seq(
                        GenericArguments.string(Texts.of("player")), // "string(...)" instead of "player(...)" to support offline players
                        GenericArguments.integer(Texts.of("amt"))))
                .setExecutor(new CommandTakeMoney(this))
                .build());
        
        //check balance    
        subcommands.put(Arrays.asList("balance"), CommandSpec.builder()
                .setPermission("phoenix.balance")
                .setDescription(Texts.of("Balance"))
                .setExtendedDescription(Texts.of("This will return how much money you have"))
                .setExecutor(new CommandMoney(this))
                .build());
        
        
		CommandSpec moneyCommand = CommandSpec.builder()
			.setDescription(Texts.of("Displays how much money you currently have"))
			.setChildren(subcommands) // register subcommands
			.build();
		
		subcommands.clear();
		
        //send mail
        subcommands.put(Arrays.asList("send"), CommandSpec.builder()
                .setPermission("phoenix.mailSend")
                .setDescription(Texts.of("Send mail"))
                .setExtendedDescription(Texts.of("Send a message to a player"))
                .setArguments(GenericArguments.seq(
                        GenericArguments.string(Texts.of("player")), // "string(...)" instead of "player(...)" to support offline players
                        GenericArguments.remainingJoinedStrings(Texts.of("msg"))))
                .setExecutor(new CommandSendMail(this))
                .build());
        
        //check mail
        subcommands.put(Arrays.asList("read", "check"), CommandSpec.builder()
                .setPermission("phoenix.mailCheck")
                .setDescription(Texts.of("Check mail"))
                .setExtendedDescription(Texts.of("Check received messages"))
                .setExecutor(new CommandCheckMail(this))
                .build());
        
        
		CommandSpec mailCommand = CommandSpec.builder()
			.setDescription(Texts.of("Displays how much money you currently have"))
			.setPermission("phoenix.balance")
			.setChildren(subcommands) // register subcommands
			.build();
		
		subcommands.clear();
		
		//check starts an auction    
        subcommands.put(Arrays.asList("start", "s"), CommandSpec.builder()
                .setPermission("phoenix.balance")
                .setDescription(Texts.of("Balance"))
                .setExtendedDescription(Texts.of("Initiates an auction"))
                .setExecutor(new CommandAuctionStart(this))
                .setArguments(GenericArguments.seq(
						GenericArguments.integer(Texts.of("quantity")),
						GenericArguments.integer(Texts.of("amt"))
						))
                .build());
		
        /*
         * COMMAND USAGE
         * /auction start <quantity> <starting_amount> - Simplest way to create an auction. Auctions the item in the players hand.
         */
		CommandSpec auctionCommand = CommandSpec.builder()
				.setDescription(Texts.of("Auction command"))
				.setPermission("phoenix.auction")
				.setChildren(subcommands)
				.build();
			
		game.getCommandDispatcher().register(this, moneyCommand, "money", "m");
		game.getCommandDispatcher().register(this, mailCommand, "mail", "post");
		game.getCommandDispatcher().register(this, auctionCommand, "auction", "auc", "a");
	}
	
	public void giveMoney(Player recipient, Player sender, double amount){
		//TODO - Make sure that the play has enough money
		players.get(recipient.getUniqueId()).changeMoney(amount);
		players.get(sender.getUniqueId()).changeMoney(-amount);
		
		//tells players about the change
		sender.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " sent to " + recipient.getName() + "."));
		sender.sendMessage(Texts.of("Your new balance is " + players.get(sender.getUniqueId()).getMoney()));
		recipient.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " received from " + sender.getName() + "."));
		recipient.sendMessage(Texts.of("Your new balance is " + players.get(recipient.getUniqueId()).getMoney()));
	}
	
	public void takeMoney(Player target, Player taker, double amount){
		//TODO - Make sure that the play has enough money
		players.get(taker.getUniqueId()).changeMoney(amount);
		players.get(target.getUniqueId()).changeMoney(-amount);
		
		//tells players about the change
		taker.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " Taken from " + target.getName() + "."));
		taker.sendMessage(Texts.of("Your new balance is " + players.get(taker.getUniqueId()).getMoney()));
		target.sendMessage(Texts.of("Amount of " + amount + SETTINGS.CurrencySymbol +  " Taken by " + taker.getName() + "."));
		target.sendMessage(Texts.of("Your new balance is " + players.get(target.getUniqueId()).getMoney()));
	}
	
	public void checkMoney(Player sender){
		sender.sendMessage(Texts.of("Current balance : " + players.get(sender.getUniqueId()).getMoney() + SETTINGS.CurrencySymbol));
	}
	
	public void sendMail(Player sender, Player recipient, String mail){
		players.get(recipient).addMail(mail);
		players.get(sender).changeMoney(-SETTINGS.costPerMail);
		sender.sendMessage(Texts.of("Message sent to " + recipient.getName() + ", you have been charged " + SETTINGS.costPerMail + SETTINGS.CurrencySymbol));
	}
	
	public void checkMail(Player sender){
		List<String> mails = players.get(sender).getmail();
		
		if (mails.isEmpty()){
		sender.sendMessage(Texts.of("No mail"));
		} else{
			for (String mail : mails){
				sender.sendMessage(Texts.of(mail));
			}
		}
	}
	
	public void clearMail(){
		
	}
	
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		players.put(event.getPlayer().getUniqueId(), new PhoenixPlayer());
	}

}