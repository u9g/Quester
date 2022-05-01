package dev.u9g.quester.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack item;
    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public static ItemBuilder of (Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder from (ItemStack item) {
        return new ItemBuilder(item);
    }

    private void setItemMeta (Consumer<ItemMeta> consumer) {
        consumer.andThen(item::setItemMeta).accept(item.getItemMeta());
    }

    public ItemBuilder count (int count) {
        item.setAmount(count);
        return this;
    }

    public ItemBuilder name (Component component) {
        setItemMeta(im -> {
            Component c = component.colorIfAbsent(NamedTextColor.WHITE);
            if (!c.hasDecoration(TextDecoration.ITALIC)) {
                c = c.decoration(TextDecoration.ITALIC, false);
            }
            im.setDisplayName(Util.toRaw(c));
        });
        return this;
    }

    public ItemBuilder lore (List<Component> components) {
        List<String> loreLines = components.stream().map(c -> {
            c = c.colorIfAbsent(NamedTextColor.WHITE);
            if (!c.hasDecoration(TextDecoration.ITALIC)) {
                c = c.decoration(TextDecoration.ITALIC, false);
            }
            return Util.toRaw(c);
        }).collect(Collectors.toList());
        setItemMeta(im -> im.setLore(loreLines));
        return this;
    }

    public ItemBuilder enchant (Enchantment enchantment, int level) {
        setItemMeta(im -> {
            im.addEnchant(enchantment, level, true);
        });
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
