package nico.worldgamerulesenumfix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.drex.world_gamerules.util.WorldGameRules;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(WorldGameRules.class)
public abstract class WorldGameRulesMixin {
    @WrapMethod(method = "lambda$loadFromCompoundTag$0")
    private static void enum_fix$load(NbtCompound compoundTag, GameRules.Key<?> key, GameRules.Rule<?> value, Operation<Void> original) {
        if (value instanceof EnumRule<?> enumRule) {
            String vl = compoundTag.getString(key.getName());
            ((EnumRuleAccessor) value).enum_fix$deserialize(vl);
        } else {
            original.call(compoundTag, key, value);
        }
    }
}
