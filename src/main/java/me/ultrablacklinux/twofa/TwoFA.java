package me.ultrablacklinux.twofa;

import me.ultrablacklinux.twofa.config.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwoFA implements ModInitializer {
    Logger logger = LogManager.getLogger();
    @Override
    public void onInitialize() {
        Config.init();
        logger.info("[TwoFA] Started");
    }
}
