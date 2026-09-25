package nico.worldgamerulesenumfix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.arguments.ArgumentType;
import net.fabricmc.fabric.impl.gamerule.EnumRuleType;
import nico.worldgamerulesenumfix.rule.EnumOrdinalArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Supplier;

@Mixin(EnumRuleType.class)
public abstract class EnumRuleTypeMixin {
    @ModifyArg(
            method = "<init>(Ljava/util/function/Function;Ljava/util/function/BiConsumer;[Ljava/lang/Enum;Lnet/minecraft/world/GameRules$Acceptor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/GameRules$Type;<init>(Ljava/util/function/Supplier;Ljava/util/function/Function;Ljava/util/function/BiConsumer;Lnet/minecraft/world/GameRules$Acceptor;)V"
            ),
            index = 0
    )
    private static Supplier<ArgumentType<?>> enum_fix$modifyArgumentType(
            Supplier<ArgumentType<?>> original,
            @Local(argsOnly = true) Enum[] supportedValues
    ) {
        return () -> new EnumOrdinalArgumentType(supportedValues);
    }
}
