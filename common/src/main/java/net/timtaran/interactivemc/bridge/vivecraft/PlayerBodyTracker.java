/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 *
 * Copyright (c) 2026 timtaran
 */
package net.timtaran.interactivemc.bridge.vivecraft;

import net.minecraft.client.player.LocalPlayer;
import org.vivecraft.api.client.Tracker;
import org.vivecraft.api.client.VRClientAPI;
import org.vivecraft.api.data.VRPose;

/**
 * Tracker for monitoring the player's body movements in ViveCraft VR.
 *
 * @author timtaran
 */
public class PlayerBodyTracker implements Tracker {
    private int tickCounter = 0;

    @Override
    public ProcessType processType() {
        return ProcessType.PER_FRAME;
    }

    @Override
    public boolean isActive(LocalPlayer localPlayer) {
        return true;
    }

    @Override
    public void activeProcess(LocalPlayer localPlayer) {
        VRPose renderPose = VRClientAPI.instance().getWorldRenderPose();

        tickCounter++;

        if (tickCounter % 120 == 0) {
            System.out.println("ViveCraft VR Tick: " + tickCounter);
           if (renderPose != null && renderPose.getHead() != null)
                    System.out.println(renderPose.getHead().getRotation().toString());
        }
    }
}
