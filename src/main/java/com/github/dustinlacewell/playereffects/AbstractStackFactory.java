package com.github.dustinlacewell.playereffects;

import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class AbstractStackFactory {
    abstract int getValue(PotionEffect effect);
    abstract void leftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier);
    abstract void leftShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier);
    abstract void rightClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier);
    abstract void rightShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier);

    public StaticGuiElement create(Player target, char slot, ItemStack stack, Material material, PotionEffectType type, String... lore) {
        var effect = target.getPotionEffect(type);
        var value = effect == null ? 0 : this.getValue(effect);
        stack.setAmount(value);
        return new StaticGuiElement(slot, stack, click -> {
            var clickType = click.getType();
            var isShiftClick = clickType.isShiftClick();
            var currentEffect = target.getPotionEffect(type);
            var currentDuration = currentEffect == null ? 0 : currentEffect.getDuration();
            var currentAmplifier = currentEffect == null ? 0 : currentEffect.getAmplifier();
            target.removePotionEffect(type);
            if (clickType.isLeftClick()) {
                if (isShiftClick) {
                    leftShiftClick(target, type, currentDuration, currentAmplifier);
                } else {
                    leftClick(target, type, currentDuration, currentAmplifier);
                }

            } else {
                if (isShiftClick) {
                    rightShiftClick(target, type, currentDuration, currentAmplifier);
                } else {
                    rightClick(target, type, currentDuration, currentAmplifier);
                }
            }
            return true;
        }, lore);
    }
}
