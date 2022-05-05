package net.helix.bennis.util.tags;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class StringTagType implements PersistentDataType<byte[], String> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<String> getComplexType() {
        return String.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull String complex, @NotNull PersistentDataAdapterContext context) {
        return complex.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public @NotNull String fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return new String(primitive, StandardCharsets.UTF_8);
    }
}
