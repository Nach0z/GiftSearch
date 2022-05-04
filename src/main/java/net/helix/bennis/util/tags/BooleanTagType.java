package net.helix.bennis.util.tags;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BooleanTagType implements PersistentDataType<Byte, Boolean> {

    @Override
    public @NotNull Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public Byte toPrimitive(Boolean complex, PersistentDataAdapterContext context) {
        return (byte) (complex ? 1 : 0);
    }

    @Override
    public Boolean fromPrimitive(Byte primitive, PersistentDataAdapterContext context) {
        return primitive != 0;
    }
}
