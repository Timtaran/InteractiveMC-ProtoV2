/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.xmx.velthoric.physics.lifecycle.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.xmx.velthoric.init.VxMainClass;
import net.xmx.velthoric.network.VxPacketHandler;
import net.xmx.velthoric.physics.world.VxPhysicsWorld;

import java.util.function.Supplier;

/**
 * A packet used to measure round-trip time (RTT) between client and server.
 * This packet is sent from the client to the server with current client tick
 * to request server physics tick.
 *
 * @author timtaran
 */
public class C2SRoundTripTimePacket {
    private final static ResourceKey<Registry<Level>> DIMENSION_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("dimension"));
    private final long clientTick;
    private final ResourceKey<Level> dimensionKey;

    public C2SRoundTripTimePacket(long clientTick, ResourceKey<Level> dimensionKey) {
        this.clientTick = clientTick;
        this.dimensionKey = dimensionKey;
    }


    public static void encode(C2SRoundTripTimePacket msg, FriendlyByteBuf buf) {
        buf.writeLong(msg.clientTick);
        buf.writeResourceKey(msg.dimensionKey);
    }

    public static C2SRoundTripTimePacket decode(FriendlyByteBuf buf) {
        return new C2SRoundTripTimePacket(buf.readLong(), buf.readResourceKey(DIMENSION_REGISTRY_KEY));
    }

    public static void handle(C2SRoundTripTimePacket msg, Supplier<NetworkManager.PacketContext> ctx) {
        if (ctx.get().getPlayer() instanceof ServerPlayer serverPlayer) {
            VxPhysicsWorld physicsWorld = VxPhysicsWorld.get(msg.dimensionKey);
            if (physicsWorld == null) {
                VxMainClass.LOGGER.warn("Failed to find physics world for {}", msg.dimensionKey);
                return;
            }
            VxPacketHandler.sendToPlayer(
                    new S2CRoundTripTimePacket(msg.clientTick, physicsWorld.getPhysicsTickCount()),
                    serverPlayer
            );
        }
    }
}
