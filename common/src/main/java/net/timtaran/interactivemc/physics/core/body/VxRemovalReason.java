package net.timtaran.interactivemc.physics.core.body;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/body/VxRemovalReason.java
package net.timtaran.interactivemc.physics.physics.body;
========
package net.timtaran.interactivemc.physics.core.body;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/body/VxRemovalReason.java

/**
 * Enum representing the reason for removing a physics body.
 * This is used to determine how the body should be handled when it is removed.
 *
 * @author xI-Mx-Ix
 */
public enum VxRemovalReason {
    /**
     *  The body is discarded and should not be saved.
     */
    DISCARD,

    /**
     * The body is being temporarily unloaded and should be saved to disk.
     */
    SAVE,

    /**
     * The body is being removed from memory because its chunk is unloading. Persistence is handled separately.
     */
    UNLOAD
}