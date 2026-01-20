/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.vxnative.init.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.timtaran.interactivemc.physics.vxnative.item.VxPhysicsCreatorItem;
import net.timtaran.interactivemc.physics.vxnative.item.boxlauncher.VxBoxLauncherItem;
import net.timtaran.interactivemc.physics.vxnative.item.boxlauncher.VxBoxLauncherMode;
import net.timtaran.interactivemc.physics.vxnative.item.chaincreator.VxChainCreatorItem;
import net.timtaran.interactivemc.physics.vxnative.item.chaincreator.VxChainCreatorMode;
import net.timtaran.interactivemc.physics.vxnative.item.magnetizer.VxMagnetizerItem;
import net.timtaran.interactivemc.physics.vxnative.item.magnetizer.VxMagnetizerMode;
import net.timtaran.interactivemc.physics.vxnative.item.physicsgun.VxPhysicsGunItem;
import net.timtaran.interactivemc.physics.vxnative.item.ragdolllauncher.VxRagdollLauncherItem;
import net.timtaran.interactivemc.physics.vxnative.item.ragdolllauncher.VxRagdollLauncherMode;
import net.timtaran.interactivemc.physics.vxnative.item.tool.registry.VxToolRegistry;

/**
 * This class handles the registration of items.
 *
 * @author xI-Mx-Ix
 */
public class ItemRegistry {
    public static final RegistrySupplier<Item> PHYSICS_CREATOR_STICK = ModRegistries.ITEMS.register("physics_creator", VxPhysicsCreatorItem::new);
    public static final RegistrySupplier<Item> PHYSICS_GUN = ModRegistries.ITEMS.register("physics_gun", VxPhysicsGunItem::new);
    public static final RegistrySupplier<Item> MAGNETIZER = ModRegistries.ITEMS.register("magnetizer", VxMagnetizerItem::new);
    public static final RegistrySupplier<Item> BOX_LAUNCHER = ModRegistries.ITEMS.register("box_launcher", VxBoxLauncherItem::new);
    public static final RegistrySupplier<Item> CHAIN_CREATOR = ModRegistries.ITEMS.register("chain_creator", VxChainCreatorItem::new);
    public static final RegistrySupplier<Item> RAGDOLL_LAUNCHER = ModRegistries.ITEMS.register("ragdoll_launcher", VxRagdollLauncherItem::new);

    public static void register() {
        ModRegistries.ITEMS.register();

        ItemRegistry.BOX_LAUNCHER.listen(item -> VxToolRegistry.register(item, new VxBoxLauncherMode()));
        ItemRegistry.MAGNETIZER.listen(item -> VxToolRegistry.register(item, new VxMagnetizerMode()));
        ItemRegistry.RAGDOLL_LAUNCHER.listen(item -> VxToolRegistry.register(item, new VxRagdollLauncherMode()));
        ItemRegistry.CHAIN_CREATOR.listen(item -> VxToolRegistry.register(item, new VxChainCreatorMode()));
    }
}