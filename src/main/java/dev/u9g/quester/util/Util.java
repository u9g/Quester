package dev.u9g.quester.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Util {
    public static String toRaw (Component component) {
        return LegacyComponentSerializer.legacy(LegacyComponentSerializer.SECTION_CHAR).serialize(component);
    }

    public static Component mm (String msg) {
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public static ItemStack SWORD_WITH_SHARPNESS_1 = ItemBuilder.of(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build();
    // https://dev.bukkit.org/projects/etshop/pages/potion-data-value-table
    public static ItemStack STRENGTH_POTION = new ItemStack(Material.POTION, 1, (short)8201);
}
