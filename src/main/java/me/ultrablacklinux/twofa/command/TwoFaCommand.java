package me.ultrablacklinux.twofa.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.ultrablacklinux.twofa.util.Account;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;


public class TwoFaCommand implements ClientCommandPlugin {
    MinecraftClient client = MinecraftClient.getInstance();
    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        dispatcher.register(literal("twofa")
                .then(literal("delete").executes(ctx -> {
                    if (client.isInSingleplayer()) {
                        client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                    }
                    else {
                        Account.removeEntry(client.getCurrentServerEntry().address);
                    }
                    return 1;
                }))
                .then(literal("add").executes(ctx -> {
                    if (client.isInSingleplayer()) {
                        client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                    }
                    else {
                        System.out.println(ctx.getInput());
                        //Account.addEntry(client.getCurrentServerEntry().address);
                    }
                    return 1;
                }))
                .then(literal("register").executes(ctx -> {
                    if (client.isInSingleplayer()) {
                        client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                    }
                    else {
                        Account.register("", true);
                    }
                    return 1;
                }))
                .then(literal("login").executes(ctx -> {
                    if (client.isInSingleplayer()) {
                        client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                    }
                    else {
                        Account.login(true);
                    }
                    return 1;
                }))
                .then(literal("addtoList").executes(ctx -> {
                    if (client.isInSingleplayer()) {
                        client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                    }
                    else {
                        Account.addToList();
                    }
                    return 1;
                }))
                .then(literal("help").executes(ctx -> {
                    client.player.sendMessage(Text.of("§e----TwoFA Help----\n" +
                            "§e/twofa addToList: Adds the current server to the settings\n" +
                            "§e/twofa login: Logs You in\n" +
                            "§e/twofa register: Registers You\n" +
                            "§e/twofa delete: Deletes current server's entry\n"), false);
                    return 1;
                })));
    }
}