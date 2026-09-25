package nico.worldgamerulesenumfix.mixin;

import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnumRule.class)
public interface EnumRuleAccessor {
    @Invoker("deserialize")
    void enum_fix$deserialize(String value);
}
