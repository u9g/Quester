package dev.u9g.quester.handlers;

import dev.u9g.quester.QuestGUI;
import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

public class QuestCommandHandler {
    @CommandHook("quests")
    public void onQuestCommand(Player player) {
        QuestGUI.openGUIFor(player);
    }
}
