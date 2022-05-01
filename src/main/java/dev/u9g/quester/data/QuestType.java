package dev.u9g.quester.data;

import dev.u9g.quester.util.ItemBuilder;
import dev.u9g.quester.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum QuestType {
    KILL_TEN_COWS(Util.mm("<dark_red>Kill 10 cows"), Material.RAW_BEEF, "kill_cows", 10),
    WALK_500_BLOCKS(Util.mm("<green>Walk 500 blocks"), Material.GRASS, "walk_blocks", 500),
    CATCH_30_FISH(Util.mm("<dark_green>Catch 30 fish"), Material.RAW_FISH, "catch_fish", 30),
    BREAK_50_BLOCKS(Util.mm("<light_purple>Break 50 blocks"), Material.DIAMOND_PICKAXE, "break_blocks", 50),
    PLACE_50_BLOCKS(Util.mm("<aqua>Place 50 blocks"), Material.WORKBENCH, "place_blocks", 50),
    CONSUME_20_GOLDEN_APPLES(Util.mm("<yellow>Consume 20 golden apples"), Material.GOLDEN_APPLE, "craft_golden_apples", 20),
    BREW_1_STR_POTION(Util.mm("<dark_purple>Brew a strength potion"), Util.STRENGTH_POTION, "brew_str_potion", 1),
    ENCHANT_DIAM_SWORD_WITH_SHARP_1(Util.mm("<blue>Enchant a diamond sword with sharpness 1"), Util.SWORD_WITH_SHARPNESS_1, "ench_diam_sword_with_sharp_1", 1),
    CRAFT_1_COMPASS(Util.mm("<dark_gray>Craft a compass"), Material.COMPASS, "craft_compass", 1),;

    private final Component title;
    private final ItemStack icon;
    private final String nameInDatabase;
    private final int goal;
    QuestType(Component title, Material icon, String nameInDatabase, int goal) {
        this.title = title;
        this.icon = ItemBuilder.of(icon).build();
        this.nameInDatabase = nameInDatabase;
        this.goal = goal;
    }

    QuestType(Component title, ItemStack icon, String nameInDatabase, int goal) {
        this.title = title;
        this.icon = icon;
        this.nameInDatabase = nameInDatabase;
        this.goal = goal;
    }

    public Component getTitle() {
        return title;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public int getGoal() {
        return goal;
    }

    public String getDBCol() {
        return nameInDatabase;
    }
}
