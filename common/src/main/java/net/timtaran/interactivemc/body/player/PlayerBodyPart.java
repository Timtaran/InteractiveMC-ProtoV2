/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 *
 * Copyright (c) 2026 timtaran
 */
package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.Vec3;

public enum PlayerBodyPart {
    HEAD(new Vec3(0.5f, 0.5f, 0.5f)),

    LEFT_ARM(new Vec3(0.25f, 0.75f, 0.25f)),
    RIGHT_ARM(new Vec3(0.25f, 0.75f, 0.25f));

    private final Vec3 size;

    /**
     * Defines a body part for a VR player body.
     *
     * @param size The full size (width, height, depth) of the physics shape for this part.
     */
    PlayerBodyPart(Vec3 size) {
        this.size = size;
    }

    /**
     * Gets the full size of this body part.
     *
     * @return A vector representing the width, height, and depth.
     */
    public Vec3 getSize() {
        return size;
    }

    /**
     * Calculates the local pivot point on this body part for its joint connection.
     * This is typically at the top-center for limbs and the bottom-center for the head.
     *
     * @return A vector representing the local pivot point.
     */
    public Vec3 getLocalPivot() {
        return new Vec3(0, 0, 0); // todo replace with real values

    }
}
