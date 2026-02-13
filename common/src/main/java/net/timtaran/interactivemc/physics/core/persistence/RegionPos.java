package net.timtaran.interactivemc.physics.core.persistence;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/persistence/RegionPos.java
package net.timtaran.interactivemc.physics.physics.persistence;
========
package net.timtaran.interactivemc.physics.core.persistence;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/persistence/RegionPos.java

/**
 * Represents the coordinate of a region file in the world.
 * Each region covers an area of 32x32 chunks.
 *
 * @param x The region x-coordinate (chunkX >> 5).
 * @param z The region z-coordinate (chunkZ >> 5).
 * @author xI-Mx-Ix
 */
public record RegionPos(int x, int z) {
}