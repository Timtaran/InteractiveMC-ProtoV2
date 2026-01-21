/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 *
 * Copyright (c) 2026 timtaran
 */
package net.timtaran.interactivemc.init.registries;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.timtaran.interactivemc.util.InteractiveMCIdentifier;

/**
 * Key mappings for the mod.
 *
 * @author timtaran
 */
public class KeyMappings {
    public static final String KEYBIND_CATEGORY = InteractiveMCIdentifier.getTranslationKey("category", "vrbindings");

    // We are not binding Main-Hand trigger as it is handled by the existing Minecraft attack key.
    // todo examine `org.vivecraft.api.client.InteractModule` as alternative to interrupt minecraft logic and handle main-hand grab on our own.

    // Main-Hand Grab
    public static final KeyMapping MAIN_GRAB_KEYMAPPING = new KeyMapping(
            InteractiveMCIdentifier.getTranslationKey("key", "mgrab"),
            InputConstants.Type.KEYSYM,
            -1,
            KEYBIND_CATEGORY
    );

    // Off-Hand Trigger
    public static final KeyMapping OFF_TRIGGER_KEYMAPPING = new KeyMapping(
            InteractiveMCIdentifier.getTranslationKey("key", "otrigger"),
            InputConstants.Type.KEYSYM,
            -1,
            KEYBIND_CATEGORY
    );

    // Off-Hand Grab
    public static final KeyMapping OFF_GRAB_KEYMAPPING = new KeyMapping(
            InteractiveMCIdentifier.getTranslationKey("key", "ograb"),
            InputConstants.Type.KEYSYM,
            -1,
            KEYBIND_CATEGORY
    );

    /**
     * Registers the key mappings. Should be called during the client initialization phase.
     */
    public static void init() {
        KeyMappingRegistry.register(MAIN_GRAB_KEYMAPPING);
        KeyMappingRegistry.register(OFF_GRAB_KEYMAPPING);
        KeyMappingRegistry.register(OFF_TRIGGER_KEYMAPPING);
    }
}
