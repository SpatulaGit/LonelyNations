package spatularat.lonelynation.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class NationCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> create() {
        return ClientCommandManager.literal("nation")
                .executes(context -> {
                    context.getSource().sendFeedback(Text.literal(""));
                    return 1;
                });
    }
}