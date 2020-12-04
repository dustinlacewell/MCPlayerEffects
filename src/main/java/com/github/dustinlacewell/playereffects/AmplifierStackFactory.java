package com.github.dustinlacewell.playereffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AmplifierStackFactory extends AbstractStackFactory {
    int normalize(int amplifier) {
        return Math.max(0, Math.min(255, amplifier));
    }

    int getValue(PotionEffect effect) {
        return Math.max(0, Math.min(64, effect.getDuration() / 20));
    }

    void leftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        var amplifier = this.normalize(currentAmplifier + 1);
        target.addPotionEffect(new PotionEffect(type, currentDuration, amplifier));
    }

    void leftShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        target.addPotionEffect(new PotionEffect(type, currentDuration, 255));
    }

    void rightClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        var amplifier = this.normalize(currentAmplifier - 1);
        target.addPotionEffect(new PotionEffect(type, currentDuration, amplifier));
    }

    void rightShiftClick(Player target, PotionEffectType type, int currentDuration, int currentAmplifier) {
        target.addPotionEffect(new PotionEffect(type, currentDuration, 0));
    }
}
