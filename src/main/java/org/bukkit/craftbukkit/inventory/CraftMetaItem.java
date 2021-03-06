package org.bukkit.craftbukkit.inventory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.common.collect.ImmutableSortedMap;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.Overridden;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.nbt.CompressedStreamTools;
import org.apache.commons.codec.binary.Base64;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import org.bukkit.inventory.meta.ItemMeta.Spigot;

// Spigot start
import static org.spigotmc.ValidateUtils.*;
// Spigot end

import static org.spigotmc.ValidateUtils.*;

/**
 * Children must include the following:
 *
 * <li> Constructor(CraftMetaItem meta)
 * <li> Constructor(NBTTagCompound tag)
 * <li> Constructor(Map<String, Object> map)
 * <br><br>
 * <li> void applyToItem(NBTTagCompound tag)
 * <li> boolean applicableTo(Material type)
 * <br><br>
 * <li> boolean equalsCommon(CraftMetaItem meta)
 * <li> boolean notUncommon(CraftMetaItem meta)
 * <br><br>
 * <li> boolean isEmpty()
 * <li> boolean is{Type}Empty()
 * <br><br>
 * <li> int applyHash()
 * <li> public Class clone()
 * <br><br>
 * <li> Builder<String, Object> serialize(Builder<String, Object> builder)
 * <li> SerializableMeta.Deserializers deserializer()
 */
