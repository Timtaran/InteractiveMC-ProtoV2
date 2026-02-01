package net.xmx.velthoric.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class VxExecuteUtil {
    public static void executeRunnable(Level level, Runnable runnable) {
        if (!level.isClientSide) {
            level.getServer().execute(runnable);
        }
        else {
            executeRunnableOnClient(runnable);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void executeRunnableOnClient(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }
}
