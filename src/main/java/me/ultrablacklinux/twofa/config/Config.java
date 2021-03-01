package me.ultrablacklinux.twofa.config;


import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
@me.shedaniel.autoconfig.annotation.Config(name = "twofa")
public class Config extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("TypoPrevention")
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public General general = new General();


    public static void init() { AutoConfig.register(Config.class, PartitioningSerializer.wrap(GsonConfigSerializer::new)); }

    public static Config get() {
        return AutoConfig.getConfigHolder(Config.class).getConfig();
    }

    @me.shedaniel.autoconfig.annotation.Config(name = "general")
    public static class General implements ConfigData {
        public ArrayList<HashMap<String, HashMap<String, String>>> details = new ArrayList<>(); //Username(Server, Password) - 1 all details, 2 username, 3 server + pass
    }


}




