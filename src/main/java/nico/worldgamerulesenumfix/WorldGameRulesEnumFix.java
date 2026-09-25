package nico.worldgamerulesenumfix;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.util.Identifier;
import nico.worldgamerulesenumfix.rule.EnumOrdinalArgumentSerializer;
import nico.worldgamerulesenumfix.rule.EnumOrdinalArgumentType;

public class WorldGameRulesEnumFix implements ModInitializer {
    public static final String MOD_ID = "worldgamerulesenumfix";

    @Override
    public void onInitialize() {
        ArgumentTypeRegistry.registerArgumentType(
                Identifier.of(MOD_ID, "enum_ordinal_argument"),
                (Class<? extends EnumOrdinalArgumentType<?>>) (Class<?>) EnumOrdinalArgumentType.class,
                new EnumOrdinalArgumentSerializer()
        );

    }
}
