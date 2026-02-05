/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.init;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.timtaran.interactivemc.body.BodyRegistry;
import net.timtaran.interactivemc.body.player.PlayerBodyManager;
import net.timtaran.interactivemc.init.registries.KeyMappings;
import net.timtaran.interactivemc.init.registries.ViveTrackers;
import net.timtaran.interactivemc.network.PacketRegistry;
import net.timtaran.interactivemc.physics.physics.world.VxPhysicsWorld;
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

        // todo refactor

        PlayerEvent.PLAYER_JOIN.register((ServerPlayer player) -> {
            PlayerBodyManager.get(VxPhysicsWorld.get(player.level().dimension())).spawnPlayer(player);
        } );

        BodyRegistry.register();
        PacketRegistry.registerPackets();
    }

    /**
     * Called during the client-side initialization phase.
     * Initializes client-specific components.
     */
    public static void onClientInit() {
        LOGGER.info("Initializing InteractiveMC Client");
        KeyMappings.init();
        ViveTrackers.init();
        BodyRegistry.registerClient();
    }
}
