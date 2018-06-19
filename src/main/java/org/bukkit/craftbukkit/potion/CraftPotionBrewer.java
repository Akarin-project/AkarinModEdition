package org.bukkit.craftbukkit.potion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class CraftPotionBrewer implements PotionBrewer {
    private static final Map<PotionType, Collection<PotionEffect>> cache = Maps.newHashMap();

    public Collection<PotionEffect> getEffects(PotionType damage, boolean upgraded, boolean extended) {
        if (cache.containsKey(damage))
            return cache.get(damage);

        List<PotionEffect> mcEffects = PotionType.func_185168_a(CraftPotionUtil.fromBukkit(new PotionData(damage, extended, upgraded))).func_185170_a();

        ImmutableList.Builder<PotionEffect> builder = new ImmutableList.Builder<PotionEffect>();
        for (PotionEffect effect : mcEffects) {
            builder.add(CraftPotionUtil.toBukkit(effect));
        }

        cache.put(damage, builder.build());

        return cache.get(damage);
    }

    @Override
    public Collection<PotionEffect> getEffectsFromDamage(int damage) {
        return new ArrayList<PotionEffect>();
    }

    @Override
    public PotionEffect createEffect(PotionEffectType potion, int duration, int amplifier) {
        return new PotionEffect(potion, potion.isInstant() ? 1 : (int) (duration * potion.getDurationModifier()), amplifier);
    }
}
