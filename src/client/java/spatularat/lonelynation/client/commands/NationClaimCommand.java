package spatularat.lonelynation.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkPos;
import spatularat.lonelynation.client.data.world.ChunkData;
import spatularat.lonelynation.client.data.JsonFileManager;
import spatularat.lonelynation.client.ServerInfo;
import spatularat.lonelynation.client.data.world.WorldData;

public class NationClaimCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> create() {
        return ClientCommandManager.literal("claim")
                .executes(context -> {
                    MinecraftClient client = MinecraftClient.getInstance();

                    WorldData worldData = JsonFileManager.loadFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json", WorldData.class);
                    if (worldData == null) {
                        JsonFileManager.createFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json",new WorldData());
                    }

                    if (client.player != null){
                        long chunkID = client.player.getChunkPos().toLong();
                        worldData.claimedChunks.put(chunkID,new ChunkData());
                        JsonFileManager.saveFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json",worldData);

                        ChunkPos chunk = new ChunkPos(chunkID);
                        context.getSource().sendFeedback(Text.literal("Claimed chunk "+
                                chunk.x+", "+chunk.z+"!"));
                    }

                    return 1;
                })
                .then(argument("x",IntegerArgumentType.integer())
                        .then(argument("z",IntegerArgumentType.integer()))
                        .executes(context -> {

                            WorldData worldData = JsonFileManager.loadFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json", WorldData.class);
                            if (worldData == null) {
                                JsonFileManager.createFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json",new WorldData());
                            }

                            int x = IntegerArgumentType.getInteger(context, "x");
                            int z = IntegerArgumentType.getInteger(context,"z");

                            long chunkID = new ChunkPos(x >> 4,z >> 4).toLong();
                            JsonFileManager.saveFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json",worldData);

                            ChunkPos chunk = new ChunkPos(chunkID);
                            context.getSource().sendFeedback(Text.literal("Claimed chunk "+
                                    chunk.x+", "+chunk.z+ "!"));

                            return 1;
                        }));
    }
}
