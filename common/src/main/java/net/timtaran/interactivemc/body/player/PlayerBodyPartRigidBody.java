/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 *
 * Copyright (c) 2026 timtaran
 */
package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.timtaran.interactivemc.network.sync.DataSerializers;
import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.physics.VxPhysicsLayers;
import net.timtaran.interactivemc.physics.physics.body.VxRemovalReason;
import net.timtaran.interactivemc.physics.physics.body.network.synchronization.VxSynchronizedData;
import net.timtaran.interactivemc.physics.physics.body.network.synchronization.accessor.VxServerAccessor;
import net.timtaran.interactivemc.physics.physics.body.registry.VxBodyType;
import net.timtaran.interactivemc.physics.physics.body.type.VxRigidBody;
import net.timtaran.interactivemc.physics.physics.body.type.factory.VxRigidBodyFactory;
import net.timtaran.interactivemc.physics.physics.world.VxPhysicsWorld;

import java.util.UUID;

/**
 * Represents a single rigid body part of a ragdoll, such as a head, torso, or limb.
 * It stores data about its type, dimensions, and the skin texture it should use for rendering.
 *
 * @author xI-Mx-Ix
 */
public class PlayerBodyPartRigidBody extends VxRigidBody {

    public static final VxServerAccessor<PlayerBodyPart> DATA_BODY_PART = VxServerAccessor.create(PlayerBodyPartRigidBody.class, DataSerializers.BODY_PART);

    /**
     * Server-side constructor.
     */
    public PlayerBodyPartRigidBody(VxBodyType<PlayerBodyPartRigidBody> type, VxPhysicsWorld world, UUID id) {
        super(type, world, id);
    }

    /**
     * Client-side constructor.
     */
    @Environment(EnvType.CLIENT)
    public PlayerBodyPartRigidBody(VxBodyType<PlayerBodyPartRigidBody> type, UUID id) {
        super(type, id);
    }

    @Override
    protected void defineSyncData(VxSynchronizedData.Builder builder) {
        builder.define(DATA_BODY_PART, PlayerBodyPart.HEAD);
    }

    @Override
    public int createJoltBody(VxRigidBodyFactory factory) {
        PlayerBodyPart partType = get(DATA_BODY_PART);
        Vec3 fullSize = partType.getSize();

        ShapeSettings shapeSettings = new BoxShapeSettings(new Vec3(fullSize.getX() / 2, fullSize.getY() / 2, fullSize.getZ() / 2));


        try (BodyCreationSettings bcs = new BodyCreationSettings()) {
            bcs.setMotionType(EMotionType.Kinematic);
            bcs.setObjectLayer(VxPhysicsLayers.MOVING);
            bcs.setFriction(0.5f);
            bcs.setRestitution(0.3f);
            return factory.create(shapeSettings, bcs);
        } finally {
            shapeSettings.close();
        }
    }


    @Override
    public void writePersistenceData(VxByteBuf buf) {
        super.writePersistenceData(buf);
        DataSerializers.BODY_PART.write(buf, get(DATA_BODY_PART));
    }

    @Override
    public void readPersistenceData(VxByteBuf buf) {
        VxPhysicsWorld physicsWorld = getPhysicsWorld();
        if (physicsWorld != null) {
            this.getPhysicsWorld().getBodyManager().removeBody(this.physicsId, VxRemovalReason.DISCARD);
        }
    }
}