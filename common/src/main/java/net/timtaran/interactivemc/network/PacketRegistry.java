/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.network;

import dev.architectury.networking.NetworkManager;
import net.timtaran.interactivemc.network.sync.packet.C2SFrameVRPosePacket;
import net.timtaran.interactivemc.physics.network.VxPacketHandler;

/**
 * Registers packets same way as {@link net.timtaran.interactivemc.physics.network.VxPacketRegistry}.
 *
 * @author timtaran
 */
public class PacketRegistry {
    public static void registerPackets() {
        // --- Client to Server Packets (C2S) ---
        // These packets are sent by the client and handled on the server.

        VxPacketHandler.registerPacket(
                C2SFrameVRPosePacket.class,
                "frame_pose",
                C2SFrameVRPosePacket::encode,
                C2SFrameVRPosePacket::decode,
                C2SFrameVRPosePacket::handle,
                NetworkManager.Side.C2S
        );

        // --- Server to Client Packets (S2C) ---
        // These packets are sent by the server and handled on the client.
    }
}
