package me.ultrablacklinux.twofa;

import me.ultrablacklinux.twofa.command.TwoFaCommand;
import me.ultrablacklinux.twofa.config.Config;
import me.ultrablacklinux.twofa.config.VersionManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwoFA implements ModInitializer {
    Logger logger = LogManager.getLogger();
    public static int version = 3;
    @Override
    public void onInitialize() {
        Config.init();
        VersionManager.init();
        TwoFaCommand.registerCommands();
        logger.info("[TwoFA] Started");
    }
}
