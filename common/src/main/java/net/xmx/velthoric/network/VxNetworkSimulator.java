package net.xmx.velthoric.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

// not sure is that code right because it's ai generated and idk
@Environment(EnvType.CLIENT)
public class VxNetworkSimulator {
    public static <T> void simulateClientReceive(
            T packet,
            BiConsumer<T, Supplier<NetworkManager.PacketContext>> handler
    ) {
        handler.accept(packet, VxNetworkSimulator::createClientContext);
    }

    @Environment(EnvType.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    private static NetworkManager.PacketContext createClientContext() {
        Minecraft mc = getMinecraft();

        return new NetworkManager.PacketContext() {

            @Override
            public Player getPlayer() {
                // Client player (LocalPlayer extends Player)
                return mc.player;
            }

            @Override
            public void queue(Runnable runnable) {
                // Ensure correct thread (same as real packet handling)
                mc.execute(runnable);
            }

            @Override
            public Env getEnvironment() {
                return Env.CLIENT;
            }

            @Override
            public RegistryAccess registryAccess() {
                // Same registry view the client normally uses
                return mc.level != null
                        ? mc.level.registryAccess()
                        : RegistryAccess.EMPTY;
            }
        };
    }
}
