/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.network;

import net.timtaran.interactivemc.network.sync.packet.C2SFrameVRPosePacket;
import net.timtaran.interactivemc.physics.network.VxPacketRegistry;

/**
 * Registers packets same way as {@link net.timtaran.interactivemc.physics.network.VxPacketRegistry}.
 *
 * @author timtaran
 */
public class PacketRegistry {
    public static void registerPackets() {
        // --- Client to Server Packets (C2S) ---
        // These packets are sent by the client and handled on the server.

        VxPacketRegistry.registerC2S(
                C2SFrameVRPosePacket.class,
                C2SFrameVRPosePacket::decode
        );

        // --- Server to Client Packets (S2C) ---
        // These packets are sent by the server and handled on the client.
    }
}
