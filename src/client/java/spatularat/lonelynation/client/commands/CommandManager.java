package spatularat.lonelynation.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.text.Text;
import spatularat.lonelynation.client.ModInfo;

public class CommandManager {
	//add new commands here
	private static LiteralArgumentBuilder<FabricClientCommandSource> createRoot(String name){
		return ClientCommandManager.literal(name)
				.executes(context -> {
					context.getSource().sendFeedback(
							Text.literal("Lonely Nation\nVersion "+
									ModInfo.VERSION+
									"\n\nCommands: \n  /mnations help"+
									ModInfo.LINE)
					);
					return 1;
				})
				.then(HelpCommand.create())
				.then(NationClaimCommand.create());
	}

	//DO NOT TOUCH
	public static void register (CommandDispatcher<FabricClientCommandSource> dispatcher){
		dispatcher.register(createRoot("lnation"));
		dispatcher.register(createRoot("lonelynation"));
	}


}