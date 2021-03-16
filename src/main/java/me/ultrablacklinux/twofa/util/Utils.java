package me.ultrablacklinux.twofa.util;


import me.ultrablacklinux.twofa.config.Config;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;


/*
public class Utils  {
    public static String playername;
    private static MinecraftClient client = MinecraftClient.getInstance();

    static String itemSeperator = Config.get().misc.itemSeparator;
    //static String optItemSeperator = Config.get().misc.optItemSeparator;

    public static ArrayList<String> stringALJoiner(ArrayList<ArrayList<String>> input) {
        ArrayList<String> finished = new ArrayList<>();
        for (ArrayList<String> s : input) {
            for (String s2 : s) {
                finished.add(s2.replace(itemSeperator, ""));
            }
        }
        return finished;
    }

    public static ArrayList<String> simpleStringToAL(String input) {
        ArrayList<String> finished = new ArrayList<>();
        for (String s : input.split(itemSeperator)) {
            finished.add(s.replace(itemSeperator, ""));
        }
        return finished;
    }

    public static HashMap<String, String> advancedStringToHM(String input) {
        HashMap<String, String> finished = new HashMap<>();
        try {
            for (String s : input.split(itemSeperator)) {
                String[] split = s.split(optItemSeperator);
                finished.put(split[0].replace(itemSeperator, ""), split[1].replace(optItemSeperator, ""));
            }
        } catch (Exception e) {finished = null;}
        return finished;
    }

}
 */