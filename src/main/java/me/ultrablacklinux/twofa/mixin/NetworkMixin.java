package me.ultrablacklinux.twofa.mixin;

import me.ultrablacklinux.twofa.util.Login;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.UUID;


@Mixin(ClientPlayNetworkHandler.class)
public class NetworkMixin {
    private final MinecraftClient client = MinecraftClient.getInstance();
    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        if (packet.getMessage().getString().contains("/login") &&
                packet.getSenderUuid() != client.player.getUuid() &&
                !packet.getSenderUuid().equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
            Login.login();
        }
    }
}

