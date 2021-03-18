package me.ultrablacklinux.twofa.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.ultrablacklinux.twofa.TwoFA;
import me.ultrablacklinux.twofa.util.Util;
import java.util.Map;

public class VersionManager {
    static Integer version;
    public static void init() {
        version = Config.get().general.version;
        if (version == 0) {
            version = 2;
        }

        if (version == 2) {
            passToBase();
            version = 3;
        }

        Config.get().general.version = TwoFA.version;
        Config.save();
    }

    private static void passToBase() {
        version = 3;
        for (Map.Entry<String, JsonObject> entry : Config.get().registration.data.entrySet()) {
            JsonObject server = new JsonObject();
            entry.getValue().keySet().forEach(key -> { //for each server in the player entry
                server.add(key, new JsonPrimitive(Util.toBase(entry.getValue().get(key).getAsString())));
            });
            Config.get().registration.data.replace(entry.getKey(), server);
        }
    }
}