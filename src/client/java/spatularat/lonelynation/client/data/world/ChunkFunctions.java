package spatularat.lonelynation.client.data.world;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;

import java.util.Set;

public class ChunkFunctions {

    public static final Set<EntityType<?>> farmAnimalsCheck = Set.of(
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.PIG,
            EntityType.CHICKEN
    );

    public static ChunkData updateChunkData(long chunkID, WorldData worldData) {

        ClientWorld world = MinecraftClient.getInstance().world;
        assert world != null;

        ChunkPos pos = new ChunkPos(chunkID);
        ChunkData chunkData = worldData.claimedChunks.get(chunkID);

        if (chunkData != null) {
            for (Entity entity : world.getEntities()) {
                if (!entity.getChunkPos().equals(pos)) {
                    continue;
                }

                if (entity instanceof VillagerEntity villager) {
                    VillagerData data = villager.getVillagerData();

                    chunkData.villagerRaces.merge(data.getType().toString(),1,Integer::sum);

                    if (villager.isBaby()) chunkData.babyVillagers++;
                    else if (data.getProfession() == VillagerProfession.NONE) chunkData.unemployedVillagers++;
                    else if (data.getProfession() == VillagerProfession.NITWIT) chunkData.nitwits++;
                    else chunkData.villagerProfessions.merge(data.getProfession().toString(),1,Integer::sum);
                }

                else if (entity instanceof IronGolemEntity) chunkData.ironGolems++;
                else if (farmAnimalsCheck.contains(entity.getType())) chunkData.farmAnimals.merge(entity.getType().toString(),1,Integer::sum);

                else {
                    switch (entity.getType().getSpawnGroup()) {
                        case MONSTER -> chunkData.hostileMobs++;

                        case CREATURE,
                             AMBIENT,
                             WATER_CREATURE,
                             WATER_AMBIENT,
                             AXOLOTLS,
                             UNDERGROUND_WATER_CREATURE -> chunkData.petMobs++;

                        default -> {}
                    }
                }
            }
        }

        return chunkData;
    }

}
