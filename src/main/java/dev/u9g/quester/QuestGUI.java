package dev.u9g.quester;

import dev.u9g.quester.data.QuestType;
import dev.u9g.quester.util.ItemBuilder;
import dev.u9g.quester.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import redempt.redlib.inventorygui.InventoryGUI;

import java.util.Collections;

public class QuestGUI {
    public static void openGUIFor(Player player) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Quests"));
        gui.open(player);
        Inventory inv = gui.getInventory();
        int i = 0;
        for (QuestType quest : QuestType.values()) {
            int finalI = i;
            Quester.INSTANCE.database.getCount(player, quest).thenAccept(count -> {
                String colorOfText = count >= quest.getGoal() ? "<green>" : "<gold>";
                inv.setItem(finalI, ItemBuilder.from(quest.getIcon())
                        .name(quest.getTitle())
                        .lore(Collections.singletonList(Util.mm("<white>Progress<gray>: "+ colorOfText + count + " <gray>/ <green>" + quest.getGoal())))
                        .build());
            });
            i++;
        }
    }
}
