package net.timtaran.interactivemc.body;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.timtaran.interactivemc.body.player.PlayerBodyPartRenderer;
import net.timtaran.interactivemc.body.player.PlayerBodyPartRigidBody;
import net.timtaran.interactivemc.physics.physics.body.registry.VxBodyRegistry;
import net.timtaran.interactivemc.physics.physics.body.registry.VxBodyType;
import net.timtaran.interactivemc.util.InteractiveMCIdentifier;

public class BodyRegistry {
    public static final VxBodyType<PlayerBodyPartRigidBody> PLAYER_BODY_PART = VxBodyType.Builder
            .<PlayerBodyPartRigidBody>create(PlayerBodyPartRigidBody::new)
            .noSummon()
            .build(InteractiveMCIdentifier.get("player_body_part"));

    public static void register() {
        VxBodyRegistry.getInstance().register(PLAYER_BODY_PART);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        var registry = VxBodyRegistry.getInstance();

        // Client-side factory registration
        registry.registerClientFactory(PLAYER_BODY_PART.getTypeId(), (type, id) -> new PlayerBodyPartRigidBody((VxBodyType<PlayerBodyPartRigidBody>) type, id));

        // Client-side renderer registration
        registry.registerClientRenderer(PLAYER_BODY_PART.getTypeId(), new PlayerBodyPartRenderer());
    }
}
