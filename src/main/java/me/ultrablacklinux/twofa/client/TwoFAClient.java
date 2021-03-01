package me.ultrablacklinux.twofa.client;

import me.ultrablacklinux.twofa.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class TwoFAClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }
}
