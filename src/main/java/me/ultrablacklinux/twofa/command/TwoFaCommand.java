package me.ultrablacklinux.twofa.command;

import me.ultrablacklinux.twofa.util.Account;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.text.Text;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.*;


public class TwoFaCommand {
    static MinecraftClient client = MinecraftClient.getInstance();
    public static void registerCommands() {
        ClientCommandManager.DISPATCHER.register(literal("twofa")
                .then(literal("help").executes(ctx -> commandManager(-1, "")))
                .then(literal("add").then(argument("password", MessageArgumentType.message())
                        .executes(ctx -> commandManager(0, ctx.getInput()))))
                .then(literal("delete").executes(ctx -> commandManager(1, "")))
                .then(literal("login").executes(ctx -> commandManager(2, "")))
                .then(literal("register").executes(ctx -> commandManager(3, "")))
                .then(literal("addtoList").executes(ctx -> commandManager(4, ""))));
    }

    private static int commandManager(int action, String args) {
        if (client.isInSingleplayer()) {
            client.player.sendMessage(Text.of("[TwoFA] §cYou can't run this in singleplayer!"), false);
            return 0;
        }

        switch (action) {
            case -1: //help
                client.player.sendMessage(Text.of("§e----TwoFA Help----\n" +
                        "§e/twofa addToList: Adds the current server to the settings\n" +
                        "§e/twofa login: Logs You in\n" +
                        "§e/twofa register: Registers You\n" +
                        "§e/twofa delete: Deletes current server's entry\n" +
                        "§e/twofa add <password>: Adds an entry for the current server"), false);
                break;

            case 0: //add
                try {
                    Account.addEntry(args.split(" ")[2].replace(" ", ""));
                } catch (ArrayIndexOutOfBoundsException ingore) {}
                break;

            case 1: //delete
                Account.removeEntry(client.getCurrentServerEntry().address);
                break;

            case 2: //login
                Account.login(true);
                break;

            case 3: //register
                client.player.sendMessage(Text.of("[TwoFA] §cYou can't run that in singleplayer!"), false);
                break;

            case 4: //addtolist
                Account.addToList();
                break;
        }
        return 1;

    }
}