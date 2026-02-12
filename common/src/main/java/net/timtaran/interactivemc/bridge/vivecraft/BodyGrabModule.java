package net.timtaran.interactivemc.bridge.vivecraft;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.timtaran.interactivemc.util.InteractiveMCIdentifier;
import org.vivecraft.api.client.HeldInteractModule;

public class BodyGrabModule implements HeldInteractModule {
    private final ResourceLocation id;
    private final InteractionHand hand;

    public BodyGrabModule(InteractionHand hand) {
        this.id = InteractiveMCIdentifier.get(hand.name().toLowerCase() + "_body_grab");
        this.hand = hand;
    }

    /**
     * @param localPlayer
     * @param interactionHand
     * @param vec3
     * @return
     */
    @Override
    public boolean isActive(LocalPlayer localPlayer, InteractionHand interactionHand, Vec3 vec3) {
        return interactionHand.equals(hand);
    }

    /**
     * @param player
     * @param hand
     * @return
     */
    @Override
    public boolean onHoldTick(LocalPlayer player, InteractionHand hand) {
        // todo send grab try packet
        System.out.println("hold");
        return HeldInteractModule.super.onHoldTick(player, hand);
    }

    /**
     * @param localPlayer
     * @param interactionHand
     */
    @Override
    public void onRelease(LocalPlayer localPlayer, InteractionHand interactionHand) {
        System.out.println("release");
    }

    /**
     * @return
     */
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public int getPriority() {
        // before everything
        return 0;
    }

    /**
     * @param localPlayer
     * @param interactionHand
     * @return
     */
    @Override
    public boolean onPress(LocalPlayer localPlayer, InteractionHand interactionHand) {
        System.out.println("press");
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean swingsArm() {
        return false;
    }
}
