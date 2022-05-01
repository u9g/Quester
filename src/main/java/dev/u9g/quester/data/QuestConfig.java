package dev.u9g.quester.data;

import dev.u9g.quester.Quester;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import redempt.redlib.config.ConfigManager;
import redempt.redlib.config.annotations.ConfigMappable;
import redempt.redlib.config.annotations.ConfigName;

import java.util.HashMap;
import java.util.Map;

@ConfigMappable
public class QuestConfig {
    public Map<String, String> runOnFinish;
    @ConfigName("mongo_db_uri") public String mongoDbUri;

    public static QuestConfig init() {
        QuestConfig questConfig = new QuestConfig();
        Map<String, String> commandsToRun = new HashMap<>();
        for (QuestType quest : QuestType.values()) {
            commandsToRun.put(quest.getDBCol(), "tell %player% You have just finished the '%quest_name%' quest".replace("%quest_name%", quest.getDBCol()));
        }
        questConfig.runOnFinish = commandsToRun;
        questConfig.mongoDbUri = "FILL THIS IN";
        makeConfigManager(questConfig).saveDefaults().load();
        return questConfig;
    }

    private static ConfigManager makeConfigManager(QuestConfig config) {
        final ConfigManager configManager = ConfigManager.create(Quester.INSTANCE, "quest_config.yml");
        return configManager.target(config);
    }
}