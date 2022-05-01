package dev.u9g.quester;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dev.u9g.quester.data.QuestType;
import org.bson.Document;
import org.bukkit.entity.Player;
import redempt.redlib.misc.Task;

import java.util.concurrent.CompletableFuture;

import static org.bukkit.Bukkit.getServer;

public class Database {
    private final MongoClient client;
    private final MongoCollection<Document> playersCollection;
    private static final String PLAYER_UUID = "player_uuid";
    public Database(String uri) {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();
        client = MongoClients.create(settings);
        playersCollection = client.getDatabase("quests").getCollection("players");
    }

    public void incrColumn(Player player, QuestType quest) {
        Task.asyncDelayed(() -> {
            Document playerDoc = playersCollection.find(Filters.eq(PLAYER_UUID, player.getUniqueId().toString())).first();
            if (playerDoc == null) {
                playersCollection.insertOne(new Document().append(PLAYER_UUID, player.getUniqueId().toString()).append(quest.getDBCol(), 1));
                postIncrRunCommandFromConfig(player, quest, 1);
            } else {
                playersCollection.updateOne(new Document().append(PLAYER_UUID, player.getUniqueId().toString()), Updates.inc(quest.getDBCol(), 1));
                Integer prevCount = playerDoc.getInteger(quest.getDBCol());
                if (prevCount == null) {
                    prevCount = 0;
                }
                postIncrRunCommandFromConfig(player, quest, prevCount+1);
            }
        });
    }

    private void postIncrRunCommandFromConfig(Player player, QuestType quest, int count) {
        if (quest.getGoal() == count) {
            String cmd = Quester.INSTANCE.config.runOnFinish.get(quest.getDBCol());
            if (cmd == null) return;
            Task.syncDelayed(() -> {
                getServer().dispatchCommand(getServer().getConsoleSender(), cmd.replace("%player%", player.getName()));
            });
        }
    }

    public CompletableFuture<Integer> getCount(Player player, QuestType quest) {
        CompletableFuture<Integer> count = new CompletableFuture<>();
        Task.asyncDelayed(() -> {
            Document playerDoc = playersCollection.find(Filters.eq(PLAYER_UUID, player.getUniqueId().toString())).first();
            if (playerDoc == null) {
                count.complete(0);
            } else {
                count.complete(playerDoc.getInteger(quest.getDBCol(), 0));
            }
        });
        return count;
    }

    public void close() {
        client.close();
    }
}
