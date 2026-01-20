/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.vxnative.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.timtaran.interactivemc.physics.vxnative.debug.VxF3ScreenAddition;
import net.timtaran.interactivemc.physics.vxnative.item.physicsgun.beam.VxPhysicsGunBeamRenderer;
import net.timtaran.interactivemc.physics.vxnative.item.physicsgun.event.VxPhysicsGunClientEvents;
import net.timtaran.interactivemc.physics.vxnative.item.physicsgun.event.VxPhysicsGunEvents;
import net.timtaran.interactivemc.physics.vxnative.item.tool.event.VxToolClientEvents;
import net.timtaran.interactivemc.physics.vxnative.item.tool.event.VxToolEvents;
import net.timtaran.interactivemc.physics.vxnative.physics.lifecycle.VxClientLifecycleHandler;
import net.timtaran.interactivemc.physics.physics.lifecycle.VxServerLifecycleHandler;
import net.timtaran.interactivemc.physics.vxnative.physics.body.client.renderer.VxPhysicsRenderer;
import net.timtaran.interactivemc.physics.vxnative.vehicle.gui.VxVehicleHudRenderer;

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
        VxClientLifecycleHandler.registerEvents();
        VxF3ScreenAddition.registerEvents();
        VxPhysicsRenderer.registerEvents();
        VxPhysicsGunBeamRenderer.registerEvents();
        VxPhysicsGunClientEvents.registerEvents();
        VxToolClientEvents.registerEvents();
        VxVehicleHudRenderer.registerEvents();
    }
}