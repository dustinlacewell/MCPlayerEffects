package com.github.dustinlacewell.playereffects.menus;

import com.github.dustinlacewell.playereffects.AmplifierStackFactory;
import com.github.dustinlacewell.playereffects.DurationStackFactory;
import com.github.dustinlacewell.playereffects.PlayerEffects;
import com.github.dustinlacewell.playereffects.commands.EffectsCommand;
import com.github.dustinlacewell.playereffects.runnables.EffectsMenuRefreshTask;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiStateElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.stream.Collectors;


public class EffectsMenu {
    public enum Mode { DURATION, AMPLIFIER }
    protected final PlayerEffects plugin;
    private final Player targetPlayer;
    private Mode mode;
    private final DurationStackFactory durationStackFactory = new DurationStackFactory();
    private final AmplifierStackFactory amplifierStackFactory = new AmplifierStackFactory();

    public EffectsMenu(PlayerEffects plugin, Player targetPlayer) {
        this.plugin = plugin;
        this.targetPlayer = targetPlayer;
        this.mode = Mode.DURATION;
    }

    protected InventoryGui getGUI(String title) {
        var layout = this.getLayout();
        return new InventoryGui(this.plugin, title, layout);
    }

    protected DynamicGuiElement getEffectSelector(char selector, Material material, PotionEffectType type, String... lore) {
        return new DynamicGuiElement(selector, viewer -> {
            var name = type.getName();
            var parts = Arrays.stream(name.split("_")).map(str -> str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()).collect(Collectors.toList());
            name = String.join(" ", parts);
            var stack = new ItemStack(material);
            var meta = stack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            stack.setItemMeta(meta);
            var newLore = Arrays.copyOf(lore, lore.length + 1);
            newLore[0] = name;
            System.arraycopy(lore, 0, newLore, 1, lore.length);

            if (this.mode == Mode.DURATION) {
                return this.durationStackFactory.create(this.targetPlayer, selector, stack, material, type, newLore);
            } else {
                return this.amplifierStackFactory.create(this.targetPlayer, selector, stack, material, type, newLore);
            }
        });
    }

    protected String[] getLayout() {
        return new String[] {
                "abBcCdDf ",
                "FghHtvGi ",
                "IjlLnprT ",
                "sSAPuwWR ",
                "         ",
                "?      xX",
        };
    }

    protected StaticGuiElement createCloseStack(InventoryGui gui) {
        return new StaticGuiElement('X', new ItemStack(Material.BARRIER), click -> {
            gui.close();
            return true;
        }, "Close");
    }

    protected StaticGuiElement createBackStack(InventoryGui gui, Player player) {
        return new StaticGuiElement('x', new ItemStack(Material.STRUCTURE_VOID), click -> {
            gui.close();
            EffectsCommand.doCmd(this.plugin, player, null);
            return true;
        }, "Players", "Select a different Player");
    }

    protected GuiStateElement createStateStack() {
        return new GuiStateElement('?',
                new GuiStateElement.State(
                        change -> {
                            this.mode = Mode.DURATION;
                            change.getGui().draw();
                        },
                        "durationMode",
                        new ItemStack(Material.CLOCK), // the item to display as an icon
                        "Duration Mode", // explanation text what this element does
                        "Click to show effect amplifiers instead."
                ),
                new GuiStateElement.State(
                        change -> {
                            this.mode = Mode.AMPLIFIER;
                            change.getGui().draw();
                        },
                        "amplifierMode",
                        new ItemStack(Material.REDSTONE),
                        "Amplifier Mode",
                        "Click to show effect durations instead."
                )
        );
    }