@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaItem implements ItemMeta, Repairable {

    static class ItemMetaKey {

        @Retention(RetentionPolicy.SOURCE)
        @Target(ElementType.FIELD)
        @interface Specific {
            enum To {
                BUKKIT,
                NBT,
                ;
            }
            To value();
        }

        final String BUKKIT;
        final String NBT;

        ItemMetaKey(final String both) {
            this(both, both);
        }

        ItemMetaKey(final String nbt, final String bukkit) {
            this.NBT = nbt;
            this.BUKKIT = bukkit;
        }
    }

    @SerializableAs("ItemMeta")
    public static class SerializableMeta implements ConfigurationSerializable {
        static final String TYPE_FIELD = "meta-type";

        static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap;
        static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;

        static {
            classMap = ImmutableMap.<Class<? extends CraftMetaItem>, String>builder()
                    .put(CraftMetaBanner.class, "BANNER")
                    .put(CraftMetaBlockState.class, "TILE_ENTITY")
                    .put(CraftMetaBook.class, "BOOK")
                    .put(CraftMetaBookSigned.class, "BOOK_SIGNED")
                    .put(CraftMetaSkull.class, "SKULL")
                    .put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR")
                    .put(CraftMetaMap.class, "MAP")
                    .put(CraftMetaPotion.class, "POTION")
                    .put(CraftMetaSpawnEgg.class, "SPAWN_EGG")
                    .put(CraftMetaEnchantedBook.class, "ENCHANTED")
                    .put(CraftMetaFirework.class, "FIREWORK")
                    .put(CraftMetaCharge.class, "FIREWORK_EFFECT")
                    .put(CraftMetaKnowledgeBook.class, "KNOWLEDGE_BOOK")
                    .put(CraftMetaArmorStand.class, "ARMOR_STAND")
                    .put(CraftMetaItem.class, "UNSPECIFIC")
                    .build();

            final ImmutableMap.Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();
            for (Map.Entry<Class<? extends CraftMetaItem>, String> mapping : classMap.entrySet()) {
                try {
                    classConstructorBuilder.put(mapping.getValue(), mapping.getKey().getDeclaredConstructor(Map.class));
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            }
            constructorMap = classConstructorBuilder.build();
        }

        private SerializableMeta() {
        }

        public static ItemMeta deserialize(Map<String, Object> map) throws Throwable {
            Validate.notNull(map, "Cannot deserialize null map");

            String type = getString(map, TYPE_FIELD, false);
            Constructor<? extends CraftMetaItem> constructor = constructorMap.get(type);

            if (constructor == null) {
                throw new IllegalArgumentException(type + " is not a valid " + TYPE_FIELD);
            }

            try {
                return constructor.newInstance(map);
            } catch (final InstantiationException e) {
                throw new AssertionError(e);
            } catch (final IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (final InvocationTargetException e) {
                throw e.getCause();
            }
        }

        @Override
        public Map<String, Object> serialize() {
            throw new AssertionError();
        }

        static String getString(Map<?, ?> map, Object field, boolean nullable) {
            return getObject(String.class, map, field, nullable);
        }

        static boolean getBoolean(Map<?, ?> map, Object field) {
            Boolean value = getObject(Boolean.class, map, field, true);
            return value != null && value;
        }

        static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable) {
            final Object object = map.get(field);

            if (clazz.isInstance(object)) {
                return clazz.cast(object);
            }
            if (object == null) {
                if (!nullable) {
                    throw new NoSuchElementException(map + " does not contain " + field);
                }
                return null;
            }
            throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
        }
    }

    static final ItemMetaKey NAME = new ItemMetaKey("Name", "display-name");
    static final ItemMetaKey LOCNAME = new ItemMetaKey("LocName", "loc-name");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey DISPLAY = new ItemMetaKey("display");
    static final ItemMetaKey LORE = new ItemMetaKey("Lore", "lore");
    static final ItemMetaKey ENCHANTMENTS = new ItemMetaKey("ench", "enchants");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ENCHANTMENTS_ID = new ItemMetaKey("id");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ENCHANTMENTS_LVL = new ItemMetaKey("lvl");
    static final ItemMetaKey REPAIR = new ItemMetaKey("RepairCost", "repair-cost");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES = new ItemMetaKey("AttributeModifiers");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_IDENTIFIER = new ItemMetaKey("AttributeName");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_NAME = new ItemMetaKey("Name");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_VALUE = new ItemMetaKey("Amount");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_TYPE = new ItemMetaKey("Operation");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_UUID_HIGH = new ItemMetaKey("UUIDMost");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey ATTRIBUTES_UUID_LOW = new ItemMetaKey("UUIDLeast");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey HIDEFLAGS = new ItemMetaKey("HideFlags", "ItemFlags");
    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey UNBREAKABLE = new ItemMetaKey("Unbreakable");

    private String displayName;
    private String locName;
    private List<String> lore;
    private EnchantmentMap enchantments; // Paper
    private int repairCost;
    private int hideFlag;
    private boolean unbreakable;

    private static final Set<String> HANDLED_TAGS = Sets.newHashSet();

    private NBTTagCompound internalTag;
    private final Map<String, NBTBase> unhandledTags = new TreeMap<>(); // Paper

    CraftMetaItem(CraftMetaItem meta) {
        if (meta == null) {
            return;
        }

        this.displayName = meta.displayName;
        this.locName = meta.locName;

        if (meta.hasLore()) {
            this.lore = new ArrayList<String>(meta.lore);
        }

        if (meta.enchantments != null) { // Spigot
            this.enchantments = new EnchantmentMap(meta.enchantments); // Paper
        }

        this.repairCost = meta.repairCost;
        this.hideFlag = meta.hideFlag;
        this.unbreakable = meta.unbreakable;
        this.unhandledTags.putAll(meta.unhandledTags);

        this.internalTag = meta.internalTag;
        if (this.internalTag != null) {
            deserializeInternal(internalTag);
        }
    }

    CraftMetaItem(NBTTagCompound tag) {
        if (tag.func_74764_b(DISPLAY.NBT)) {
            NBTTagCompound display = tag.func_74775_l(DISPLAY.NBT);

            if (display.func_74764_b(NAME.NBT)) {
                displayName = limit( display.func_74779_i(NAME.NBT), 1024 ); // Spigot
            }

            if (display.func_74764_b(LOCNAME.NBT)) {
                locName = display.func_74779_i(LOCNAME.NBT);
            }

            if (display.func_74764_b(LORE.NBT)) {
                NBTTagList list = display.func_150295_c(LORE.NBT, CraftMagicNumbers.NBT.TAG_STRING);
                lore = new ArrayList<String>(list.func_74745_c());

                for (int index = 0; index < list.func_74745_c(); index++) {
                    String line = limit( list.func_150307_f(index), 1024 ); // Spigot
                    lore.add(line);
                }
            }
        }

        this.enchantments = buildEnchantments(tag, ENCHANTMENTS);

        if (tag.func_74764_b(REPAIR.NBT)) {
            repairCost = tag.func_74762_e(REPAIR.NBT);
        }

        if (tag.func_74764_b(HIDEFLAGS.NBT)) {
            hideFlag = tag.func_74762_e(HIDEFLAGS.NBT);
        }
        if (tag.func_74764_b(UNBREAKABLE.NBT)) {
            unbreakable = tag.func_74767_n(UNBREAKABLE.NBT);
        }

        if (tag.func_74781_a(ATTRIBUTES.NBT) instanceof NBTTagList) {
            NBTTagList save = null;
            NBTTagList nbttaglist = tag.func_150295_c(ATTRIBUTES.NBT, CraftMagicNumbers.NBT.TAG_COMPOUND);

            // Spigot start
            gnu.trove.map.hash.TObjectDoubleHashMap<String> attributeTracker = new gnu.trove.map.hash.TObjectDoubleHashMap<String>();
            gnu.trove.map.hash.TObjectDoubleHashMap<String> attributeTrackerX = new gnu.trove.map.hash.TObjectDoubleHashMap<String>();
            Map<String, IAttribute> attributesByName = new HashMap<String, IAttribute>();
            attributeTracker.put( "generic.maxHealth", 20.0 );
            attributesByName.put( "generic.maxHealth", SharedMonsterAttributes.field_111267_a );
            attributeTracker.put( "generic.followRange", 32.0 );
            attributesByName.put( "generic.followRange", SharedMonsterAttributes.field_111265_b );
            attributeTracker.put( "generic.knockbackResistance", 0.0 );
            attributesByName.put( "generic.knockbackResistance", SharedMonsterAttributes.field_111266_c );
            attributeTracker.put( "generic.movementSpeed", 0.7 );
            attributesByName.put( "generic.movementSpeed", SharedMonsterAttributes.field_111263_d );
            attributeTracker.put( "generic.attackDamage", 1.0 );
            attributesByName.put( "generic.attackDamage", SharedMonsterAttributes.field_111264_e );
            NBTTagList oldList = nbttaglist;
            nbttaglist = new NBTTagList();

            List<NBTTagCompound> op0 = new ArrayList<NBTTagCompound>();
            List<NBTTagCompound> op1 = new ArrayList<NBTTagCompound>();
            List<NBTTagCompound> op2 = new ArrayList<NBTTagCompound>();

            for ( int i = 0; i < oldList.func_74745_c(); ++i )
            {
                NBTTagCompound nbttagcompound = oldList.func_150305_b( i );
                if ( nbttagcompound == null ) continue;

                if ( !nbttagcompound.func_150297_b(ATTRIBUTES_UUID_HIGH.NBT, 99) )
                {
                    continue;
                }
                if ( !nbttagcompound.func_150297_b(ATTRIBUTES_UUID_LOW.NBT, 99)  )
                {
                    continue;
                }
                if ( !( nbttagcompound.func_74781_a( ATTRIBUTES_IDENTIFIER.NBT ) instanceof NBTTagString ) || !CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains( nbttagcompound.func_74779_i( ATTRIBUTES_IDENTIFIER.NBT ) ) )
                {
                    continue;
                }
                if ( !( nbttagcompound.func_74781_a( ATTRIBUTES_NAME.NBT ) instanceof NBTTagString ) || nbttagcompound.func_74779_i( ATTRIBUTES_NAME.NBT ).isEmpty() )
                {
                    continue;
                }
                if ( !nbttagcompound.func_150297_b(ATTRIBUTES_VALUE.NBT, 99) )
                {
                    continue;
                }
                if ( !nbttagcompound.func_150297_b(ATTRIBUTES_TYPE.NBT, 99) || nbttagcompound.func_74762_e( ATTRIBUTES_TYPE.NBT ) < 0 || nbttagcompound.func_74762_e( ATTRIBUTES_TYPE.NBT ) > 2 )
                {
                    continue;
                }

                switch ( nbttagcompound.func_74762_e( ATTRIBUTES_TYPE.NBT ) )
                {
                    case 0:
                        op0.add( nbttagcompound );
                        break;
                    case 1:
                        op1.add( nbttagcompound );
                        break;
                    case 2:
                        op2.add( nbttagcompound );
                        break;
                }
            }
            for ( NBTTagCompound nbtTagCompound : op0 )
            {
                String name = nbtTagCompound.func_74779_i( ATTRIBUTES_IDENTIFIER.NBT );
                if ( attributeTracker.containsKey( name ) )
                {
                    double val = attributeTracker.get( name );
                    val += nbtTagCompound.func_74769_h( ATTRIBUTES_VALUE.NBT );
                    if ( val != attributesByName.get( name ).func_111109_a( val ) )
                    {
                        continue;
                    }
                    attributeTracker.put( name, val );
                }
                nbttaglist.func_74742_a( nbtTagCompound );
            }
            for ( String name : attributeTracker.keySet() )
            {
                attributeTrackerX.put( name, attributeTracker.get( name ) );
            }
            for ( NBTTagCompound nbtTagCompound : op1 )
            {
                String name = nbtTagCompound.func_74779_i( ATTRIBUTES_IDENTIFIER.NBT );
                if ( attributeTracker.containsKey( name ) )
                {
                    double val = attributeTracker.get( name );
                    double valX = attributeTrackerX.get( name );
                    val += valX * nbtTagCompound.func_74769_h( ATTRIBUTES_VALUE.NBT );
                    if ( val != attributesByName.get( name ).func_111109_a( val ) )
                    {
                        continue;
                    }
                    attributeTracker.put( name, val );
                }
                nbttaglist.func_74742_a( nbtTagCompound );
            }
            for ( NBTTagCompound nbtTagCompound : op2 )
            {
                String name = nbtTagCompound.func_74779_i( ATTRIBUTES_IDENTIFIER.NBT );
                if ( attributeTracker.containsKey( name ) )
                {
                    double val = attributeTracker.get( name );
                    val += val * nbtTagCompound.func_74769_h( ATTRIBUTES_VALUE.NBT );
                    if ( val != attributesByName.get( name ).func_111109_a( val ) )
                    {
                        continue;
                    }
                    attributeTracker.put( name, val );
                }
                nbttaglist.func_74742_a( nbtTagCompound );
            }

            // Spigot end

            for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                if (!(nbttaglist.func_150305_b(i) instanceof NBTTagCompound)) {
                    continue;
                }
                NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);

                if (!nbttagcompound.func_150297_b(ATTRIBUTES_UUID_HIGH.NBT, CraftMagicNumbers.NBT.TAG_ANY_NUMBER)) {
                    continue;
                }
                if (!nbttagcompound.func_150297_b(ATTRIBUTES_UUID_LOW.NBT, CraftMagicNumbers.NBT.TAG_ANY_NUMBER)) {
                    continue;
                }
                if (!(nbttagcompound.func_74781_a(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString) || !CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.func_74779_i(ATTRIBUTES_IDENTIFIER.NBT))) {
                    continue;
                }
                if (!(nbttagcompound.func_74781_a(ATTRIBUTES_NAME.NBT) instanceof NBTTagString) || nbttagcompound.func_74779_i(ATTRIBUTES_NAME.NBT).isEmpty()) {
                    continue;
                }
                if (!nbttagcompound.func_150297_b(ATTRIBUTES_VALUE.NBT, CraftMagicNumbers.NBT.TAG_ANY_NUMBER)) {
                    continue;
                }
                if (!nbttagcompound.func_150297_b(ATTRIBUTES_TYPE.NBT, CraftMagicNumbers.NBT.TAG_ANY_NUMBER) || nbttagcompound.func_74762_e(ATTRIBUTES_TYPE.NBT) < 0 || nbttagcompound.func_74762_e(ATTRIBUTES_TYPE.NBT) > 2) {
                    continue;
                }

                if (save == null) {
                    save = new NBTTagList();
                }

                NBTTagCompound entry = new NBTTagCompound();
                entry.func_74782_a(ATTRIBUTES_UUID_HIGH.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_UUID_HIGH.NBT));
                entry.func_74782_a(ATTRIBUTES_UUID_LOW.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_UUID_LOW.NBT));
                entry.func_74782_a(ATTRIBUTES_IDENTIFIER.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_IDENTIFIER.NBT));
                entry.func_74782_a(ATTRIBUTES_NAME.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_NAME.NBT));
                entry.func_74782_a(ATTRIBUTES_VALUE.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_VALUE.NBT));
                entry.func_74782_a(ATTRIBUTES_TYPE.NBT, nbttagcompound.func_74781_a(ATTRIBUTES_TYPE.NBT));
                save.func_74742_a(entry);
            }

            unhandledTags.put(ATTRIBUTES.NBT, save);
        }

        Set<String> keys = tag.func_150296_c();
        for (String key : keys) {
            if (!getHandledTags().contains(key)) {
                unhandledTags.put(key, tag.func_74781_a(key));
            }
        }
    }

    static EnchantmentMap buildEnchantments(NBTTagCompound tag, ItemMetaKey key) { // Paper
        if (!tag.func_74764_b(key.NBT)) {
            return null;
        }

        NBTTagList ench = tag.func_150295_c(key.NBT, CraftMagicNumbers.NBT.TAG_COMPOUND);
        EnchantmentMap enchantments = new EnchantmentMap(); // Paper

        for (int i = 0; i < ench.func_74745_c(); i++) {
            int id = 0xffff & ench.func_150305_b(i).func_74765_d(ENCHANTMENTS_ID.NBT);
            int level = 0xffff & ench.func_150305_b(i).func_74765_d(ENCHANTMENTS_LVL.NBT);

            Enchantment enchant = Enchantment.getById(id);
            if (enchant != null) {
                enchantments.put(enchant, level);
            }
        }

        return enchantments;
    }

    CraftMetaItem(Map<String, Object> map) {
        setDisplayName(SerializableMeta.getString(map, NAME.BUKKIT, true));
        setLocalizedName(SerializableMeta.getString(map, LOCNAME.BUKKIT, true));

        Iterable<?> lore = SerializableMeta.getObject(Iterable.class, map, LORE.BUKKIT, true);
        if (lore != null) {
            safelyAdd(lore, this.lore = new ArrayList<String>(), Integer.MAX_VALUE);
        }

        enchantments = buildEnchantments(map, ENCHANTMENTS);

        Integer repairCost = SerializableMeta.getObject(Integer.class, map, REPAIR.BUKKIT, true);
        if (repairCost != null) {
            setRepairCost(repairCost);
        }

        Iterable<?> hideFlags = SerializableMeta.getObject(Iterable.class, map, HIDEFLAGS.BUKKIT, true);
        if (hideFlags != null) {
            for (Object hideFlagObject : hideFlags) {
                String hideFlagString = (String) hideFlagObject;
                try {
                    ItemFlag hideFlatEnum = ItemFlag.valueOf(hideFlagString);
                    addItemFlags(hideFlatEnum);
                } catch (IllegalArgumentException ex) {
                    // Ignore when we got a old String which does not map to a Enum value anymore
                }
            }
        }

        Boolean unbreakable = SerializableMeta.getObject(Boolean.class, map, UNBREAKABLE.BUKKIT, true);
        if (unbreakable != null) {
            setUnbreakable(unbreakable);
        }

        String internal = SerializableMeta.getString(map, "internal", true);
        if (internal != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(Base64.decodeBase64(internal));
            try {
                internalTag = CompressedStreamTools.func_74796_a(buf);
                deserializeInternal(internalTag);
                Set<String> keys = internalTag.func_150296_c();
                for (String key : keys) {
                    if (!getHandledTags().contains(key)) {
                        unhandledTags.put(key, internalTag.func_74781_a(key));
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void deserializeInternal(NBTTagCompound tag) {
    }

    static EnchantmentMap buildEnchantments(Map<String, Object> map, ItemMetaKey key) { // Paper
        Map<?, ?> ench = SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
        if (ench == null) {
            return null;
        }

        EnchantmentMap enchantments = new EnchantmentMap(); // Paper
        for (Map.Entry<?, ?> entry : ench.entrySet()) {
            // Doctor older enchants
            String enchantKey = entry.getKey().toString();
            if (enchantKey.equals("SWEEPING")) {
                enchantKey = "SWEEPING_EDGE";
            }

            Enchantment enchantment = Enchantment.getByName(enchantKey);
            if ((enchantment != null) && (entry.getValue() instanceof Integer)) {
                enchantments.put(enchantment, (Integer) entry.getValue());
            }
        }

        return enchantments;
    }

    @Overridden
    void applyToItem(NBTTagCompound itemTag) {
        if (hasDisplayName()) {
            setDisplayTag(itemTag, NAME.NBT, new NBTTagString(displayName));
        }
        if (hasLocalizedName()){
            setDisplayTag(itemTag, LOCNAME.NBT, new NBTTagString(locName));
        }

        if (hasLore()) {
            setDisplayTag(itemTag, LORE.NBT, createStringList(lore));
        }

        if (hideFlag != 0) {
            itemTag.func_74768_a(HIDEFLAGS.NBT, hideFlag);
        }

        applyEnchantments(enchantments, itemTag, ENCHANTMENTS);

        if (hasRepairCost()) {
            itemTag.func_74768_a(REPAIR.NBT, repairCost);
        }

        if (isUnbreakable()) {
            itemTag.func_74757_a(UNBREAKABLE.NBT, unbreakable);
        }

        for (Map.Entry<String, NBTBase> e : unhandledTags.entrySet()) {
            itemTag.func_74782_a(e.getKey(), e.getValue());
        }
    }

    static NBTTagList createStringList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        NBTTagList tagList = new NBTTagList();
        for (String value : list) {
            tagList.func_74742_a(new NBTTagString(value));
        }

        return tagList;
    }

    static void applyEnchantments(Map<Enchantment, Integer> enchantments, NBTTagCompound tag, ItemMetaKey key) {
        if (enchantments == null /*|| enchantments.size() == 0*/) { // Spigot - remove size check
            return;
        }

        NBTTagList list = new NBTTagList();

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            NBTTagCompound subtag = new NBTTagCompound();

            subtag.func_74777_a(ENCHANTMENTS_ID.NBT, (short) entry.getKey().getId());
            subtag.func_74777_a(ENCHANTMENTS_LVL.NBT, entry.getValue().shortValue());

            list.func_74742_a(subtag);
        }

        tag.func_74782_a(key.NBT, list);
    }

    void setDisplayTag(NBTTagCompound tag, String key, NBTBase value) {
        final NBTTagCompound display = tag.func_74775_l(DISPLAY.NBT);

        if (!tag.func_74764_b(DISPLAY.NBT)) {
            tag.func_74782_a(DISPLAY.NBT, display);
        }

        display.func_74782_a(key, value);
    }

    @Overridden
    boolean applicableTo(Material type) {
        return type != Material.AIR;
    }

    @Overridden
    boolean isEmpty() {
        return !(hasDisplayName() || hasLocalizedName() || hasEnchants() || hasLore() || hasRepairCost() || !unhandledTags.isEmpty() || hideFlag != 0 || isUnbreakable());
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public final void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public boolean hasDisplayName() {
        return !Strings.isNullOrEmpty(displayName);
    }

    @Override
    public String getLocalizedName() {
        return locName;
    }

    @Override
    public void setLocalizedName(String name) {
        this.locName = name;
    }

    @Override
    public boolean hasLocalizedName() {
        return !Strings.isNullOrEmpty(locName);
    }

    @Override
    public boolean hasLore() {
        return this.lore != null && !this.lore.isEmpty();
    }

    @Override
    public boolean hasRepairCost() {
        return repairCost > 0;
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        Validate.notNull(ench, "Enchantment cannot be null");
        return hasEnchants() && enchantments.containsKey(ench);
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        Validate.notNull(ench, "Enchantment cannot be null");
        Integer level = hasEnchants() ? enchantments.get(ench) : null;
        if (level == null) {
            return 0;
        }
        return level;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return hasEnchants() ? ImmutableSortedMap.copyOfSorted(enchantments) : ImmutableMap.<Enchantment, Integer>of(); // Paper
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
        Validate.notNull(ench, "Enchantment cannot be null");
        if (enchantments == null) {
            enchantments = new EnchantmentMap(); // Paper
        }

        if (ignoreRestrictions || level >= ench.getStartLevel() && level <= ench.getMaxLevel()) {
            Integer old = enchantments.put(ench, level);
            return old == null || old != level;
        }
        return false;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        Validate.notNull(ench, "Enchantment cannot be null");
        // Spigot start
        boolean b = hasEnchants() && enchantments.remove( ench ) != null;
        if ( enchantments != null && enchantments.isEmpty() )
        {
            this.enchantments = null;
        }
        return b;
        // Spigot end
    }

    @Override
    public boolean hasEnchants() {
        return !(enchantments == null || enchantments.isEmpty());
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        return checkConflictingEnchants(enchantments, ench);
    }

    @Override
    public void addItemFlags(ItemFlag... hideFlags) {
        for (ItemFlag f : hideFlags) {
            this.hideFlag |= getBitModifier(f);
        }
    }

    @Override
    public void removeItemFlags(ItemFlag... hideFlags) {
        for (ItemFlag f : hideFlags) {
            this.hideFlag &= ~getBitModifier(f);
        }
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        Set<ItemFlag> currentFlags = EnumSet.noneOf(ItemFlag.class);

        for (ItemFlag f : ItemFlag.values()) {
            if (hasItemFlag(f)) {
                currentFlags.add(f);
            }
        }

        return currentFlags;
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        int bitModifier = getBitModifier(flag);
        return (this.hideFlag & bitModifier) == bitModifier;
    }

    private byte getBitModifier(ItemFlag hideFlag) {
        return (byte) (1 << hideFlag.ordinal());
    }

    @Override
    public List<String> getLore() {
        return this.lore == null ? null : new ArrayList<String>(this.lore);
    }

    @Override
    public void setLore(List<String> lore) { // too tired to think if .clone is better
        if (lore == null) {
            this.lore = null;
        } else {
            if (this.lore == null) {
                safelyAdd(lore, this.lore = new ArrayList<String>(lore.size()), Integer.MAX_VALUE);
            } else {
                this.lore.clear();
                safelyAdd(lore, this.lore, Integer.MAX_VALUE);
            }
        }
    }

    @Override
    public int getRepairCost() {
        return repairCost;
    }

    @Override
    public void setRepairCost(int cost) { // TODO: Does this have limits?
        repairCost = cost;
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    @Override
    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof CraftMetaItem)) {
            return false;
        }
        return CraftItemFactory.instance().equals(this, (ItemMeta) object);
    }

    /**
     * This method is almost as weird as notUncommon.
     * Only return false if your common internals are unequal.
     * Checking your own internals is redundant if you are not common, as notUncommon is meant for checking those 'not common' variables.
     */
    @Overridden
    boolean equalsCommon(CraftMetaItem that) {
        return ((this.hasDisplayName() ? that.hasDisplayName() && this.displayName.equals(that.displayName) : !that.hasDisplayName()))
                && (this.hasLocalizedName()? that.hasLocalizedName()&& this.locName.equals(that.locName) : !that.hasLocalizedName())
                && (this.hasEnchants() ? that.hasEnchants() && this.enchantments.equals(that.enchantments) : !that.hasEnchants())
                && (this.hasLore() ? that.hasLore() && this.lore.equals(that.lore) : !that.hasLore())
                && (this.hasRepairCost() ? that.hasRepairCost() && this.repairCost == that.repairCost : !that.hasRepairCost())
                && (this.unhandledTags.equals(that.unhandledTags))
                && (this.hideFlag == that.hideFlag)
                && (this.isUnbreakable() == that.isUnbreakable());
    }

    /**
     * This method is a bit weird...
     * Return true if you are a common class OR your uncommon parts are empty.
     * Empty uncommon parts implies the NBT data would be equivalent if both were applied to an item
     */
    @Overridden
    boolean notUncommon(CraftMetaItem meta) {
        return true;
    }

    @Override
    public final int hashCode() {
        return applyHash();
    }

    @Overridden
    int applyHash() {
        int hash = 3;
        hash = 61 * hash + (hasDisplayName() ? this.displayName.hashCode() : 0);
        hash = 61 * hash + (hasLocalizedName()? this.locName.hashCode() : 0);
        hash = 61 * hash + (hasLore() ? this.lore.hashCode() : 0);
        hash = 61 * hash + (hasEnchants() ? this.enchantments.hashCode() : 0);
        hash = 61 * hash + (hasRepairCost() ? this.repairCost : 0);
        hash = 61 * hash + unhandledTags.hashCode();
        hash = 61 * hash + hideFlag;
        hash = 61 * hash + (isUnbreakable() ? 1231 : 1237);
        return hash;
    }

    @Overridden
    @Override
    public CraftMetaItem clone() {
        try {
            CraftMetaItem clone = (CraftMetaItem) super.clone();
            if (this.lore != null) {
                clone.lore = new ArrayList<String>(this.lore);
            }
            if (this.enchantments != null) {
                clone.enchantments = new EnchantmentMap(this.enchantments); // Paper
            }
            clone.hideFlag = this.hideFlag;
            clone.unbreakable = this.unbreakable;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    @Override
    public final Map<String, Object> serialize() {
        ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
        map.put(SerializableMeta.TYPE_FIELD, SerializableMeta.classMap.get(getClass()));
        serialize(map);
        return map.build();
    }

    @Overridden
    ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
        if (hasDisplayName()) {
            builder.put(NAME.BUKKIT, displayName);
        }
        if (hasLocalizedName()) {
            builder.put(LOCNAME.BUKKIT, locName);
        }

        if (hasLore()) {
            builder.put(LORE.BUKKIT, ImmutableList.copyOf(lore));
        }

        serializeEnchantments(enchantments, builder, ENCHANTMENTS);

        if (hasRepairCost()) {
            builder.put(REPAIR.BUKKIT, repairCost);
        }

        List<String> hideFlags = new ArrayList<String>();
        for (ItemFlag hideFlagEnum : getItemFlags()) {
            hideFlags.add(hideFlagEnum.name());
        }
        if (!hideFlags.isEmpty()) {
            builder.put(HIDEFLAGS.BUKKIT, hideFlags);
        }

        if (isUnbreakable()) {
            builder.put(UNBREAKABLE.BUKKIT, unbreakable);
        }

        final Map<String, NBTBase> internalTags = new HashMap<String, NBTBase>(unhandledTags);
        serializeInternal(internalTags);
        if (!internalTags.isEmpty()) {
            NBTTagCompound internal = new NBTTagCompound();
            for (Map.Entry<String, NBTBase> e : internalTags.entrySet()) {
                internal.func_74782_a(e.getKey(), e.getValue());
            }
            try {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                CompressedStreamTools.func_74799_a(internal, buf);
                builder.put("internal", Base64.encodeBase64String(buf.toByteArray()));
            } catch (IOException ex) {
                Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return builder;
    }

    void serializeInternal(final Map<String, NBTBase> unhandledTags) {
    }

    static void serializeEnchantments(Map<Enchantment, Integer> enchantments, ImmutableMap.Builder<String, Object> builder, ItemMetaKey key) {
        if (enchantments == null || enchantments.isEmpty()) {
            return;
        }

        ImmutableMap.Builder<String, Integer> enchants = ImmutableMap.builder();
        for (Map.Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
            enchants.put(enchant.getKey().getName(), enchant.getValue());
        }

        builder.put(key.BUKKIT, enchants.build());
    }

    static void safelyAdd(Iterable<?> addFrom, Collection<String> addTo, int maxItemLength) {
        if (addFrom == null) {
            return;
        }

        for (Object object : addFrom) {
            if (!(object instanceof String)) {
                if (object != null) {
                    throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
                }

                addTo.add("");
            } else {
                String page = object.toString();

                if (page.length() > maxItemLength) {
                    page = page.substring(0, maxItemLength);
                }

                addTo.add(page);
            }
        }
    }

    static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench) {
        if (enchantments == null || enchantments.isEmpty()) {
            return false;
        }

        for (Enchantment enchant : enchantments.keySet()) {
            if (enchant.conflictsWith(ench)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final String toString() {
        return SerializableMeta.classMap.get(getClass()) + "_META:" + serialize(); // TODO: cry
    }

    public static Set<String> getHandledTags() {
        synchronized (HANDLED_TAGS) {
            if (HANDLED_TAGS.isEmpty()) {
                HANDLED_TAGS.addAll(Arrays.asList(
                        DISPLAY.NBT,
                        REPAIR.NBT,
                        ENCHANTMENTS.NBT,
                        HIDEFLAGS.NBT,
                        UNBREAKABLE.NBT,
                        CraftMetaMap.MAP_SCALING.NBT,
                        CraftMetaPotion.POTION_EFFECTS.NBT,
                        CraftMetaPotion.DEFAULT_POTION.NBT,
                        CraftMetaSkull.SKULL_OWNER.NBT,
                        CraftMetaSkull.SKULL_PROFILE.NBT,
                        CraftMetaSpawnEgg.ENTITY_TAG.NBT,
                        CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT,
                        CraftMetaBook.BOOK_TITLE.NBT,
                        CraftMetaBook.BOOK_AUTHOR.NBT,
                        CraftMetaBook.BOOK_PAGES.NBT,
                        CraftMetaBook.RESOLVED.NBT,
                        CraftMetaBook.GENERATION.NBT,
                        CraftMetaFirework.FIREWORKS.NBT,
                        CraftMetaEnchantedBook.STORED_ENCHANTMENTS.NBT,
                        CraftMetaCharge.EXPLOSION.NBT,
                        CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT,
                        CraftMetaKnowledgeBook.BOOK_RECIPES.NBT,
                        // Paper start
                        CraftMetaArmorStand.ENTITY_TAG.NBT,
                        CraftMetaArmorStand.INVISIBLE.NBT,
                        CraftMetaArmorStand.NO_BASE_PLATE.NBT,
                        CraftMetaArmorStand.SHOW_ARMS.NBT,
                        CraftMetaArmorStand.SMALL.NBT,
                        CraftMetaArmorStand.MARKER.NBT
                        // Paper end
                ));
            }
            return HANDLED_TAGS;
        }
    }

    // Paper start
    private static class EnchantmentMap extends TreeMap<Enchantment, Integer> {
        private EnchantmentMap(Map<Enchantment, Integer> enchantments) {
            this();
            putAll(enchantments);
        }

        private EnchantmentMap() {
            super((o1, o2) -> ((Integer) o1.getId()).compareTo(o2.getId()));
        }

        @Override
        public EnchantmentMap clone() {
            return (EnchantmentMap) super.clone();
        }
    }
    // Paper end

    // Spigot start
    private final Spigot spigot = new Spigot()
    {
        @Override
        public void setUnbreakable(boolean setUnbreakable)
        {
            CraftMetaItem.this.setUnbreakable(setUnbreakable);
        }

        @Override
        public boolean isUnbreakable()
        {
            return CraftMetaItem.this.unbreakable;
        }
    };

    @Override
    public Spigot spigot()
    {
        return spigot;
    }
    // Spigot end
}
