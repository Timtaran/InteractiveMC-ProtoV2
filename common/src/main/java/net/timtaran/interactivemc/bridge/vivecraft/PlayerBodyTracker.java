/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.bridge.vivecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.timtaran.interactivemc.network.sync.packet.C2SFrameVRPosePacket;
import net.timtaran.interactivemc.physics.network.VxPacketHandler;
import org.vivecraft.api.client.Tracker;
import org.vivecraft.api.client.VRClientAPI;
import org.vivecraft.api.data.VRPose;

/**
 * Tracker for monitoring the player's body movements in ViveCraft VR.
 *
 * @author timtaran
 */
@Environment(EnvType.CLIENT)
public class PlayerBodyTracker implements Tracker {
    private static final long NETWORK_SYNC_PERIOD = 50 / 1000000;
    private static long lastSync = 0;

    @Override
    public ProcessType processType() {
        return ProcessType.PER_FRAME;
    }

    @Override
    public boolean isActive(LocalPlayer localPlayer) {
        return localPlayer != null;
    }

    @Override
    public void activeProcess(LocalPlayer localPlayer) {
        if (System.nanoTime() - lastSync > NETWORK_SYNC_PERIOD) {
            // todo replace with vivecraft utils (maybe)
            VRPose renderPose = VRClientAPI.instance().getWorldRenderPose();
            VxPacketHandler.sendToServer(new C2SFrameVRPosePacket(renderPose));

            lastSync = System.nanoTime();
        }
    }
}
