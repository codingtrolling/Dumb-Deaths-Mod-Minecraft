package com.example;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumbDeaths implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("dumbdeaths");

    @Override
    public void onInitialize() {
        LOGGER.info("Dumb Death Messages initialized! Prepare to be Creepered.");
    }
}
