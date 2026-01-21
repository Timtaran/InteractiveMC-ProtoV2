/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 *
 * Copyright (c) 2026 timtaran
 */
package net.timtaran.interactivemc.init;

import com.mojang.logging.LogUtils;
import net.timtaran.interactivemc.init.registries.KeyMappings;
import net.timtaran.interactivemc.init.registries.ViveTrackers;
import org.slf4j.Logger;

/**
 * The main class for the InteractiveMC mod.
 *
 * @author timtaran
 */
public class InteractiveMC {
    public static final String MOD_ID = "interactivemc";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Called during the common initialization phase (Server and Client).
     * Initializes registries, packets, and loads native libraries.
     */
    public static void onInit() {
        LOGGER.info("Initializing InteractiveMC");
    }

    /**
     * Called during the client-side initialization phase.
     * Initializes client-specific components.
     */
    public static void onClientInit() {
        LOGGER.info("Initializing InteractiveMC Client");
        KeyMappings.init();
        ViveTrackers.init();
    }
}
