/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
package net.timtaran.interactivemc.physics.config.subsystems;

import net.timtaran.interactivemc.physics.config.VxConfigSpec;
import net.timtaran.interactivemc.physics.config.VxConfigValue;

/**
 * Configuration container for Client-Side settings.
 *
 * @author xI-Mx-Ix
 */
public class VxClientConfig {

    public final VxConfigValue<Long> interpolationDelayNanos;
    public final VxConfigValue<Boolean> enableCreativeTab;

    public VxClientConfig(VxConfigSpec.Builder builder) {
        this.interpolationDelayNanos = builder.defineInRange(
                "interpolation_delay_nanos",
                150_000_000L,
                0L,
                1_000_000_000L,
                "Delay applied to rendering physics bodies to smooth over network jitter (in nanoseconds). Default: 150ms."
        );

        this.enableCreativeTab = builder.define(
                "enable_creative_tab",
                true,
                "Whether to register the Velthoric creative tab. Requires a restart to take effect."
        );
    }
}