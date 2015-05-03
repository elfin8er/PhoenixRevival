package com.github.elfin8er.PhoenixRevival;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.google.inject.Inject;

@Plugin(name = "PhoenixRevival", id = "PhoenixRevival", version = "ALPHA")
public class Main {
	
	@Inject
	Game game;
	
	@Inject
	Logger logger;
	
	@Subscribe
	public void onServerInitialization(InitializationEvent event){
		CommandSpec basicCommand = CommandSpec.builder()
			.setDescription(Texts.of("Short Command Description"))
			.setExecutor(new BlahCommand())
			.build();
		
		game.getCommandDispatcher().register(this, basicCommand, "command");
		
	}
	

}
