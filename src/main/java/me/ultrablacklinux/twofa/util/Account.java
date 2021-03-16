package me.ultrablacklinux.twofa.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.shedaniel.autoconfig.AutoConfig;
import me.ultrablacklinux.twofa.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.util.*;

public class Account {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean noSelfChat = false;

    public static void getMessage(String chat) {
        if (chat.contains("/login") && Config.get().login.autoLogin) {
            Account.login(false);
        }
        else if (chat.contains("/register") && Config.get().registration.autoRegister) {
            String out = "";
            if (chat.contains("captcha")) {
                String[] captcha = chat.split(" ");
                out = captcha[captcha.length-1];
            }
            Account.register(out, false);
        }
    }

    public static void login(boolean manually) {
        if (!regLogIf(manually)) return;

        JsonObject server = Config.get().registration.data.getOrDefault(client.player.getName().getString(), null);

        if (server != null && server.has(client.getCurrentServerEntry().address)) {
            client.player.sendMessage(Text.of("[TwoFA] §aLogging in..."), false);
            client.player.sendChatMessage("/login " + server.get(client.getCurrentServerEntry().address).getAsString());
            System.out.println(server.get(client.getCurrentServerEntry().address).getAsString());
        }
        else {
            client.player.sendMessage(Text.of("[TwoFA] §cNo entry found"), false);
        }
    }


    public static void register(String captcha, boolean manually) {
        if (!regLogIf(manually)) return;

        try { //exit if server already exists
            if (Config.get().registration.data.get(client.player.getName().getString()).toString()
                    .contains(client.getCurrentServerEntry().address)) return;
        } catch (NullPointerException ignore) {}

        String password = Config.get().registration.defaultPass;

        if (Config.get().registration.random) { //generate a random password
            ArrayList<String> tmppass = new ArrayList<>();
            for (int i = 0; i < Config.get().registration.randomLength; i++) {
                ArrayList<String> chars = new ArrayList<>(Arrays.asList(Config.get().registration.randomChars.split("")));
                Collections.shuffle(chars);
                tmppass.add(chars.get(0));
            }
            password = String.join("", tmppass.toArray(new String[tmppass.size()]));
        }
        noSelfChat = true;
        //use and register the password
        addEntry(password);
        client.player.sendChatMessage("/register" + " " + password + " " + password + " " + captcha);
        noSelfChat = false;
    }


    public static void addEntry(String password) {
        JsonObject oldEntries;

        /*
         Thank you for opening my eyes <3
         @Zandra#5962
         @lylythechosenone#2734
         */

        oldEntries = Config.get().registration.data.getOrDefault(client.player.getName().getString(), null); //the more you know
        if (oldEntries == null) {
            oldEntries = new JsonObject();
            Config.get().registration.data.put(client.player.getName().getString(), null);
        }
        oldEntries.add(client.getCurrentServerEntry().address, new JsonPrimitive(password)); //add current server with password
        Config.get().registration.data.replace(client.player.getName().getString(), oldEntries); //replace the old entry with a new one
        System.out.println(Config.get().registration.data.get(client.player.getName().getString()));
        AutoConfig.getConfigHolder(Config.class).save(); //save config
        client.player.sendMessage(Text.of("[TwoFA] §aSaved credentials!"), false);
    }



    public static void removeEntry(String server) {
        try {
            Config.get().registration.data.get(client.player.getName().getString()).remove(server);
            AutoConfig.getConfigHolder(Config.class).save(); //save config
            client.player.sendMessage(Text.of("[TwoFA] §aEntry deleted!"), false);
        } catch (NullPointerException e) {
            client.player.sendMessage(Text.of("[TwoFA] §cNo entry found"), false);
        }
    }

    public static boolean regLogIf(boolean manually) {
        if (manually) return true;
        Config.SavingOptions a = Config.get().general.savingOptions;
        switch(a) {
            case ALWAYS:
                return true;
            case NEVER:
                return false;
            case WHITELIST:
                if (Config.get().general.servers.contains(client.getCurrentServerEntry().address)) {
                    return true;
                }
            case BLACKLIST:
                if (Config.get().general.servers.contains(client.getCurrentServerEntry().address)) {
                    return false;
                }

        }
        return false; //scuffedness
    }

    public static void addToList() {
        client.player.sendMessage(Text.of("[TwoFa] §aAdded current server to settings"), false);
        if (!Config.get().general.servers.contains(client.getCurrentServerEntry().address)) {
            Config.get().general.servers = Config.get().general.servers + " " + client.getCurrentServerEntry().address;
        }
    }


}