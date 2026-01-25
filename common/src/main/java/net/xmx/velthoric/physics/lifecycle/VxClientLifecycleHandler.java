/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.xmx.velthoric.physics.lifecycle;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.xmx.velthoric.event.api.VxClientLevelEvent;
import net.xmx.velthoric.event.api.VxClientPlayerNetworkEvent;
import net.xmx.velthoric.physics.world.VxClientPhysicsWorld;
import net.xmx.velthoric.physics.world.VxPhysicsWorld;

/**
 * Handles the client-side physics lifecycle events.
 * Connects level loading, unloading, and client ticks to the singleton {@link VxClientPhysicsWorld}.
 *
 * @author xI-Mx-Ix
 */
public class VxClientLifecycleHandler {

    /**
     * Registers all necessary client-side event listeners.
     */
    public static void registerEvents() {
        VxClientLevelEvent.Load.EVENT.register(VxClientLifecycleHandler::onLevelLoad);
        VxClientLevelEvent.Unload.EVENT.register(VxClientLifecycleHandler::onLevelUnload);
        ClientTickEvent.CLIENT_PRE.register(VxClientLifecycleHandler::onClientTick);
        VxClientPlayerNetworkEvent.LoggingOut.EVENT.register(event -> onDisconnect());
    }

    /**
     * Called when a new client level is loaded.
     *
     * @param event The level load event.
     */
    private static void onLevelLoad(VxClientLevelEvent.Load event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            VxPhysicsWorld.getOrCreate(level);
        }

    }

    /**
     * Called when a client level is unloaded.
     *
     * @param event The level unload event.
     */
    private static void onLevelUnload(VxClientLevelEvent.Unload event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            VxPhysicsWorld.shutdown(level.dimension());
        }
    }

    /**
     * Called just before the client processes its tick logic.
     * Passes the pause state to the physics world to handle clock synchronization.
     *
     * @param client The Minecraft instance.
     */
    private static void onClientTick(net.minecraft.client.Minecraft client) {
        if (client.level != null) {
            VxPhysicsWorld physicsWorld = VxPhysicsWorld.get(client.level.dimension());
            if (physicsWorld != null && physicsWorld.isRunning()) {
                physicsWorld.onGameTick(client.level);
            }
        }
        VxClientPhysicsWorld.getInstance().tick(client.isPaused());
    }

    /**
     * Called when the player disconnects/logs out. Ensures cleanup.
     */
    private static void onDisconnect() {
        VxClientPhysicsWorld.getInstance().clear();
    }
}