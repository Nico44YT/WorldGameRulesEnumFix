package nico.worldgamerulesenumfix.rule;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;

public class EnumOrdinalArgumentSerializer implements ArgumentSerializer<EnumOrdinalArgumentType<?>, EnumOrdinalArgumentSerializer.Properties> {

    @Override
    public void writePacket(Properties properties, PacketByteBuf buf) {
        Enum[] values = properties.supportedValues;

        if (values.length == 0) {
            throw new IllegalArgumentException("Cannot serialize an enum argument with no supported values");
        }

        buf.writeString(values[0].getDeclaringClass().getName());
        buf.writeVarInt(values.length);

        for (Enum value : values) {
            buf.writeString(value.name());
        }
    }

    @Override
    public Properties fromPacket(PacketByteBuf buf) {
        String className = buf.readString();
        int length = buf.readVarInt();

        try {
            Class<?> clazz = Class.forName(className);

            if (!clazz.isEnum()) {
                throw new IllegalArgumentException(className + " is not an enum");
            }

            Enum[] supportedValues = new Enum[length];

            for (int i = 0; i < length; i++) {
                String name = buf.readString();

                @SuppressWarnings({"rawtypes", "unchecked"})
                Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;

                supportedValues[i] = Enum.valueOf(enumClass, name);
            }

            return new Properties(supportedValues);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown enum class: " + className, e);
        }
    }

    @Override
    public void writeJson(Properties properties, JsonObject json) {
        Enum[] values = properties.supportedValues;

        if (values.length == 0) {
            return;
        }

        json.addProperty(
                "enum",
                values[0].getDeclaringClass().getName()
        );

        JsonArray supportedValues = new JsonArray();

        for (Enum value : values) {
            supportedValues.add(value.name());
        }

        json.add("values", supportedValues);
    }

    @Override
    public Properties getArgumentTypeProperties(EnumOrdinalArgumentType<?> argumentType) {
        return new Properties(argumentType.supportedValues);
    }

    public class Properties implements ArgumentSerializer.ArgumentTypeProperties<EnumOrdinalArgumentType<?>> {

        final Enum[] supportedValues;

        public Properties(Enum[] supportedValues) {
            this.supportedValues = supportedValues;
        }

        @Override
        public EnumOrdinalArgumentType<?> createType(CommandRegistryAccess commandRegistryAccess) {
            return new EnumOrdinalArgumentType<>(supportedValues);
        }

        @Override
        public ArgumentSerializer<EnumOrdinalArgumentType<?>, ?> getSerializer() {
            return EnumOrdinalArgumentSerializer.this;
        }
    }
}
