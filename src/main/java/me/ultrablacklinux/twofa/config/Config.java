package me.ultrablacklinux.twofa.config;


import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.HashMap;

@SuppressWarnings("unused")
@me.shedaniel.autoconfig.annotation.Config(name = "twofa")
@me.shedaniel.autoconfig.annotation.Config.Gui.Background("minecraft:textures/block/light_gray_concrete.png")
public class Config extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    @ConfigEntry.Gui.PrefixText()
    public General general = new General();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = false)
    public Registration registration = new Registration();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = false)
    public Login login = new Login();


    public enum SavingOptions {
        ALWAYS, NEVER, WHITELIST, BLACKLIST
    }

    public static void init() { AutoConfig.register(Config.class, PartitioningSerializer.wrap(GsonConfigSerializer::new)); }

    public static Config get() {
        return AutoConfig.getConfigHolder(Config.class).getConfig();
    }


    @me.shedaniel.autoconfig.annotation.Config(name = "general")
    public static class General implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public SavingOptions savingOptions = SavingOptions.ALWAYS;

        @Comment("Only applies, when \"Whitelist/Blacklist\" is enabled ")
        public String servers = "";
    }


    @me.shedaniel.autoconfig.annotation.Config(name = "registration")
    public static class Registration implements ConfigData {
        @ConfigEntry.Gui.Excluded
        public HashMap<String, JsonObject> data = new HashMap<>(); //Username(Server, Password) - 1 all details, 2 username, 3 server + pass

        public boolean autoRegister = true;
        public boolean random = true;
        public String randomChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@#$%&*_-";
        @Comment("Only applies, if \"Random Password\" is disabled")
        public String defaultPass = "";

        @ConfigEntry.BoundedDiscrete(min = 1, max = 122)
        public int randomLength = 8;
    }

    @me.shedaniel.autoconfig.annotation.Config(name = "login")
    public static class Login implements ConfigData {
        public boolean autoLogin = true;
    }




    @me.shedaniel.autoconfig.annotation.Config(name = "misc")
    public static class Misc implements ConfigData {
        public String itemSeparator = ";";
    }


}




