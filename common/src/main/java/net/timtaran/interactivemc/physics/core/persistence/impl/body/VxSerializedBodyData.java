package net.timtaran.interactivemc.physics.core.persistence.impl.body;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/body/persistence/VxSerializedBodyData.java
package net.timtaran.interactivemc.physics.physics.body.persistence;
========
package net.timtaran.interactivemc.physics.core.persistence.impl.body;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/persistence/impl/body/VxSerializedBodyData.java

import net.minecraft.resources.ResourceLocation;
import net.timtaran.interactivemc.physics.network.VxByteBuf;

import java.util.UUID;

/**
 * Serialized snapshot of a physics body used for persistence or syncing.
 * This record holds the identification information and a data buffer containing
 * the internal state of the body (transform, velocity, vertices, etc.).
 *
 * @param typeId    body type identifier
 * @param id        unique instance ID
 * @param bodyData  the buffer containing the body's internal persistence data
 *
 * @author xI-Mx-Ix
 */
public record VxSerializedBodyData(
        ResourceLocation typeId,
        UUID id,
        VxByteBuf bodyData
) {}