/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.init.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

/**
 * This class handles the registration of entity types.
 *
 * @author xI-Mx-Ix
 */
public class EntityRegistry {

    public static void register() {
        ModRegistries.ENTITY_TYPES.register();
        registerEntityRenderers();
    }

    private static void registerEntityRenderers() {
    }
}
