package spatularat.lonelynation.client.data.world;

import java.util.HashMap;
import java.util.Map;

public class ChunkData {
    public Map<String,Integer> villagerProfessions = new HashMap<>();
    public Map<String, Integer> villagerRaces = new HashMap<>();
    public Map<String,Integer> farmAnimals = new HashMap<>();
    public int babyVillagers = 0;
    public int unemployedVillagers = 0;
    public int nitwits = 0;
    public String _nitwits_description = "Adult villagers unable to obtain jobs and cannot perform labour";
    public int ironGolems = 0;
    public int hostileMobs = 0;
    public int petMobs = 0;
}
