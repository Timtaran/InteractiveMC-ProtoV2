package net.timtaran.interactivemc.physics.core.persistence.impl.body;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/body/persistence/VxBodyStorage.java
package net.timtaran.interactivemc.physics.physics.body.persistence;

import net.minecraft.server.level.ServerLevel;
import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.physics.body.type.VxBody;
import net.timtaran.interactivemc.physics.physics.persistence.VxChunkBasedStorage;
========
package net.timtaran.interactivemc.physics.core.persistence.impl.body;

import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.core.body.type.VxBody;
import net.timtaran.interactivemc.physics.core.persistence.VxChunkBasedStorage;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/persistence/impl/body/VxBodyStorage.java

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