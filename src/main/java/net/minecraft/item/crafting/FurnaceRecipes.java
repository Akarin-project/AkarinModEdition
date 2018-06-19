package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes {

    private static final FurnaceRecipes field_77606_a = new FurnaceRecipes();
    public Map<ItemStack, ItemStack> field_77604_b = Maps.newHashMap();
    private final Map<ItemStack, Float> field_77605_c = Maps.newHashMap();
    public Map<ItemStack,ItemStack> customRecipes = Maps.newHashMap(); // CraftBukkit - add field
    public Map<ItemStack,Float> customExperience = Maps.newHashMap(); // CraftBukkit - add field

    public static FurnaceRecipes func_77602_a() {
        return FurnaceRecipes.field_77606_a;
    }

    public FurnaceRecipes() {
        this.func_151393_a(Blocks.field_150366_p, new ItemStack(Items.field_151042_j), 0.7F);
        this.func_151393_a(Blocks.field_150352_o, new ItemStack(Items.field_151043_k), 1.0F);
        this.func_151393_a(Blocks.field_150482_ag, new ItemStack(Items.field_151045_i), 1.0F);
        this.func_151393_a(Blocks.field_150354_m, new ItemStack(Blocks.field_150359_w), 0.1F);
        this.func_151396_a(Items.field_151147_al, new ItemStack(Items.field_151157_am), 0.35F);
        this.func_151396_a(Items.field_151082_bd, new ItemStack(Items.field_151083_be), 0.35F);
        this.func_151396_a(Items.field_151076_bf, new ItemStack(Items.field_151077_bg), 0.35F);
        this.func_151396_a(Items.field_179558_bo, new ItemStack(Items.field_179559_bp), 0.35F);
        this.func_151396_a(Items.field_179561_bm, new ItemStack(Items.field_179557_bn), 0.35F);
        this.func_151393_a(Blocks.field_150347_e, new ItemStack(Blocks.field_150348_b), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.field_176248_b), new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.field_176251_N), 0.1F);
        this.func_151396_a(Items.field_151119_aD, new ItemStack(Items.field_151118_aC), 0.3F);
        this.func_151393_a(Blocks.field_150435_aG, new ItemStack(Blocks.field_150405_ch), 0.35F);
        this.func_151393_a(Blocks.field_150434_aF, new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.GREEN.func_176767_b()), 0.2F);
        this.func_151393_a(Blocks.field_150364_r, new ItemStack(Items.field_151044_h, 1, 1), 0.15F);
        this.func_151393_a(Blocks.field_150363_s, new ItemStack(Items.field_151044_h, 1, 1), 0.15F);
        this.func_151393_a(Blocks.field_150412_bA, new ItemStack(Items.field_151166_bC), 1.0F);
        this.func_151396_a(Items.field_151174_bG, new ItemStack(Items.field_151168_bH), 0.35F);
        this.func_151393_a(Blocks.field_150424_aL, new ItemStack(Items.field_151130_bT), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150360_v, 1, 1), new ItemStack(Blocks.field_150360_v, 1, 0), 0.15F);
        this.func_151396_a(Items.field_185161_cS, new ItemStack(Items.field_185162_cT), 0.1F);
        ItemFishFood.FishType[] aitemfish_enumfish = ItemFishFood.FishType.values();
        int i = aitemfish_enumfish.length;

        for (int j = 0; j < i; ++j) {
            ItemFishFood.FishType itemfish_enumfish = aitemfish_enumfish[j];

            if (itemfish_enumfish.func_150973_i()) {
                this.func_151394_a(new ItemStack(Items.field_151115_aP, 1, itemfish_enumfish.func_150976_a()), new ItemStack(Items.field_179566_aV, 1, itemfish_enumfish.func_150976_a()), 0.35F);
            }
        }

        this.func_151393_a(Blocks.field_150365_q, new ItemStack(Items.field_151044_h), 0.1F);
        this.func_151393_a(Blocks.field_150450_ax, new ItemStack(Items.field_151137_ax), 0.7F);
        this.func_151393_a(Blocks.field_150369_x, new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.BLUE.func_176767_b()), 0.2F);
        this.func_151393_a(Blocks.field_150449_bY, new ItemStack(Items.field_151128_bU), 0.2F);
        this.func_151396_a((Item) Items.field_151020_U, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151023_V, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151022_W, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151029_X, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151035_b, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151037_a, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151036_c, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151019_K, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151040_l, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151028_Y, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151030_Z, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151165_aa, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a((Item) Items.field_151167_ab, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151138_bX, new ItemStack(Items.field_191525_da), 0.1F);
        this.func_151396_a(Items.field_151005_D, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a(Items.field_151011_C, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a(Items.field_151006_E, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a(Items.field_151013_M, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a(Items.field_151010_B, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a((Item) Items.field_151169_ag, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a((Item) Items.field_151171_ah, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a((Item) Items.field_151149_ai, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a((Item) Items.field_151151_aj, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151396_a(Items.field_151136_bY, new ItemStack(Items.field_151074_bl), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.WHITE.func_176765_a()), new ItemStack(Blocks.field_192427_dB), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.ORANGE.func_176765_a()), new ItemStack(Blocks.field_192428_dC), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.MAGENTA.func_176765_a()), new ItemStack(Blocks.field_192429_dD), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.LIGHT_BLUE.func_176765_a()), new ItemStack(Blocks.field_192430_dE), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.YELLOW.func_176765_a()), new ItemStack(Blocks.field_192431_dF), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.LIME.func_176765_a()), new ItemStack(Blocks.field_192432_dG), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.PINK.func_176765_a()), new ItemStack(Blocks.field_192433_dH), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.GRAY.func_176765_a()), new ItemStack(Blocks.field_192434_dI), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.SILVER.func_176765_a()), new ItemStack(Blocks.field_192435_dJ), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.CYAN.func_176765_a()), new ItemStack(Blocks.field_192436_dK), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.PURPLE.func_176765_a()), new ItemStack(Blocks.field_192437_dL), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.BLUE.func_176765_a()), new ItemStack(Blocks.field_192438_dM), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.BROWN.func_176765_a()), new ItemStack(Blocks.field_192439_dN), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.GREEN.func_176765_a()), new ItemStack(Blocks.field_192440_dO), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.RED.func_176765_a()), new ItemStack(Blocks.field_192441_dP), 0.1F);
        this.func_151394_a(new ItemStack(Blocks.field_150406_ce, 1, EnumDyeColor.BLACK.func_176765_a()), new ItemStack(Blocks.field_192442_dQ), 0.1F);
    }

    // CraftBukkit start - add method
    public void registerRecipe(ItemStack itemstack, ItemStack itemstack1, float f) {
        this.customRecipes.put(itemstack, itemstack1);
        this.customExperience.put(itemstack, f);
    }
    // CraftBukkit end

    public void func_151393_a(Block block, ItemStack itemstack, float f) {
        this.func_151396_a(Item.func_150898_a(block), itemstack, f);
    }

    public void func_151396_a(Item item, ItemStack itemstack, float f) {
        this.func_151394_a(new ItemStack(item, 1, 32767), itemstack, f);
    }

    public void func_151394_a(ItemStack itemstack, ItemStack itemstack1, float f) {
        this.field_77604_b.put(itemstack, itemstack1);
        this.field_77605_c.put(itemstack1, Float.valueOf(f));
    }

    public ItemStack func_151395_a(ItemStack itemstack) {
        // CraftBukkit start - initialize to customRecipes
        boolean vanilla = false;
        Iterator<Entry<ItemStack, ItemStack>> iterator = this.customRecipes.entrySet().iterator();
        // CraftBukkit end

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                // CraftBukkit start - fall back to vanilla recipes
                if (!vanilla && !this.field_77604_b.isEmpty()) {
                    iterator = this.field_77604_b.entrySet().iterator();
                    vanilla = true;
                } else {
                    return ItemStack.field_190927_a;
                }
                // CraftBukkit end
            }

            entry = (Entry) iterator.next();
        } while (!this.func_151397_a(itemstack, (ItemStack) entry.getKey()));

        return (ItemStack) entry.getValue();
    }

    private boolean func_151397_a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack1.func_77973_b() == itemstack.func_77973_b() && (itemstack1.func_77960_j() == 32767 || itemstack1.func_77960_j() == itemstack.func_77960_j());
    }

    public Map<ItemStack, ItemStack> func_77599_b() {
        return this.field_77604_b;
    }

    public float func_151398_b(ItemStack itemstack) {
        // CraftBukkit start - initialize to customRecipes
        boolean vanilla = false;
        Iterator<Entry<ItemStack, Float>> iterator = this.customExperience.entrySet().iterator();
        // CraftBukkit end

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                // CraftBukkit start - fall back to vanilla recipes
                if (!vanilla && !this.field_77605_c.isEmpty()) {
                    iterator = this.field_77605_c.entrySet().iterator();
                    vanilla = true;
                } else {
                    return 0.0F;
                }
                // CraftBukkit end
            }

            entry = (Entry) iterator.next();
        } while (!this.func_151397_a(itemstack, (ItemStack) entry.getKey()));

        return ((Float) entry.getValue()).floatValue();
    }
}
