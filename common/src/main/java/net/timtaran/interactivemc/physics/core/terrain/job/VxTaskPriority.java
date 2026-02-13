package net.timtaran.interactivemc.physics.core.terrain.job;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/terrain/job/VxTaskPriority.java
package net.timtaran.interactivemc.physics.physics.terrain.job;
========
package net.timtaran.interactivemc.physics.core.terrain.job;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/terrain/job/VxTaskPriority.java

/**
 * Represents the priority levels assigned to terrain generation tasks.
 * <p>
 * These priorities determine the order in which chunks are processed,
 * ensuring that areas near active entities or players are generated first.
 * </p>
 *
 * <p>Typical usage includes scheduling terrain jobs for physics simulations,
 * world streaming, or chunk updates.</p>
 *
 * @author xI-Mx-Ix
 */
public enum VxTaskPriority {

    /**
     * Low priority - used for background generation far from any player or body.
     */
    LOW,

    /**
     * Medium priority - standard level for normal terrain operations like block updates.
     */
    MEDIUM,

    /**
     * High priority - for chunks in the immediate preloading range of an active body.
     */
    HIGH,

    /**
     * Critical priority - reserved for chunks required immediately by the simulation.
     */
    CRITICAL,
}