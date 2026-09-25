package nico.worldgamerulesenumfix.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(CommandContext.class)
public interface CommandContextAccessor {
    @Accessor("arguments")
    Map<String, ParsedArgument<?, ?>> enum_fix$getArguments();
}
