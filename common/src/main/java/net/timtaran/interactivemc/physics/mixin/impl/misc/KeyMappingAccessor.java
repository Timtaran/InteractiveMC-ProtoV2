/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/mixin/impl/misc/KeyMappingAccessor.java
package net.timtaran.interactivemc.physics.mixin.impl.misc;
========
package net.timtaran.interactivemc.physics.mixin.impl.mounting.input;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/mixin/impl/mounting/input/KeyMappingAccessor.java

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Mixin accessor to access key value of KeyMapping.
 *
 * @author timtaran
 */
@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
    @Accessor("key")
    InputConstants.Key velthoric_getKey();
}