package me.ultrablacklinux.twofa.mixin;

import me.ultrablacklinux.twofa.util.Account;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onTitle", at = @At("HEAD"))
    private void onTitle(TitleS2CPacket packet, CallbackInfo ci) {
        boolean hasCorrectTitle = false;
        if ((packet.getAction() == TitleS2CPacket.Action.TITLE || packet.getAction() == TitleS2CPacket.Action.SUBTITLE)) {
            hasCorrectTitle = true;
        }
        if (hasCorrectTitle) {
            Account.getMessage(packet.getText().getString());
        }
    }
}
