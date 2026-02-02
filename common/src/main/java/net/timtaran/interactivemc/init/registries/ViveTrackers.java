/*
 * This file is part of InteractiveMC.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.init.registries;

import net.timtaran.interactivemc.bridge.vivecraft.PlayerBodyTracker;
import org.vivecraft.client_vr.ClientDataHolderVR;

/**
 * Registry for ViveCraft VR trackers.
 *
 * @author timtaran
 */
public class ViveTrackers {
    private static final PlayerBodyTracker PLAYER_BODY_TRACKER = new PlayerBodyTracker();

    /**
     * Initializes and registers ViveCraft VR trackers.
     */
    public static void init() {
        ClientDataHolderVR.getInstance().registerTracker(PLAYER_BODY_TRACKER);
    }
}
