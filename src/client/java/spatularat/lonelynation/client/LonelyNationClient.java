package spatularat.lonelynation.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;
import spatularat.lonelynation.client.commands.CommandManager;
import spatularat.lonelynation.client.data.Config;
import spatularat.lonelynation.client.data.JsonFileManager;
import spatularat.lonelynation.client.data.world.ChunkData;
import spatularat.lonelynation.client.data.world.ChunkFunctions;
import spatularat.lonelynation.client.data.world.WorldData;

public class LonelyNationClient implements ClientModInitializer {

	int ticks = 0;

	@Override
	public void onInitializeClient(){

		JsonFileManager.createFile("/config/lonelynation.json", new Config());

		ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
			JsonFileManager.createFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json", new ChunkData());
		}));

		ClientCommandRegistrationCallback.EVENT.register(
				((dispatcher, registryAccess) -> {
					CommandManager.register(dispatcher);
				})
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ticks++;

			if (ticks >= 20) {
				ticks = 0;

				if (MinecraftClient.getInstance().options == null) return;
				int renderDistance = MinecraftClient.getInstance().options.getViewDistance().getValue();

				WorldData worldData = JsonFileManager.loadFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json", WorldData.class);

				assert worldData != null;
				if (client.player != null) {
					ChunkPos playerChunk = client.player.getChunkPos();

					for (int x = -renderDistance; x <= renderDistance; x++) {
						for (int z = -renderDistance; z <= renderDistance; z++) {
							int chunkX = playerChunk.x + x;
							int chunkZ = playerChunk.z + z;

							long newChunkID = new ChunkPos(chunkX,chunkZ).toLong();
                            for (long claimedChunkID : worldData.claimedChunks.keySet()) {
								if (newChunkID==claimedChunkID) {
									ChunkData newChunkData = ChunkFunctions.updateChunkData(claimedChunkID, worldData);
									worldData.claimedChunks.put(claimedChunkID,newChunkData);
								}
							}
						}
					}
				}

				JsonFileManager.saveFile("/lonelynation/" + ServerInfo.getWorldID() +"/ChunkData.json",worldData);

			}
		});
	}
}