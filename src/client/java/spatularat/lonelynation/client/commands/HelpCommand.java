package spatularat.lonelynation.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import spatularat.lonelynation.client.ModInfo;


public class HelpCommand {
	public static LiteralArgumentBuilder<FabricClientCommandSource> create(){
		return ClientCommandManager.literal("help")
				.executes(context -> {
					context.getSource().sendFeedback(
							Text.literal("Lonely Nation Help\n\n" +
									"/lnation help - shows this menu\n"+
									ModInfo.LINE)
					);
					return 1;
				});
	}
}