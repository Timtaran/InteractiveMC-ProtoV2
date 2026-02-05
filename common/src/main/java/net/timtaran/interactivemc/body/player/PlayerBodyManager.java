package net.timtaran.interactivemc.body.player;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.operator.Op;
import net.minecraft.world.entity.player.Player;
import net.timtaran.interactivemc.body.BodyRegistry;
import net.timtaran.interactivemc.physics.math.VxConversions;
import net.timtaran.interactivemc.physics.math.VxTransform;
import net.timtaran.interactivemc.physics.physics.body.manager.VxBodyManager;
import net.timtaran.interactivemc.physics.physics.world.VxPhysicsWorld;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class PlayerBodyManager {
    private static final HashMap<VxPhysicsWorld, PlayerBodyManager> managers = new HashMap<>();

    private final VxPhysicsWorld world;
    private final VxBodyManager bodyManager;

    private PlayerBodyManager(VxPhysicsWorld world) {
        this.world = world;
        this.bodyManager = world.getBodyManager();
    }

    public static PlayerBodyManager get(VxPhysicsWorld world) {
        return managers.computeIfAbsent(world, PlayerBodyManager::new);
    }

    private PlayerBodyPartRigidBody createBodyPart(PlayerBodyPart partType, Player player) {
        Vec3 size = partType.getSize();
        Vec3 halfExtents = Op.star(0.5f, size);

        VxTransform transform = new VxTransform(
                VxConversions.toJolt(player.position().add(partType.getLocalPivot())),
                new Quat()
        );

        return bodyManager.createRigidBody(
                BodyRegistry.PLAYER_BODY_PART,
                transform,
                EActivation.Activate,
                body -> {
                    body.setServerData(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS, halfExtents);
                    body.setServerData(PlayerBodyPartRigidBody.DATA_PLAYER_ID, player.getUUID());
                    body.setServerData(PlayerBodyPartRigidBody.DATA_BODY_PART, partType);
                }
        );
    }

    public void spawnPlayer(Player player) {
        Map<PlayerBodyPart, PlayerBodyPartRigidBody> parts = new EnumMap<>(PlayerBodyPart.class);

        for (PlayerBodyPart partType : PlayerBodyPart.values()) {
            PlayerBodyPartRigidBody partBody = createBodyPart(partType, player);
            parts.put(partType, partBody);
        }
    }
}
