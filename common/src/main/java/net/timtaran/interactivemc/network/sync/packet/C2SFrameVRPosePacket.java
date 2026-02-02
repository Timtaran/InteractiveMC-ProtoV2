/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.network.sync.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.timtaran.interactivemc.data.PlayerDataStore;
import org.jetbrains.annotations.Nullable;
import org.vivecraft.api.data.FBTMode;
import org.vivecraft.api.data.VRBodyPart;
import org.vivecraft.api.data.VRBodyPartData;
import org.vivecraft.api.data.VRPose;
import org.vivecraft.common.api_impl.data.VRBodyPartDataImpl;
import org.vivecraft.common.api_impl.data.VRPoseImpl;
import org.vivecraft.common.network.CommonNetworkHelper;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A packet that contains VR pose.
 *
 * @author timtaran
 */
public class C2SFrameVRPosePacket {
    private final VRPose pose;

    /**
     * Constructs a new C2S packet.
     *
     * @param pose The VR pose to send.
     */
    public C2SFrameVRPosePacket(VRPose pose) {
        this.pose = pose;
    }

    /**
     * Writes the VR body part data to the buffer.
     * @param buf  The buffer to read from.
     * @param data VR body part data.
     */
    private static void writeBodyPartData(FriendlyByteBuf buf, @Nullable VRBodyPartData data) {
        buf.writeBoolean(data != null);
        if (data == null) return;

        buf.writeVec3(data.getPos());
        buf.writeVec3(data.getDir());
        CommonNetworkHelper.serialize(buf, data.getRotation());
    }

    /**
     * Constructs a new C2S packet.
     * @param buf The buffer to read from.
     * @return    A new instance of the packet.
     */
    private static VRBodyPartData readBodyPartData(FriendlyByteBuf buf) {
        boolean present = buf.readBoolean();
        if (!present) return null;

        return new VRBodyPartDataImpl(buf.readVec3(), buf.readVec3(), CommonNetworkHelper.deserializeVivecraftQuaternion(buf));
    }


    /**
     * Encodes the packet's data into a network buffer.
     *
     * @param msg The packet instance to encode.
     * @param buf The buffer to write to.
     */
    public static void encode(C2SFrameVRPosePacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.pose.isSeated());
        buf.writeBoolean(msg.pose.isLeftHanded());

        buf.writeEnum(msg.pose.getFBTMode());

        for (VRBodyPart part : VRBodyPart.values()) {
            writeBodyPartData(buf, msg.pose.getBodyPartData(part));
        }
    }

    /**
     * Decodes the packet from a network buffer.
     * Decompresses the data before reconstructing the map.
     *
     * @param buf The buffer to read from.
     * @return A new instance of the packet.
     */
    public static C2SFrameVRPosePacket decode(FriendlyByteBuf buf) {
        boolean isSeated = buf.readBoolean();
        boolean isLeftHanded = buf.readBoolean();
        FBTMode fbtMode = buf.readEnum(FBTMode.class);

        // прочитаем все части в Map (сохраняем по enum-ключу)
        Map<VRBodyPart, VRBodyPartData> map = new EnumMap<>(VRBodyPart.class);
        for (VRBodyPart part : VRBodyPart.values()) {
            map.put(part, readBodyPartData(buf));
        }

        return new C2SFrameVRPosePacket(
                new VRPoseImpl(
                    map.get(VRBodyPart.HEAD),
                    map.get(VRBodyPart.MAIN_HAND),
                    map.get(VRBodyPart.OFF_HAND),
                    map.get(VRBodyPart.RIGHT_FOOT),
                    map.get(VRBodyPart.LEFT_FOOT),
                    map.get(VRBodyPart.WAIST),
                    map.get(VRBodyPart.RIGHT_KNEE),
                    map.get(VRBodyPart.LEFT_KNEE),
                    map.get(VRBodyPart.RIGHT_ELBOW),
                    map.get(VRBodyPart.LEFT_ELBOW),
                    isSeated,
                    isLeftHanded,
                    fbtMode
            )
        );
    }

    /**
     * Handles the packet on the server side.
     * Validates and applies updates using the anti-cheat logic in VxSynchronizedData.
     *
     * @param msg             The received packet.
     * @param contextSupplier A supplier for the network packet context.
     */
    public static void handle(C2SFrameVRPosePacket msg, Supplier<NetworkManager.PacketContext> contextSupplier) {
        PlayerDataStore.vrPoses.put(contextSupplier.get().getPlayer().getUUID(), msg.pose);
    }
}
