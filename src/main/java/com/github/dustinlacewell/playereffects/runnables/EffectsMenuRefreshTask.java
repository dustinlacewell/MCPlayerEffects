package com.github.dustinlacewell.playereffects.runnables;

import de.themoep.inventorygui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectsMenuRefreshTask extends BukkitRunnable {

    private final InventoryGui gui;
    private final JavaPlugin plugin;
    private final Player player;

    public EffectsMenuRefreshTask(JavaPlugin plugin, Player player, InventoryGui gui) {
        this.plugin = plugin;
        this.player = player;
        this.gui = gui;
    }

    @Override
    public void run() {
        if (this.gui.equals(InventoryGui.getOpen(this.player))) {
            this.gui.draw(this.player);
        } else {
            this.cancel();
        }
    }
}

