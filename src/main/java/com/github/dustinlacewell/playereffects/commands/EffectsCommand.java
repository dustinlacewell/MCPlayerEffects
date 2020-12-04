package com.github.dustinlacewell.playereffects.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.github.dustinlacewell.playereffects.PlayerEffects;
import com.github.dustinlacewell.playereffects.menus.EffectsMenu;
import com.github.dustinlacewell.playereffects.menus.PlayerMenu;
import org.bukkit.entity.Player;

@CommandAlias("pe")
public class EffectsCommand extends BaseCommand {

    private final PlayerEffects plugin;

    public EffectsCommand(PlayerEffects plugin) {
        this.plugin = plugin;
    }

    public static void doCmd(PlayerEffects plugin, Player player, OnlinePlayer target) {
        if (target == null) {
            var playerMenu = new PlayerMenu(plugin);
            playerMenu.Show(player, "Select a player:", selectedPlayer -> {
                var effectsMenu = new EffectsMenu(plugin, selectedPlayer);
                var title = String.format("%s's effects:", selectedPlayer.getName());
                effectsMenu.Show(player, title);
            });
        } else {
            var effectsMenu = new EffectsMenu(plugin, target.getPlayer());
            var title = String.format("%s's effects:", target.getPlayer().getName());
            effectsMenu.Show(player, title);
        }
    }

    @Default
    @Description("Manage player status effects")
    @CommandCompletion("@players")
    public void onCmd(Player player, @Optional OnlinePlayer target) {
        EffectsCommand.doCmd(this.plugin, player, target);
    }
}
