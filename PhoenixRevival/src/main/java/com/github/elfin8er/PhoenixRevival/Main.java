package com.github.elfin8er.PhoenixRevival;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
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
	
	CommandSpec basicCommand = CommandSpec.builder()
			.setDescription(Texts.of("Short Command Description"))
			.setExecutor(new BlahCommand())
			.build();

}
