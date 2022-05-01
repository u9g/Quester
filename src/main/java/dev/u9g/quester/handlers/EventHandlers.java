package dev.u9g.quester.handlers;

import dev.u9g.quester.Quester;
import dev.u9g.quester.data.QuestType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class EventHandlers implements Listener {
    @EventHandler
    private void onKillCow(EntityDeathEvent e) {
        if (!e.getEntityType().equals(EntityType.COW)) return;
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent)e.getEntity().getLastDamageCause()).getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;
                Quester.INSTANCE.database.incrColumn(player, QuestType.KILL_TEN_COWS);
            }
        }
    }

    private boolean hasChangedBlock(PlayerMoveEvent e) {
        return (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) && e.getFrom().getWorld().equals(e.getTo().getWorld());
    }

    @EventHandler
    private void onWalk(PlayerMoveEvent e) {
        if (hasChangedBlock(e)) {
            Quester.INSTANCE.database.incrColumn(e.getPlayer(), QuestType.WALK_500_BLOCKS);
        }
    }

    private boolean caughtFish(Entity fish) {
        ItemStack item = ((Item) fish).getItemStack();
        return item.getType() == Material.RAW_FISH;
    }

    @EventHandler
    private void onCatchFish(PlayerFishEvent e) {
        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) && caughtFish(e.getCaught())) {
            Quester.INSTANCE.database.incrColumn(e.getPlayer(), QuestType.CATCH_30_FISH);
        }
    }

    @EventHandler
    private void onBreakBlock(BlockBreakEvent e) {
        Quester.INSTANCE.database.incrColumn(e.getPlayer(), QuestType.BREAK_50_BLOCKS);
    }

    @EventHandler
    private void onPlaceBlock(BlockPlaceEvent e) {
        Quester.INSTANCE.database.incrColumn(e.getPlayer(), QuestType.PLACE_50_BLOCKS);
    }

    @EventHandler
    private void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            Quester.INSTANCE.database.incrColumn(e.getPlayer(), QuestType.CONSUME_20_GOLDEN_APPLES);
        }
    }

    @EventHandler
    private void onBrew(InventoryClickEvent e) {
        if (e.getClickedInventory() != null &&
            e.getClickedInventory().equals(e.getView().getTopInventory()) &&
            e.getClickedInventory().getType().equals(InventoryType.BREWING) &&
            e.getClick().isLeftClick() && e.getCursor().getType().equals(Material.AIR) &&
            (e.getRawSlot() == 0 || e.getRawSlot() == 1 || e.getRawSlot() == 2)) {
        Quester.INSTANCE.database.incrColumn((Player) e.getWhoClicked(), QuestType.BREW_1_STR_POTION);
        }
    }

    @EventHandler
    private void onEnchant(EnchantItemEvent e) {
        e.getEnchantsToAdd().computeIfPresent(Enchantment.DAMAGE_ALL, (ench, lvl) -> {
            if (lvl == 1) {
                Quester.INSTANCE.database.incrColumn(e.getEnchanter(), QuestType.ENCHANT_DIAM_SWORD_WITH_SHARP_1);
            }
            return lvl;
        });
    }

    @EventHandler
    private void onCraftCompass(CraftItemEvent e) {
        if (e.getRecipe().getResult().getType().equals(Material.COMPASS)) {
            Quester.INSTANCE.database.incrColumn((Player) e.getWhoClicked(), QuestType.CRAFT_1_COMPASS);
        }
    }
}