    public boolean Show(Player player, String title) {
        var gui = this.getGUI(title);

        gui.addElement(this.getEffectSelector('a', Material.IRON_BLOCK, PotionEffectType.ABSORPTION,
                "Gives hearts that don't heal. :'("));
        gui.addElement(this.getEffectSelector('b', Material.ENDER_PEARL, PotionEffectType.BAD_OMEN,
                "Cause a raid upon entering a village."));
        gui.addElement(this.getEffectSelector('B', Material.ENDER_EYE, PotionEffectType.BLINDNESS,
                 "Make things very dark."));
        gui.addElement(this.getEffectSelector('c', Material.BEACON, PotionEffectType.CONDUIT_POWER,
                "- Underwater sight ",
                "- Mining speed",
                "- Can't drown"));
        gui.addElement(this.getEffectSelector('C', Material.PLAYER_HEAD, PotionEffectType.CONFUSION,
                "Wobbly distortions."));
        gui.addElement(this.getEffectSelector('d', Material.DIAMOND_CHESTPLATE, PotionEffectType.DAMAGE_RESISTANCE,
                "Less damage taken."));
        gui.addElement(this.getEffectSelector('D', Material.TROPICAL_FISH, PotionEffectType.DOLPHINS_GRACE,
                "Swim faster."));
        gui.addElement(this.getEffectSelector('f', Material.DIAMOND_SHOVEL, PotionEffectType.FAST_DIGGING,
                "Dig faster."));

        gui.addElement(this.getEffectSelector('F', Material.MAGMA_CREAM, PotionEffectType.FIRE_RESISTANCE,
                "Less fire-damage taken."));
        gui.addElement(this.getEffectSelector('g', Material.GLOWSTONE, PotionEffectType.GLOWING,
                
                        "- Glow with outline",
                        "- Seen through walls"));
        gui.addElement(this.getEffectSelector('h', Material.SWEET_BERRIES, PotionEffectType.HARM,
                "Take damage."));
        gui.addElement(this.getEffectSelector('H', Material.MELON, PotionEffectType.HEAL,
                "Heal some hearts :)"));
        gui.addElement(this.getEffectSelector('t', Material.GOLDEN_APPLE, PotionEffectType.HEALTH_BOOST,
                "Increased max health."));
        gui.addElement(this.getEffectSelector('v', Material.VILLAGER_SPAWN_EGG, PotionEffectType.HERO_OF_THE_VILLAGE,
                "Gifts and discounts from villagers."));
        gui.addElement(this.getEffectSelector('G', Material.BREAD, PotionEffectType.HUNGER,
                "Increased hunger."));
        gui.addElement(this.getEffectSelector('i', Material.DIAMOND_SWORD, PotionEffectType.INCREASE_DAMAGE,
                "Do more damage."));

        gui.addElement(this.getEffectSelector('I', Material.GLASS, PotionEffectType.INVISIBILITY,
                "Can't be seen."));
        gui.addElement(this.getEffectSelector('j', Material.SLIME_BLOCK, PotionEffectType.JUMP,
                "Jump higher."));
        gui.addElement(this.getEffectSelector('l', Material.FEATHER, PotionEffectType.LEVITATION,
                "Raise into the air."));
        gui.addElement(this.getEffectSelector('L', Material.RABBIT_FOOT, PotionEffectType.LUCK,
                "Better loot."));
        gui.addElement(this.getEffectSelector('n', Material.GOLDEN_CARROT, PotionEffectType.NIGHT_VISION,
                "See in the dark."));
        gui.addElement(this.getEffectSelector('p', Material.SPIDER_EYE, PotionEffectType.POISON,
                "Take damage over time."));
        gui.addElement(this.getEffectSelector('r', Material.GHAST_TEAR, PotionEffectType.REGENERATION,
                "Heal over time."));
        gui.addElement(this.getEffectSelector('T', Material.POTATOES, PotionEffectType.SATURATION,
                "Recover hunger over time."));

        gui.addElement(this.getEffectSelector('s', Material.COBWEB, PotionEffectType.SLOW,
                "Move slower."));
        gui.addElement(this.getEffectSelector('S', Material.WOODEN_SHOVEL, PotionEffectType.SLOW_DIGGING,
                "Dig slower."));
        gui.addElement(this.getEffectSelector('A', Material.STRING, PotionEffectType.SLOW_FALLING,
                "Fall slower."));
        gui.addElement(this.getEffectSelector('P', Material.GOLDEN_BOOTS, PotionEffectType.SPEED,
                "Move faster."));
        gui.addElement(this.getEffectSelector('u', Material.RABBIT_HIDE, PotionEffectType.UNLUCK,
                "Worse loot."));
        gui.addElement(this.getEffectSelector('w', Material.PUFFERFISH, PotionEffectType.WATER_BREATHING,
                "Breathe underwater."));
        gui.addElement(this.getEffectSelector('W', Material.WOODEN_SWORD, PotionEffectType.WEAKNESS,
                "Do less damage."));
        gui.addElement(this.getEffectSelector('R', Material.WITHER_ROSE, PotionEffectType.WITHER,
                "Take damage over time."));

        gui.addElement(this.createStateStack());
        gui.addElement(this.createCloseStack(gui));
        gui.addElement(this.createBackStack(gui, player));

        gui.build();
        gui.show(player);
        new EffectsMenuRefreshTask(this.plugin, player, gui).runTaskTimer(this.plugin, 0, 20);
        return true;
    }
}
