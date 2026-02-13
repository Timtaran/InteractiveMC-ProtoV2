/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.timtaran.interactivemc.physics.core.body.client.VxClientBodyManager;
import net.timtaran.interactivemc.physics.core.body.client.renderer.dispatcher.VxPhysicsRenderDispatcher;
import net.timtaran.interactivemc.physics.debug.VxF3ScreenAddition;
import net.timtaran.interactivemc.physics.item.physicsgun.beam.VxPhysicsGunBeamRenderer;
import net.timtaran.interactivemc.physics.item.physicsgun.event.VxPhysicsGunClientEvents;
import net.timtaran.interactivemc.physics.item.physicsgun.event.VxPhysicsGunEvents;
import net.timtaran.interactivemc.physics.item.tool.event.VxToolClientEvents;
import net.timtaran.interactivemc.physics.item.tool.event.VxToolEvents;
import net.timtaran.interactivemc.physics.core.lifecycle.VxServerLifecycleHandler;

/**
 * @author xI-Mx-Ix
 */
public class RegisterEvents {

    public static void register() {
        VxServerLifecycleHandler.registerEvents();
        VxPhysicsGunEvents.registerEvents();
        VxToolEvents.registerEvents();
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        VxClientBodyManager.registerEvents();
        VxF3ScreenAddition.registerEvents();
        VxPhysicsRenderDispatcher.registerEvents();
        VxPhysicsGunBeamRenderer.registerEvents();
        VxPhysicsGunClientEvents.registerEvents();
        VxToolClientEvents.registerEvents();
    }
}