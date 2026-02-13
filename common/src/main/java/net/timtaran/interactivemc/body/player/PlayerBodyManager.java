/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EAxis;
import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.operator.Op;
import net.minecraft.world.entity.player.Player;
import net.timtaran.interactivemc.body.BodyRegistry;
import net.timtaran.interactivemc.physics.math.VxConversions;
import net.timtaran.interactivemc.physics.math.VxTransform;
import net.timtaran.interactivemc.physics.core.physics.world.VxPhysicsWorld;

import java.util.HashMap;

public class PlayerBodyManager {
    private static final HashMap<VxPhysicsWorld, PlayerBodyManager> managers = new HashMap<>();

    private final VxPhysicsWorld world;

    private PlayerBodyManager(VxPhysicsWorld world) {
        this.world = world;
    }

    public static PlayerBodyManager get(VxPhysicsWorld world) {
        return managers.computeIfAbsent(world, PlayerBodyManager::new);
    }

    private void createBodyPart(PlayerBodyPart partType, Player player) {
        Vec3 size = partType.getSize();
        Vec3 halfExtents = Op.star(0.5f, size);

        VxTransform transform = new VxTransform(
                VxConversions.toJolt(player.position().add(partType.getLocalPivot()).add(new net.minecraft.world.phys.Vec3(0, 2, 0))),
                new Quat()
        );

        PlayerBodyPartRigidBody bodyPart = world.getBodyManager().createRigidBody(
                BodyRegistry.PLAYER_BODY_PART,
                transform,
                EActivation.Activate,
                body -> {
                    body.setServerData(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS, halfExtents);
                    body.setServerData(PlayerBodyPartRigidBody.DATA_PLAYER_ID, player.getUUID());
                    body.setServerData(PlayerBodyPartRigidBody.DATA_BODY_PART, partType);
                }
        );

        PlayerBodyPartGhostRigidBody bodyPartGhost = world.getBodyManager().createRigidBody(
                BodyRegistry.PLAYER_BODY_PART_GHOST,
                transform,
                EActivation.Activate,
                body -> {
                    body.setServerData(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS, halfExtents);
                    body.setServerData(PlayerBodyPartRigidBody.DATA_PLAYER_ID, player.getUUID());
                    body.setServerData(PlayerBodyPartRigidBody.DATA_BODY_PART, partType);
                }
        );

        try (PointConstraintSettings settings = new PointConstraintSettings()) {
            System.out.println(settings.getSpace());
        }

        try (SixDofConstraintSettings settings = new SixDofConstraintSettings()) {
            System.out.print(settings.getSpace());
            settings.setSpace(EConstraintSpace.LocalToBodyCom);

            settings.setPosition1(new RVec3());
            settings.setPosition2(new RVec3());

            settings.setAxisX1(new Vec3(1f, 0f, 0f));
            settings.setAxisY1(new Vec3(0f, 1f, 0f));
            settings.setAxisX2(new Vec3(1f, 0f, 0f));
            settings.setAxisY2(new Vec3(0f, 1f, 0f));

            settings.setLimitedAxis(EAxis.TranslationX, 0f, 0f);
            settings.setLimitedAxis(EAxis.TranslationY, 0f, 0f);
            settings.setLimitedAxis(EAxis.TranslationZ, 0f, 0f);
            settings.setLimitedAxis(EAxis.RotationX, 0f, 0f);
            settings.setLimitedAxis(EAxis.RotationY, 0f, 0f);
            settings.setLimitedAxis(EAxis.RotationZ, 0f, 0f);

            MotorSettings linearMotor = new MotorSettings(8.0f, 1.0f, 1500.0f, 0f);
            settings.setMotorSettings(EAxis.TranslationX, linearMotor);
            settings.setMotorSettings(EAxis.TranslationY, linearMotor);
            settings.setMotorSettings(EAxis.TranslationZ, linearMotor);

            MotorSettings angularMotor = new MotorSettings(10.0f, 1.0f, 0f, 800.0f);
            settings.setMotorSettings(EAxis.RotationX, angularMotor);
            settings.setMotorSettings(EAxis.RotationY, angularMotor);
            settings.setMotorSettings(EAxis.RotationZ, angularMotor);

            // Create the constraint. It will be activated automatically once both bodies are loaded.
            world.getConstraintManager().createConstraint(settings, bodyPartGhost.getPhysicsId(), bodyPart.getPhysicsId());
        }
    }

    public void spawnPlayer(Player player) {
        for (PlayerBodyPart partType : PlayerBodyPart.values()) {
            createBodyPart(partType, player);
        }
    }

    // todo try to grab method
}
