/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.timtaran.interactivemc.data.PlayerDataStore;
import net.timtaran.interactivemc.network.sync.DataSerializers;
import net.timtaran.interactivemc.physics.math.VxConversions;
import net.timtaran.interactivemc.physics.network.VxByteBuf;
import net.timtaran.interactivemc.physics.physics.VxPhysicsLayers;
import net.timtaran.interactivemc.physics.physics.body.VxJoltBridge;
import net.timtaran.interactivemc.physics.physics.body.VxRemovalReason;
import net.timtaran.interactivemc.physics.physics.body.network.synchronization.VxDataSerializers;
import net.timtaran.interactivemc.physics.physics.body.network.synchronization.VxSynchronizedData;
import net.timtaran.interactivemc.physics.physics.body.network.synchronization.accessor.VxServerAccessor;
import net.timtaran.interactivemc.physics.physics.body.registry.VxBodyType;
import net.timtaran.interactivemc.physics.physics.body.type.VxRigidBody;
import net.timtaran.interactivemc.physics.physics.body.type.factory.VxRigidBodyFactory;
import net.timtaran.interactivemc.physics.physics.ragdoll.body.VxBodyPartRigidBody;
import net.timtaran.interactivemc.physics.physics.world.VxPhysicsWorld;
import org.joml.Quaternionf;
import org.vivecraft.api.data.VRBodyPartData;
import org.vivecraft.api.data.VRPose;

import java.util.UUID;

/**
 * Represents a single rigid body part of a ragdoll, such as a head, torso, or limb.
 * It stores data about its type, dimensions, and the skin texture it should use for rendering.
 *
 * @author xI-Mx-Ix
 */
public class PlayerBodyPartRigidBody extends VxRigidBody {
    private static final int SIMULATION_HZ = 60;
    private static final float FIXED_TIME_STEP = 1.0f / SIMULATION_HZ;

    public static final VxServerAccessor<Vec3> DATA_HALF_EXTENTS = VxServerAccessor.create(PlayerBodyPartRigidBody.class, VxDataSerializers.VEC3);
    public static final VxServerAccessor<PlayerBodyPart> DATA_BODY_PART = VxServerAccessor.create(PlayerBodyPartRigidBody.class, DataSerializers.BODY_PART);
    public static final VxServerAccessor<UUID> DATA_PLAYER_ID = VxServerAccessor.create(PlayerBodyPartRigidBody.class, VxDataSerializers.UUID);

    /**
     * Server-side constructor.
     */
    public PlayerBodyPartRigidBody(VxBodyType<PlayerBodyPartRigidBody> type, VxPhysicsWorld world, UUID id) {
        super(type, world, id);
        setPersistent(false);
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
        builder.define(DATA_HALF_EXTENTS, new Vec3(0.25f, 0.25f, 0.25f));
        builder.define(DATA_BODY_PART, PlayerBodyPart.HEAD);
        builder.define(DATA_PLAYER_ID, UUID.randomUUID());
    }

    @Override
    public int createJoltBody(VxRigidBodyFactory factory) {
        PlayerBodyPart partType = get(DATA_BODY_PART);
        Vec3 fullSize = partType.getSize();

        System.out.println("jolt body create");


        try (ShapeSettings shapeSettings = new BoxShapeSettings(new Vec3(fullSize.getX() / 2, fullSize.getY() / 2, fullSize.getZ() / 2)); BodyCreationSettings bcs = new BodyCreationSettings()) {
            System.out.println("bcs");
            bcs.setMotionType(EMotionType.Kinematic);
            bcs.setObjectLayer(VxPhysicsLayers.NON_MOVING);
            return factory.create(shapeSettings, bcs);
        }
    }

    public void onPhysicsTick(VxPhysicsWorld world) {
        super.onPhysicsTick(world);
        VRPose pose = PlayerDataStore.vrPoses.get(get(DATA_PLAYER_ID));
        if (pose == null)
            return;

        VRBodyPartData bodyPartData = pose.getBodyPartData(get(DATA_BODY_PART).toVRBodyPart());
        if (bodyPartData == null)
            return;

        VxJoltBridge.INSTANCE.getJoltBody(world, this).moveKinematic(
                VxConversions.toJolt(bodyPartData.getPos()),
                VxConversions.toJolt(new Quaternionf(bodyPartData.getRotation())),
                FIXED_TIME_STEP
                );
    }


    @Override
    public void writePersistenceData(VxByteBuf buf) {
        super.writePersistenceData(buf);
        VxDataSerializers.VEC3.write(buf, get(DATA_HALF_EXTENTS));
        DataSerializers.BODY_PART.write(buf, get(DATA_BODY_PART));
        VxDataSerializers.UUID.write(buf, get(DATA_PLAYER_ID));
    }

    @Override
    public void readPersistenceData(VxByteBuf buf) {
        super.readPersistenceData(buf);
        setServerData(DATA_HALF_EXTENTS, VxDataSerializers.VEC3.read(buf));
        setServerData(DATA_BODY_PART, DataSerializers.BODY_PART.read(buf));
        setServerData(DATA_PLAYER_ID, VxDataSerializers.UUID.read(buf));
    }
}