package me.ultrablacklinux.twofa.util;

import me.ultrablacklinux.twofa.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void login() {
        try {
            for (HashMap<String, HashMap<String, String>> bundle : Config.get().general.details) {  //list: Username(Server, Password) - 1 all details, 2 username, 3 server + pass
                for (Map.Entry<String, HashMap<String, String>> username : bundle.entrySet()) {
                    for (Map.Entry<String, String> lastStep : username.getValue().entrySet()) {
                        if (client.getCurrentServerEntry().address.equals(lastStep.getKey())) {
                            client.player.sendMessage(Text.of("[TwoFA] §aLogging in..."), false);
                            client.player.sendChatMessage("/login " + lastStep.getValue());

                        }
                    }
                }
            }
        } catch (Exception e) {
            client.player.sendMessage(Text.of("[TwoFA] No entry found"), false);
        }
    }
}
