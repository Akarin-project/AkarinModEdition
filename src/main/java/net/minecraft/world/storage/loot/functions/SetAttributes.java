package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.LootItemFunctionSetAttribute.a;
import net.minecraft.server.LootItemFunctionSetAttribute.b;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetAttributes extends LootFunction {

    private static final Logger field_186560_a = LogManager.getLogger();
    private final LootItemFunctionSetAttribute.a[] field_186561_b;

    public SetAttributes(LootCondition[] alootitemcondition, LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute_a) {
        super(alootitemcondition);
        this.field_186561_b = alootitemfunctionsetattribute_a;
    }

    public ItemStack func_186553_a(ItemStack itemstack, Random random, LootContext loottableinfo) {
        LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute_a = this.field_186561_b;
        int i = alootitemfunctionsetattribute_a.length;

        for (int j = 0; j < i; ++j) {
            LootItemFunctionSetAttribute.a lootitemfunctionsetattribute_a = alootitemfunctionsetattribute_a[j];
            UUID uuid = lootitemfunctionsetattribute_a.e;

            if (uuid == null) {
                uuid = UUID.randomUUID();
            }

            EntityEquipmentSlot enumitemslot = lootitemfunctionsetattribute_a.f[random.nextInt(lootitemfunctionsetattribute_a.f.length)];

            itemstack.func_185129_a(lootitemfunctionsetattribute_a.b, new AttributeModifier(uuid, lootitemfunctionsetattribute_a.a, (double) lootitemfunctionsetattribute_a.d.func_186507_b(random), lootitemfunctionsetattribute_a.c), enumitemslot);
        }

        return itemstack;
    }

    static class a {

        private final String a;
        private final String b;
        private final int c;
        private final RandomValueRange d;
        @Nullable
        private final UUID e;
        private final EntityEquipmentSlot[] f;

        private a(String s, String s1, int i, RandomValueRange lootvaluebounds, EntityEquipmentSlot[] aenumitemslot, @Nullable UUID uuid) {
            this.a = s;
            this.b = s1;
            this.c = i;
            this.d = lootvaluebounds;
            this.e = uuid;
            this.f = aenumitemslot;
        }

        public JsonObject a(JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();

            jsonobject.addProperty("name", this.a);
            jsonobject.addProperty("attribute", this.b);
            jsonobject.addProperty("operation", a(this.c));
            jsonobject.add("amount", jsonserializationcontext.serialize(this.d));
            if (this.e != null) {
                jsonobject.addProperty("id", this.e.toString());
            }

            if (this.f.length == 1) {
                jsonobject.addProperty("slot", this.f[0].func_188450_d());
            } else {
                JsonArray jsonarray = new JsonArray();
                EntityEquipmentSlot[] aenumitemslot = this.f;
                int i = aenumitemslot.length;

                for (int j = 0; j < i; ++j) {
                    EntityEquipmentSlot enumitemslot = aenumitemslot[j];

                    jsonarray.add(new JsonPrimitive(enumitemslot.func_188450_d()));
                }

                jsonobject.add("slot", jsonarray);
            }

            return jsonobject;
        }

        public static LootItemFunctionSetAttribute.a a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            String s = JsonUtils.func_151200_h(jsonobject, "name");
            String s1 = JsonUtils.func_151200_h(jsonobject, "attribute");
            int i = a(JsonUtils.func_151200_h(jsonobject, "operation"));
            RandomValueRange lootvaluebounds = (RandomValueRange) JsonUtils.func_188174_a(jsonobject, "amount", jsondeserializationcontext, RandomValueRange.class);
            UUID uuid = null;
            EntityEquipmentSlot[] aenumitemslot;

            if (JsonUtils.func_151205_a(jsonobject, "slot")) {
                aenumitemslot = new EntityEquipmentSlot[] { EntityEquipmentSlot.func_188451_a(JsonUtils.func_151200_h(jsonobject, "slot"))};
            } else {
                if (!JsonUtils.func_151202_d(jsonobject, "slot")) {
                    throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
                }

                JsonArray jsonarray = JsonUtils.func_151214_t(jsonobject, "slot");

                aenumitemslot = new EntityEquipmentSlot[jsonarray.size()];
                int j = 0;

                JsonElement jsonelement;

                for (Iterator iterator = jsonarray.iterator(); iterator.hasNext(); aenumitemslot[j++] = EntityEquipmentSlot.func_188451_a(JsonUtils.func_151206_a(jsonelement, "slot"))) {
                    jsonelement = (JsonElement) iterator.next();
                }

                if (aenumitemslot.length == 0) {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            }

            if (jsonobject.has("id")) {
                String s2 = JsonUtils.func_151200_h(jsonobject, "id");

                try {
                    uuid = UUID.fromString(s2);
                } catch (IllegalArgumentException illegalargumentexception) {
                    throw new JsonSyntaxException("Invalid attribute modifier id \'" + s2 + "\' (must be UUID format, with dashes)");
                }
            }

            return new LootItemFunctionSetAttribute.a(s, s1, i, lootvaluebounds, aenumitemslot, uuid);
        }

        private static String a(int i) {
            switch (i) {
            case 0:
                return "addition";

            case 1:
                return "multiply_base";

            case 2:
                return "multiply_total";

            default:
                throw new IllegalArgumentException("Unknown operation " + i);
            }
        }

        private static int a(String s) {
            if ("addition".equals(s)) {
                return 0;
            } else if ("multiply_base".equals(s)) {
                return 1;
            } else if ("multiply_total".equals(s)) {
                return 2;
            } else {
                throw new JsonSyntaxException("Unknown attribute modifier operation " + s);
            }
        }
    }

    public static class b extends LootItemFunction.a<SetAttributes> {

        public b() {
            super(new ResourceLocation("set_attributes"), SetAttributes.class);
        }

        public void a(JsonObject jsonobject, SetAttributes lootitemfunctionsetattribute, JsonSerializationContext jsonserializationcontext) {
            JsonArray jsonarray = new JsonArray();
            LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute_a = lootitemfunctionsetattribute.field_186561_b;
            int i = alootitemfunctionsetattribute_a.length;

            for (int j = 0; j < i; ++j) {
                LootItemFunctionSetAttribute.a lootitemfunctionsetattribute_a = alootitemfunctionsetattribute_a[j];

                jsonarray.add(lootitemfunctionsetattribute_a.a(jsonserializationcontext));
            }

            jsonobject.add("modifiers", jsonarray);
        }

        public SetAttributes a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootCondition[] alootitemcondition) {
            JsonArray jsonarray = JsonUtils.func_151214_t(jsonobject, "modifiers");
            LootItemFunctionSetAttribute.a[] alootitemfunctionsetattribute_a = new LootItemFunctionSetAttribute.a[jsonarray.size()];
            int i = 0;

            JsonElement jsonelement;

            for (Iterator iterator = jsonarray.iterator(); iterator.hasNext(); alootitemfunctionsetattribute_a[i++] = SetAttributes.field_186560_a.a(JsonUtils.func_151210_l(jsonelement, "modifier"), jsondeserializationcontext)) {
                jsonelement = (JsonElement) iterator.next();
            }

            if (alootitemfunctionsetattribute_a.length == 0) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            } else {
                return new SetAttributes(alootitemcondition, alootitemfunctionsetattribute_a);
            }
        }

        public LootFunction b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootCondition[] alootitemcondition) {
            return this.a(jsonobject, jsondeserializationcontext, alootitemcondition);
        }
    }
}
