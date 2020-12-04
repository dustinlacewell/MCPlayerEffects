package com.github.dustinlacewell.playereffects;

import co.aikar.commands.PaperCommandManager;
import com.github.dustinlacewell.playereffects.commands.EffectsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerEffects extends JavaPlugin {

    @Override
    public void onEnable() {
        var commands = new PaperCommandManager(this);
        commands.registerCommand(new EffectsCommand(this));
    }
}
