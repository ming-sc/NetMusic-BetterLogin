package com.github.img.netmusicbetterlogin.command;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.api.NeteaseMusicLevel;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.server.command.EnumArgument;

public class ServerLoginCommand {
    private static final String ROOT = "nmbl";
    private static final String SERVER = "server";
    private static final String COOKIE = "cookie";
    private static final String CLEAR = "clear";
    private static final String RELOAD = "reload";
    private static final String LEVEL = "level";
    private static final String SET = "set";

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(ROOT);
        LiteralArgumentBuilder<CommandSourceStack> server = Commands.literal(SERVER);
        LiteralArgumentBuilder<CommandSourceStack> cookie = Commands.literal(COOKIE);
        LiteralArgumentBuilder<CommandSourceStack> clear = Commands.literal(CLEAR);
        LiteralArgumentBuilder<CommandSourceStack> reload = Commands.literal(RELOAD);
        LiteralArgumentBuilder<CommandSourceStack> level = Commands.literal(LEVEL);
        LiteralArgumentBuilder<CommandSourceStack> set = Commands.literal(SET);
        RequiredArgumentBuilder<CommandSourceStack, NeteaseMusicLevel> levelArgument = Commands.argument(LEVEL, EnumArgument.enumArgument(NeteaseMusicLevel.class));

        server.requires(source -> source.hasPermission(2));

        cookie.then(clear.executes(context -> {
            NetMusicBetterLogin.NETEASE_API.setCookie("");
            GeneralConfig.COOKIE.set("");
            GeneralConfig.COOKIE.save();
            context.getSource().sendSystemMessage(Component.translatable("command.netmusicbetterlogin.cookie.clear.success"));
            return Command.SINGLE_SUCCESS;
        }));
        cookie.then(reload.executes(context -> {
            try {
                GeneralConfig.reload();
                context.getSource().sendSystemMessage(Component.translatable("command.netmusicbetterlogin.cookie.reload.success"));
            } catch (Exception e) {
                context.getSource().sendSystemMessage(Component.translatable("command.netmusicbetterlogin.cookie.reload.fail"));
                NetMusicBetterLogin.LOGGER.error("Failed to reload config", e);
            }
            return Command.SINGLE_SUCCESS;
        }));

        level.then(set.then(levelArgument.executes(context -> {
            NeteaseMusicLevel levelValue = context.getArgument(LEVEL, NeteaseMusicLevel.class);
            GeneralConfig.LEVEL.set(levelValue);
            GeneralConfig.LEVEL.save();
            context.getSource().sendSystemMessage(Component.translatable("command.netmusicbetterlogin.level.set.success", Component.translatable(levelValue.getTranslationKey())));
            return Command.SINGLE_SUCCESS;
        })));

        level.executes(context -> {
            NeteaseMusicLevel currentLevel = GeneralConfig.LEVEL.get();
            context.getSource().sendSystemMessage(Component.translatable("command.netmusicbetterlogin.level.current", Component.translatable(currentLevel.getTranslationKey())));
            return Command.SINGLE_SUCCESS;
        });

        server.then(cookie);
        server.then(level);
        root.then(server);

        return root;
    }
}
