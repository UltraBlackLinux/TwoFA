package me.ultrablacklinux.twofa.mixin;


import me.shedaniel.autoconfig.AutoConfig;
import me.ultrablacklinux.twofa.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(ClientPlayerEntity.class)
public abstract class PlayerMixin {
    MinecraftClient client = MinecraftClient.getInstance();
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(String msg, CallbackInfo info) {
        if (msg.startsWith("/register")) {
            String[] splitMsg = msg.split(" ");
            if (msg.length() <= 1) {return;}
            if (splitMsg[1].equals(splitMsg[2])) {
                HashMap<String, String> credentials = new HashMap<>();
                HashMap<String, HashMap<String, String>> username = new HashMap<>();
                credentials.put(client.getCurrentServerEntry().address, splitMsg[1].replace(" ", ""));
                username.put(client.player.getName().getString(), credentials);
                Config.get().general.details.add(username);
                AutoConfig.getConfigHolder(Config.class).save();
                client.player.sendMessage(Text.of("[TwoFaHelper] Saved credentials!"), false);
            }
        }
        else if (msg.startsWith("/twofa")) {
            info.cancel();
            String[] args = msg.split(" ");
            if (args.length == 2) {
                if (args[1].equals("delete")) {
                    for (HashMap<String, HashMap<String, String>> layer1 : Config.get().general.details) {
                        layer1.get(client.player.getName().getString()).remove(client.getCurrentServerEntry().address);
                        AutoConfig.getConfigHolder(Config.class).save();
                        client.player.sendMessage(Text.of("[TwoFA] Credentials deleted"), false);
                    }
                }
            }
            else if (args.length == 3) {
                if (args[1].equals("add")) {
                    for (HashMap<String, HashMap<String, String>> layer1 : Config.get().general.details) {
                        layer1.get(client.player.getName().getString()).put(client.getCurrentServerEntry().address, args[2]);
                        AutoConfig.getConfigHolder(Config.class).save();
                        client.player.sendMessage(Text.of("[TwoFA] Credentials added"), false);
                    }
                }
            }
            else {
                client.player.sendMessage(Text.of("----TwoFA Help----\n" +
                        "/twofa delete: Deletes current server's entry\n" +
                        "/twpfa add <Password>: Adds an entry to the current server\n"), false);
            }
        }
    }
}