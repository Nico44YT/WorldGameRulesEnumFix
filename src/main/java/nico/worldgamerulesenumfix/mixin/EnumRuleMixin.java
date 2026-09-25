package nico.worldgamerulesenumfix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(EnumRule.class)
public abstract class EnumRuleMixin<E extends Enum<E>> extends GameRules.Rule<EnumRule<E>> {
    @Shadow
    private E value;

    @Shadow
    @Final
    private List<E> supportedValues;

    public EnumRuleMixin(GameRules.Type<EnumRule<E>> type) {
        super(type);
    }

    @WrapMethod(method = "setFromArgument")
    public void enum_fix$setFromArgument(CommandContext<ServerCommandSource> context, String name, Operation<Void> original) {
        StringRange stringRange = ((CommandContextAccessor) context).enum_fix$getArguments().get(name).getRange();
        String s = context.getInput().substring(stringRange.getStart(), stringRange.getEnd());

        int index = Integer.parseInt(s);

        this.value = this.supportedValues.get(index);
    }
}
