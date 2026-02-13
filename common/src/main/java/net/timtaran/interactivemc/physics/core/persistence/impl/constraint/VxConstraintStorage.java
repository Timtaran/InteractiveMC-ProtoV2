package net.timtaran.interactivemc.physics.core.persistence.impl.constraint;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/constraint/persistence/VxConstraintStorage.java
package net.timtaran.interactivemc.physics.physics.constraint.persistence;

import net.minecraft.server.level.ServerLevel;
import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.physics.constraint.VxConstraint;
import net.timtaran.interactivemc.physics.physics.persistence.VxChunkBasedStorage;
========
package net.timtaran.interactivemc.physics.core.persistence.impl.constraint;

import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.core.constraint.VxConstraint;
import net.timtaran.interactivemc.physics.core.persistence.VxChunkBasedStorage;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/persistence/impl/constraint/VxConstraintStorage.java

import java.util.UUID;

/**
 * Storage implementation for physics constraints using the generic region system.
 *
 * @author xI-Mx-Ix
 */
public class VxConstraintStorage extends VxChunkBasedStorage<VxConstraint, VxConstraint> {

    public VxConstraintStorage(ServerLevel level) {
        super(level, "constraints", "vxc");
    }

    @Override
    protected void writeSingle(VxConstraint constraint, VxByteBuf buffer) {
        // Need to write ID manually as the Codec might expect it known or separate
        buffer.writeUUID(constraint.getConstraintId());
        VxConstraintCodec.serialize(constraint, buffer);
    }

    @Override
    protected VxConstraint readSingle(VxByteBuf buffer) {
        UUID id = buffer.readUUID();
        return VxConstraintCodec.deserialize(id, buffer);
    }
}