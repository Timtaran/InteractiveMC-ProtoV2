/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.xmx.velthoric.physics.lifecycle.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;


import java.util.function.Supplier;

/**
 * A packet used to measure round-trip time (RTT) between server and client.
 * This packet is sent from the server to the client in response to a C2S RTT
 * packet with current server tick.
 *
 * @author timtaran
 */
public class S2CRoundTripTimePacket {
    private final long clientTick;
    private final long serverTick;

    public S2CRoundTripTimePacket(long clientTick, long serverTick) {
        this.clientTick = clientTick;
        this.serverTick = serverTick;
    }


    public static void encode(S2CRoundTripTimePacket msg, FriendlyByteBuf buf) {
        buf.writeLong(msg.clientTick);
        buf.writeLong(msg.serverTick);
    }

    public static S2CRoundTripTimePacket decode(FriendlyByteBuf buf) {
        return new S2CRoundTripTimePacket(buf.readLong(), buf.readLong());
    }

    public static void handle(S2CRoundTripTimePacket msg, Supplier<NetworkManager.PacketContext> ignoredCtx) {
        // todo implement
        System.out.println("S2C RTT data: " + msg.clientTick + " " + msg.serverTick);
    }
}
