package spatularat.lonelynation.client.data;

import net.minecraft.client.MinecraftClient;

public class ServerInfo {
    public static String getWorldID() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.isInSingleplayer()) {
            return client.getServer()
                    .getSaveProperties()
                    .getLevelName();
        }

        if (client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        }

        return "unknown";
    }
}
