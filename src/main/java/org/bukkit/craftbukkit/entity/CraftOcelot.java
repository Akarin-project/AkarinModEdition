package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.EntityOcelot;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

import org.bukkit.entity.Ocelot.Type;

public class CraftOcelot extends CraftTameableAnimal implements Ocelot {
    public CraftOcelot(CraftServer server, EntityOcelot ocelot) {
        super(server, ocelot);
    }

    @Override
    public EntityOcelot getHandle() {
        return (EntityOcelot) entity;
    }

    public Type getCatType() {
        return Type.getType(getHandle().func_70913_u());
    }

    public void setCatType(Type type) {
        Validate.notNull(type, "Cat type cannot be null");
        getHandle().func_70912_b(type.getId());
    }

    @Override
    public String toString() {
        return "CraftOcelot";
    }

    @Override
    public EntityType getType() {
        return EntityType.OCELOT;
    }
}
