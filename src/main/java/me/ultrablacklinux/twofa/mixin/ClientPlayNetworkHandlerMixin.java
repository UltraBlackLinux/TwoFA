package me.ultrablacklinux.twofa.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import me.ultrablacklinux.twofa.util.Login;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow private CommandDispatcher<CommandSource> commandDispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile, CallbackInfo ci) {
        commandDispatcher.register(literal("twofa"));
    }

    @Inject(method = "onCommandTree", at = @At("RETURN"))
    private void onCommandTree(CommandTreeS2CPacket packet, CallbackInfo ci) {
        commandDispatcher.register(literal("twofa"));
    }

    @Inject(method = "onTitle", at = @At("HEAD"))
    private void onTitle(TitleS2CPacket packet, CallbackInfo ci) {
        if (packet.getAction() == TitleS2CPacket.Action.TITLE || packet.getAction() == TitleS2CPacket.Action.SUBTITLE) {
            if (packet.getText() != null && packet.getText().getString().contains("/login")) {
                Login.login();
            }
        }
    }
}
