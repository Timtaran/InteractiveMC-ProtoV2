/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.xmx.velthoric.physics.body.network.internal.packet;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.enumerate.EActivation;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.xmx.velthoric.math.VxTransform;
import net.xmx.velthoric.network.VxByteBuf;
import net.xmx.velthoric.network.VxPacketUtils;
import net.xmx.velthoric.physics.body.client.VxClientBodyDataStore;
import net.xmx.velthoric.physics.body.client.VxClientBodyManager;
import net.xmx.velthoric.physics.body.network.internal.VxSpawnData;
import net.xmx.velthoric.physics.body.registry.VxBodyRegistry;
import net.xmx.velthoric.physics.body.registry.VxBodyType;
import net.xmx.velthoric.physics.body.type.VxBody;
import net.xmx.velthoric.physics.world.VxClientPhysicsWorld;
import net.xmx.velthoric.physics.world.VxPhysicsWorld;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * A network packet that contains a compressed batch of physics bodies to be spawned on the client.
 * This is more efficient than sending a separate packet for each individual body.
 *
 * @author xI-Mx-Ix
 */
public class S2CSpawnBodyBatchPacket {

    private final List<VxSpawnData> spawnDataList;

    /**
     * Constructs a new batch packet with a list of objects to spawn.
     *
     * @param spawnDataList The list of {@link VxSpawnData} for each body.
     */
    public S2CSpawnBodyBatchPacket(List<VxSpawnData> spawnDataList) {
        this.spawnDataList = spawnDataList;
    }

    /**
     * Encodes the packet's data into a network buffer.
     *
     * @param msg The packet instance to encode.
     * @param buf The buffer to write to.
     */
    public static void encode(S2CSpawnBodyBatchPacket msg, FriendlyByteBuf buf) {
        FriendlyByteBuf tempBuf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            tempBuf.writeVarInt(msg.spawnDataList.size());
            for (VxSpawnData data : msg.spawnDataList) {
                data.encode(tempBuf);
            }
            byte[] uncompressedData = new byte[tempBuf.readableBytes()];
            tempBuf.readBytes(uncompressedData);

            byte[] compressedData = VxPacketUtils.compress(uncompressedData);
            buf.writeVarInt(uncompressedData.length); // Write uncompressed size for client
            buf.writeByteArray(compressedData);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to compress spawn body batch packet", e);
        } finally {
            tempBuf.release();
        }
    }

    /**
     * Decodes the packet from a network buffer.
     *
     * @param buf The buffer to read from.
     * @return A new instance of the packet.
     */
    public static S2CSpawnBodyBatchPacket decode(FriendlyByteBuf buf) {
        int uncompressedSize = buf.readVarInt();
        byte[] compressedData = buf.readByteArray();
        try {
            byte[] decompressedData = VxPacketUtils.decompress(compressedData, uncompressedSize);
            FriendlyByteBuf decompressedBuf = new FriendlyByteBuf(Unpooled.wrappedBuffer(decompressedData));
            int size = decompressedBuf.readVarInt();
            List<VxSpawnData> spawnDataList = new ObjectArrayList<>(size);
            for (int i = 0; i < size; i++) {
                spawnDataList.add(new VxSpawnData(decompressedBuf));
            }
            return new S2CSpawnBodyBatchPacket(spawnDataList);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to decompress spawn body batch packet", e);
        }
    }

    /**
     * Handles the packet on the client side.
     *
     * @param msg             The received packet.
     * @param contextSupplier A supplier for the network packet context.
     */
    public static void handle(S2CSpawnBodyBatchPacket msg, Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            System.out.println("spawning on client-side: " + context.getPlayer().level().isClientSide);
            VxPhysicsWorld physicsWorld = VxPhysicsWorld.get(contextSupplier.get().getPlayer().level().dimension());
            VxClientBodyManager manager = VxClientPhysicsWorld.getInstance().getBodyManager();
            // Iterate through each spawn data entry and spawn the corresponding body on the client.

            Vec3 pos = contextSupplier.get().getPlayer().position(); // todo replace with real object position/rotation/velocity or keep physicsTick at 0 so everything would be corrected automatically

            System.out.println(physicsWorld);
            for (VxSpawnData data : msg.spawnDataList) {
                // Wrap the raw byte data into a buffer for the manager to read.
                VxByteBuf dataBuf = new VxByteBuf(Unpooled.wrappedBuffer(data.data));
                try {
                    // Look up the registered body type
                    manager.spawnBody(data.id, data.networkId, data.typeIdentifier, dataBuf, data.timestamp);

                    VxBodyType<?> type = VxBodyRegistry.getInstance().getRegistrationData(data.typeIdentifier);

                    // Prepare transform for the new body (position + identity rotation)
                    VxTransform transform = new VxTransform(new RVec3(pos.x, pos.y, pos.z), Quat.sIdentity());

                    // Create the physics body instance
                    VxBody body = type.create(physicsWorld, UUID.randomUUID());

                    // Add the constructed body to the world with activation
                    physicsWorld.getBodyManager().addConstructedBody(body, EActivation.Activate, transform);

                    System.out.println("spawned body: " + data.id);
                } finally {
                    // Ensure the buffer is released to prevent memory leaks.
                    if (dataBuf.refCnt() > 0) {
                        dataBuf.release();
                    }
                }
            }
        });
    }
}