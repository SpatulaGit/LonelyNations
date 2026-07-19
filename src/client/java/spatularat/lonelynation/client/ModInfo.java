package spatularat.lonelynation.client;

import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;

public class ModInfo {

	public static final String MOD_ID = "lonely-nation";

	public static final String LINE = "~~~~~~~~~~~~~~~~~~~~";

	public static final Path CONFIGFILEPATH = FabricLoader
			.getInstance()
			.getConfigDir()
			.resolve("lonelynation")
			.resolve("config.json");

	public static final String VERSION =
			FabricLoader
					.getInstance()
					.getModContainer(MOD_ID)
					.get()
					.getMetadata()
					.getVersion()
					.getFriendlyString();
}
