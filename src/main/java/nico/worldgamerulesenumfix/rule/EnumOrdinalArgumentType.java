package nico.worldgamerulesenumfix.rule;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class EnumOrdinalArgumentType<E extends Enum<E>> implements ArgumentType<E> {

    public final E[] supportedValues;

    public EnumOrdinalArgumentType(E[] supportedValues) {
        this.supportedValues = supportedValues;
    }

    @Override
    public E parse(StringReader reader) throws CommandSyntaxException {
        int ordinal = reader.readInt();

        if (ordinal < 0 || ordinal >= supportedValues.length) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS
                    .readerInvalidInt()
                    .createWithContext(reader, ordinal);
        }

        return supportedValues[ordinal];
    }
}