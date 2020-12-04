package com.github.dustinlacewell.playereffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DurationStackFactory extends AbstractStackFactory {

    int getValue(PotionEffect effect) {
        return Math.max(0, Math.min(64, effect.getDuration() / 20));
    }

    void leftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        var duration = currentDuration + 5 * 20;
        target.addPotionEffect(new PotionEffect(type, duration, currentAmplifier));
    }

    void leftShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        target.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, currentAmplifier));
    }

    void rightClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        var duration = currentDuration - 5 * 20;
        target.addPotionEffect(new PotionEffect(type, duration, currentAmplifier));
    }

    void rightShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) { }
}
