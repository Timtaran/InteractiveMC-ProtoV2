/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.Vec3;
import org.vivecraft.api.data.VRBodyPart;

public enum PlayerBodyPart {
    HEAD(new Vec3(0.5f, 0.5f, 0.5f)),

    MAIN_HAND(new Vec3(0.25f, 0.75f, 0.25f)),
    OFF_HAND(new Vec3(0.25f, 0.75f, 0.25f));
    // todo add elbow

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
        return new Vec3(0f, 0f, 0f);

    }

    public VRBodyPart toVRBodyPart() {
        return VRBodyPart.valueOf(name());
    }
}
