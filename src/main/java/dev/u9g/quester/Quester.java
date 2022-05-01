package dev.u9g.quester;

import dev.u9g.quester.data.QuestConfig;
import dev.u9g.quester.handlers.EventHandlers;
import dev.u9g.quester.handlers.QuestCommandHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandParser;
import redempt.redlib.misc.EventListener;

public final class Quester extends JavaPlugin {
    public static Quester INSTANCE;
    public QuestConfig config;
    public BukkitAudiences adventure;
    public Database database;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.adventure = BukkitAudiences.create(this);
        config = QuestConfig.init();
        if (config.mongoDbUri.equals("FILL THIS IN")) {
            Bukkit.getLogger().fine("==================================================");
            Bukkit.getLogger().fine("==================================================");
            Bukkit.getLogger().fine("Put a mongo db uri into the Quester config file in your plugins folder.");
            Bukkit.getLogger().fine("==================================================");
            Bukkit.getLogger().fine("==================================================");
            throw new RuntimeException("Put a mongo db uri into the Quester config file in your plugins folder.");
        }
        new CommandParser(this.getResource("commands.rdcml")).parse().register("quester", new QuestCommandHandler());
        database = new Database(config.mongoDbUri);
        Bukkit.getPluginManager().registerEvents(new EventHandlers(), this);
    }

    @Override
    public void onDisable() {
        this.adventure.close();
        this.adventure = null;
        database.close();
    }
}
