package com.github.elfin8er.PhoenixRevival;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.google.inject.Inject;

@Plugin(name = "PhoenixRevival", id = "PhoenixRevival", version = "ALPHA")
public class Phoenix {
	
	@Inject
	Game game;
	
	@Inject
	Logger logger;
	
	HashMap<UUID, PhoenixPlayer> players = new HashMap<UUID, PhoenixPlayer>();
	
	@Subscribe
	public void onServerInitialization(InitializationEvent event){
		CommandSpec moneyCommand = CommandSpec.builder()
			.setDescription(Texts.of("Displays how much money you currently have"))
			.setExecutor(new CommandMoney())
			.build();
			
		System.out.println(SETTINGS.StartingMoney);
		game.getCommandDispatcher().register(this, moneyCommand, "money", "m");
		
	}
	
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		players.put(event.getPlayer().getUniqueId(), new PhoenixPlayer());
	}

}
