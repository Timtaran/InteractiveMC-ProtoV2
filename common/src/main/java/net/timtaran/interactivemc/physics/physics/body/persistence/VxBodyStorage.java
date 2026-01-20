/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.physics.body.persistence;

import net.minecraft.server.level.ServerLevel;
import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.physics.body.type.VxBody;
import net.timtaran.interactivemc.physics.physics.persistence.VxChunkBasedStorage;

/**
 * Storage implementation for physics bodies using the generic region system.
 *
 * @author xI-Mx-Ix
 */
public class VxBodyStorage extends VxChunkBasedStorage<VxBody, VxSerializedBodyData> {

    public VxBodyStorage(ServerLevel level) {
        super(level, "bodies", "vxb");
    }

    @Override
    protected void writeSingle(VxBody body, VxByteBuf buffer) {
        VxBodyCodec.serialize(body, buffer);
    }

    @Override
    protected VxSerializedBodyData readSingle(VxByteBuf buffer) {
        return VxBodyCodec.deserialize(buffer);
    }
}