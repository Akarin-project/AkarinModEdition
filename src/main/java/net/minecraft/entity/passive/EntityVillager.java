package net.minecraft.entity.passive;

import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.EntityVillager.h;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.LootTableList;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftVillager;
import org.bukkit.craftbukkit.inventory.CraftMerchantRecipe;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftVillager;
import org.bukkit.craftbukkit.inventory.CraftMerchantRecipe;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
// CraftBukkit end

public class EntityVillager extends EntityAgeable implements INpc, IMerchant {

    private static final Logger field_190674_bx = LogManager.getLogger();
    private static final DataParameter<Integer> field_184752_bw = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187192_b);
    private int field_70955_e;
    private boolean field_70952_f;
    private boolean field_70953_g;
    Village field_70954_d;
    @Nullable
    private EntityPlayer field_70962_h;
    @Nullable
    public MerchantRecipeList field_70963_i; // PAIL private -> public
    private int field_70961_j;
    private boolean field_70959_by;
    private boolean field_175565_bs;
    public int field_70956_bz;
    private String field_82189_bL;
    public int field_175563_bv; // PAIL private -> public // PAIL rename careerID
    private int field_175562_bw;
    private boolean field_82190_bM;
    private boolean field_175564_by;
    public final InventoryBasic field_175560_bz;
    private static final EntityVillager.ITradeList[][][][] field_175561_bA = new EntityVillager.ITradeList[][][][] { { { { new EntityVillager.EmeraldForItems(Items.field_151015_O, new EntityVillager.PriceInfo(18, 22)), new EntityVillager.EmeraldForItems(Items.field_151174_bG, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.EmeraldForItems(Items.field_151172_bF, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.ListItemForEmeralds(Items.field_151025_P, new EntityVillager.PriceInfo(-4, -2))}, { new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150423_aK), new EntityVillager.PriceInfo(8, 13)), new EntityVillager.ListItemForEmeralds(Items.field_151158_bO, new EntityVillager.PriceInfo(-3, -2))}, { new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150440_ba), new EntityVillager.PriceInfo(7, 12)), new EntityVillager.ListItemForEmeralds(Items.field_151034_e, new EntityVillager.PriceInfo(-7, -5))}, { new EntityVillager.ListItemForEmeralds(Items.field_151106_aX, new EntityVillager.PriceInfo(-10, -6)), new EntityVillager.ListItemForEmeralds(Items.field_151105_aU, new EntityVillager.PriceInfo(1, 1))}}, { { new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ItemAndEmeraldToItem(Items.field_151115_aP, new EntityVillager.PriceInfo(6, 6), Items.field_179566_aV, new EntityVillager.PriceInfo(6, 6))}, { new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151112_aM, new EntityVillager.PriceInfo(7, 8))}}, { { new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150325_L), new EntityVillager.PriceInfo(16, 22)), new EntityVillager.ListItemForEmeralds(Items.field_151097_aZ, new EntityVillager.PriceInfo(3, 4))}, { new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L)), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 1), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 2), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 3), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 4), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 5), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 6), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 7), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 8), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 9), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 10), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 11), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 12), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 13), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 14), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 15), new EntityVillager.PriceInfo(1, 2))}}, { { new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.ListItemForEmeralds(Items.field_151032_g, new EntityVillager.PriceInfo(-12, -8))}, { new EntityVillager.ListItemForEmeralds(Items.field_151031_f, new EntityVillager.PriceInfo(2, 3)), new EntityVillager.ItemAndEmeraldToItem(Item.func_150898_a(Blocks.field_150351_n), new EntityVillager.PriceInfo(10, 10), Items.field_151145_ak, new EntityVillager.PriceInfo(6, 10))}}}, { { { new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36)), new EntityVillager.ListEnchantedBookForEmeralds()}, { new EntityVillager.EmeraldForItems(Items.field_151122_aG, new EntityVillager.PriceInfo(8, 10)), new EntityVillager.ListItemForEmeralds(Items.field_151111_aL, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150342_X), new EntityVillager.PriceInfo(3, 4))}, { new EntityVillager.EmeraldForItems(Items.field_151164_bB, new EntityVillager.PriceInfo(2, 2)), new EntityVillager.ListItemForEmeralds(Items.field_151113_aN, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150359_w), new EntityVillager.PriceInfo(-5, -3))}, { new EntityVillager.ListEnchantedBookForEmeralds()}, { new EntityVillager.ListEnchantedBookForEmeralds()}, { new EntityVillager.ListItemForEmeralds(Items.field_151057_cb, new EntityVillager.PriceInfo(20, 22))}}, { { new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36))}, { new EntityVillager.EmeraldForItems(Items.field_151111_aL, new EntityVillager.PriceInfo(1, 1))}, { new EntityVillager.ListItemForEmeralds(Items.field_151148_bJ, new EntityVillager.PriceInfo(7, 11))}, { new EntityVillager.h(new EntityVillager.PriceInfo(12, 20), "Monument", MapDecoration.Type.MONUMENT), new EntityVillager.h(new EntityVillager.PriceInfo(16, 28), "Mansion", MapDecoration.Type.MANSION)}}}, { { { new EntityVillager.EmeraldForItems(Items.field_151078_bh, new EntityVillager.PriceInfo(36, 40)), new EntityVillager.EmeraldForItems(Items.field_151043_k, new EntityVillager.PriceInfo(8, 10))}, { new EntityVillager.ListItemForEmeralds(Items.field_151137_ax, new EntityVillager.PriceInfo(-4, -1)), new EntityVillager.ListItemForEmeralds(new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.BLUE.func_176767_b()), new EntityVillager.PriceInfo(-2, -1))}, { new EntityVillager.ListItemForEmeralds(Items.field_151079_bi, new EntityVillager.PriceInfo(4, 7)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150426_aN), new EntityVillager.PriceInfo(-3, -1))}, { new EntityVillager.ListItemForEmeralds(Items.field_151062_by, new EntityVillager.PriceInfo(3, 11))}}}, { { { new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151028_Y, new EntityVillager.PriceInfo(4, 6))}, { new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListItemForEmeralds(Items.field_151030_Z, new EntityVillager.PriceInfo(10, 14))}, { new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151163_ad, new EntityVillager.PriceInfo(16, 19))}, { new EntityVillager.ListItemForEmeralds(Items.field_151029_X, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.field_151022_W, new EntityVillager.PriceInfo(9, 11)), new EntityVillager.ListItemForEmeralds(Items.field_151020_U, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.field_151023_V, new EntityVillager.PriceInfo(11, 15))}}, { { new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151036_c, new EntityVillager.PriceInfo(6, 8))}, { new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151040_l, new EntityVillager.PriceInfo(9, 10))}, { new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151048_u, new EntityVillager.PriceInfo(12, 15)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151056_x, new EntityVillager.PriceInfo(9, 12))}}, { { new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151037_a, new EntityVillager.PriceInfo(5, 7))}, { new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151035_b, new EntityVillager.PriceInfo(9, 11))}, { new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151046_w, new EntityVillager.PriceInfo(12, 15))}}}, { { { new EntityVillager.EmeraldForItems(Items.field_151147_al, new EntityVillager.PriceInfo(14, 18)), new EntityVillager.EmeraldForItems(Items.field_151076_bf, new EntityVillager.PriceInfo(14, 18))}, { new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151157_am, new EntityVillager.PriceInfo(-7, -5)), new EntityVillager.ListItemForEmeralds(Items.field_151077_bg, new EntityVillager.PriceInfo(-8, -6))}}, { { new EntityVillager.EmeraldForItems(Items.field_151116_aA, new EntityVillager.PriceInfo(9, 12)), new EntityVillager.ListItemForEmeralds(Items.field_151026_S, new EntityVillager.PriceInfo(2, 4))}, { new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151027_R, new EntityVillager.PriceInfo(7, 12))}, { new EntityVillager.ListItemForEmeralds(Items.field_151141_av, new EntityVillager.PriceInfo(8, 10))}}}, { new EntityVillager.ITradeList[0][]}};

    public EntityVillager(World world) {
        this(world, 0);
    }

    public EntityVillager(World world, int i) {
        super(world);
        this.field_175560_bz = new InventoryBasic("Items", false, 8, (CraftVillager) this.getBukkitEntity()); // CraftBukkit add argument
        this.func_70938_b(i);
        this.func_70105_a(0.6F, 1.95F);
        ((PathNavigateGround) this.func_70661_as()).func_179688_b(true);
        this.func_98053_h(true);
    }

    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
        this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
        this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
        this.field_70714_bg.func_75776_a(1, new EntityAITradePlayer(this));
        this.field_70714_bg.func_75776_a(1, new EntityAILookAtTradePlayer(this));
        this.field_70714_bg.func_75776_a(2, new EntityAIMoveIndoors(this));
        this.field_70714_bg.func_75776_a(3, new EntityAIRestrictOpenDoor(this));
        this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
        this.field_70714_bg.func_75776_a(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.field_70714_bg.func_75776_a(6, new EntityAIVillagerMate(this));
        this.field_70714_bg.func_75776_a(7, new EntityAIFollowGolem(this));
        this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.field_70714_bg.func_75776_a(9, new EntityAIVillagerInteract(this));
        this.field_70714_bg.func_75776_a(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    private void func_175552_ct() {
        if (!this.field_175564_by) {
            this.field_175564_by = true;
            if (this.func_70631_g_()) {
                this.field_70714_bg.func_75776_a(8, new EntityAIPlay(this, 0.32D));
            } else if (this.func_70946_n() == 0) {
                this.field_70714_bg.func_75776_a(6, new EntityAIHarvestFarmland(this, 0.6D));
            }

        }
    }

    protected void func_175500_n() {
        if (this.func_70946_n() == 0) {
            this.field_70714_bg.func_75776_a(8, new EntityAIHarvestFarmland(this, 0.6D));
        }

        super.func_175500_n();
    }

    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
    }

    // Spigot Start
    @Override
    public void inactiveTick() {
        // SPIGOT-3874
        if (field_70170_p.spigotConfig.tickInactiveVillagers) {
            // SPIGOT-3894
            Chunk startingChunk = this.field_70170_p.getChunkIfLoaded(MathHelper.func_76128_c(this.field_70165_t) >> 4, MathHelper.func_76128_c(this.field_70161_v) >> 4);
            if (!(startingChunk != null && startingChunk.areNeighborsLoaded(1))) {
                return;
            }
            this.func_70619_bc(); // SPIGOT-3846
        }
        super.inactiveTick();
    }
    // Spigot End

    protected void func_70619_bc() {
        if (--this.field_70955_e <= 0) {
            BlockPos blockposition = new BlockPos(this);

            this.field_70170_p.func_175714_ae().func_176060_a(blockposition);
            this.field_70955_e = 70 + this.field_70146_Z.nextInt(50);
            this.field_70954_d = this.field_70170_p.func_175714_ae().func_176056_a(blockposition, 32);
            if (this.field_70954_d == null) {
                this.func_110177_bN();
            } else {
                BlockPos blockposition1 = this.field_70954_d.func_180608_a();

                this.func_175449_a(blockposition1, this.field_70954_d.func_75568_b());
                if (this.field_82190_bM) {
                    this.field_82190_bM = false;
                    this.field_70954_d.func_82683_b(5);
                }
            }
        }

        if (!this.func_70940_q() && this.field_70961_j > 0) {
            --this.field_70961_j;
            if (this.field_70961_j <= 0) {
                if (this.field_70959_by) {
                    Iterator iterator = this.field_70963_i.iterator();

                    while (iterator.hasNext()) {
                        MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

                        if (merchantrecipe.func_82784_g()) {
                            // CraftBukkit start
                            int bonus = this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(6) + 2;
                            VillagerReplenishTradeEvent event = new VillagerReplenishTradeEvent((Villager) this.getBukkitEntity(), merchantrecipe.asBukkit(), bonus);
                            Bukkit.getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                merchantrecipe.func_82783_a(event.getBonus());
                            }
                            // CraftBukkit end
                        }
                    }

                    this.func_175554_cu();
                    this.field_70959_by = false;
                    if (this.field_70954_d != null && this.field_82189_bL != null) {
                        this.field_70170_p.func_72960_a(this, (byte) 14);
                        this.field_70954_d.func_82688_a(this.field_82189_bL, 1);
                    }
                }

                this.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200, 0));
            }
        }

        super.func_70619_bc();
    }

    public boolean func_184645_a(EntityPlayer entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.func_184586_b(enumhand);
        boolean flag = itemstack.func_77973_b() == Items.field_151057_cb;

        if (flag) {
            itemstack.func_111282_a(entityhuman, (EntityLivingBase) this, enumhand);
            return true;
        } else if (!this.func_190669_a(itemstack, this.getClass()) && this.func_70089_S() && !this.func_70940_q() && !this.func_70631_g_()) {
            if (this.field_70963_i == null) {
                this.func_175554_cu();
            }

            if (enumhand == EnumHand.MAIN_HAND) {
                entityhuman.func_71029_a(StatList.field_188074_H);
            }

            if (!this.field_70170_p.field_72995_K && !this.field_70963_i.isEmpty()) {
                this.func_70932_a_(entityhuman);
                entityhuman.func_180472_a(this);
            } else if (this.field_70963_i.isEmpty()) {
                return super.func_184645_a(entityhuman, enumhand);
            }

            return true;
        } else {
            return super.func_184645_a(entityhuman, enumhand);
        }
    }

    protected void func_70088_a() {
        super.func_70088_a();
        this.field_70180_af.func_187214_a(EntityVillager.field_184752_bw, Integer.valueOf(0));
    }

    public static void func_189785_b(DataFixer dataconvertermanager) {
        EntityLiving.func_189752_a(dataconvertermanager, EntityVillager.class);
        dataconvertermanager.func_188258_a(FixTypes.ENTITY, (IDataWalker) (new ItemStackDataLists(EntityVillager.class, new String[] { "Inventory"})));
        dataconvertermanager.func_188258_a(FixTypes.ENTITY, new IDataWalker() {
            public NBTTagCompound func_188266_a(IDataFixer dataconverter, NBTTagCompound nbttagcompound, int i) {
                if (EntityList.func_191306_a(EntityVillager.class).equals(new ResourceLocation(nbttagcompound.func_74779_i("id"))) && nbttagcompound.func_150297_b("Offers", 10)) {
                    NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("Offers");

                    if (nbttagcompound1.func_150297_b("Recipes", 9)) {
                        NBTTagList nbttaglist = nbttagcompound1.func_150295_c("Recipes", 10);

                        for (int j = 0; j < nbttaglist.func_74745_c(); ++j) {
                            NBTTagCompound nbttagcompound2 = nbttaglist.func_150305_b(j);

                            DataFixesManager.func_188277_a(dataconverter, nbttagcompound2, i, "buy");
                            DataFixesManager.func_188277_a(dataconverter, nbttagcompound2, i, "buyB");
                            DataFixesManager.func_188277_a(dataconverter, nbttagcompound2, i, "sell");
                            nbttaglist.func_150304_a(j, nbttagcompound2);
                        }
                    }
                }

                return nbttagcompound;
            }
        });
    }

    public void func_70014_b(NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.func_74768_a("Profession", this.func_70946_n());
        nbttagcompound.func_74768_a("Riches", this.field_70956_bz);
        nbttagcompound.func_74768_a("Career", this.field_175563_bv);
        nbttagcompound.func_74768_a("CareerLevel", this.field_175562_bw);
        nbttagcompound.func_74757_a("Willing", this.field_175565_bs);
        if (this.field_70963_i != null) {
            nbttagcompound.func_74782_a("Offers", this.field_70963_i.func_77202_a());
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
            ItemStack itemstack = this.field_175560_bz.func_70301_a(i);

            if (!itemstack.func_190926_b()) {
                nbttaglist.func_74742_a(itemstack.func_77955_b(new NBTTagCompound()));
            }
        }

        nbttagcompound.func_74782_a("Inventory", nbttaglist);
    }

    public void func_70037_a(NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        this.func_70938_b(nbttagcompound.func_74762_e("Profession"));
        this.field_70956_bz = nbttagcompound.func_74762_e("Riches");
        this.field_175563_bv = nbttagcompound.func_74762_e("Career");
        this.field_175562_bw = nbttagcompound.func_74762_e("CareerLevel");
        this.field_175565_bs = nbttagcompound.func_74767_n("Willing");
        if (nbttagcompound.func_150297_b("Offers", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("Offers");

            this.field_70963_i = new MerchantRecipeList(nbttagcompound1);
        }

        NBTTagList nbttaglist = nbttagcompound.func_150295_c("Inventory", 10);

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            ItemStack itemstack = new ItemStack(nbttaglist.func_150305_b(i));

            if (!itemstack.func_190926_b()) {
                this.field_175560_bz.func_174894_a(itemstack);
            }
        }

        this.func_98053_h(true);
        this.func_175552_ct();
    }

    protected boolean func_70692_ba() {
        return false;
    }

    protected SoundEvent func_184639_G() {
        return this.func_70940_q() ? SoundEvents.field_187914_gn : SoundEvents.field_187910_gj;
    }

    protected SoundEvent func_184601_bQ(DamageSource damagesource) {
        return SoundEvents.field_187912_gl;
    }

    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187911_gk;
    }

    @Nullable
    protected ResourceLocation func_184647_J() {
        return LootTableList.field_191184_at;
    }

    public void func_70938_b(int i) {
        this.field_70180_af.func_187227_b(EntityVillager.field_184752_bw, Integer.valueOf(i));
    }

    public int func_70946_n() {
        return Math.max(((Integer) this.field_70180_af.func_187225_a(EntityVillager.field_184752_bw)).intValue() % 6, 0);
    }

    public boolean func_70941_o() {
        return this.field_70952_f;
    }

    public void func_70947_e(boolean flag) {
        this.field_70952_f = flag;
    }

    public void func_70939_f(boolean flag) {
        this.field_70953_g = flag;
    }

    public boolean func_70945_p() {
        return this.field_70953_g;
    }

    public void func_70604_c(@Nullable EntityLivingBase entityliving) {
        super.func_70604_c(entityliving);
        if (this.field_70954_d != null && entityliving != null) {
            this.field_70954_d.func_75575_a(entityliving);
            if (entityliving instanceof EntityPlayer) {
                byte b0 = -1;

                if (this.func_70631_g_()) {
                    b0 = -3;
                }

                this.field_70954_d.func_82688_a(entityliving.func_70005_c_(), b0);
                if (this.func_70089_S()) {
                    this.field_70170_p.func_72960_a(this, (byte) 13);
                }
            }
        }

    }

    public void func_70645_a(DamageSource damagesource) {
        if (this.field_70954_d != null) {
            Entity entity = damagesource.func_76346_g();

            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.field_70954_d.func_82688_a(entity.func_70005_c_(), -2);
                } else if (entity instanceof IMob) {
                    this.field_70954_d.func_82692_h();
                }
            } else {
                EntityPlayer entityhuman = this.field_70170_p.func_72890_a(this, 16.0D);

                if (entityhuman != null) {
                    this.field_70954_d.func_82692_h();
                }
            }
        }

        super.func_70645_a(damagesource);
    }

    public void func_70932_a_(@Nullable EntityPlayer entityhuman) {
        this.field_70962_h = entityhuman;
    }

    @Nullable
    public EntityPlayer func_70931_l_() {
        return this.field_70962_h;
    }

    public boolean func_70940_q() {
        return this.field_70962_h != null;
    }

    public boolean func_175550_n(boolean flag) {
        if (!this.field_175565_bs && flag && this.func_175553_cp()) {
            boolean flag1 = false;

            for (int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
                ItemStack itemstack = this.field_175560_bz.func_70301_a(i);

                if (!itemstack.func_190926_b()) {
                    if (itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3) {
                        flag1 = true;
                        this.field_175560_bz.func_70298_a(i, 3);
                    } else if ((itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF) && itemstack.func_190916_E() >= 12) {
                        flag1 = true;
                        this.field_175560_bz.func_70298_a(i, 12);
                    }
                }

                if (flag1) {
                    this.field_70170_p.func_72960_a(this, (byte) 18);
                    this.field_175565_bs = true;
                    break;
                }
            }
        }

        return this.field_175565_bs;
    }

    public void func_175549_o(boolean flag) {
        this.field_175565_bs = flag;
    }

    public void func_70933_a(MerchantRecipe merchantrecipe) {
        merchantrecipe.func_77399_f();
        this.field_70757_a = -this.func_70627_aG();
        this.func_184185_a(SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
        int i = 3 + this.field_70146_Z.nextInt(4);

        if (merchantrecipe.func_180321_e() == 1 || this.field_70146_Z.nextInt(5) == 0) {
            this.field_70961_j = 40;
            this.field_70959_by = true;
            this.field_175565_bs = true;
            if (this.field_70962_h != null) {
                this.field_82189_bL = this.field_70962_h.func_70005_c_();
            } else {
                this.field_82189_bL = null;
            }

            i += 5;
        }

        if (merchantrecipe.func_77394_a().func_77973_b() == Items.field_151166_bC) {
            this.field_70956_bz += merchantrecipe.func_77394_a().func_190916_E();
        }

        if (merchantrecipe.func_180322_j()) {
            this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, i, org.bukkit.entity.ExperienceOrb.SpawnReason.VILLAGER_TRADE, field_70962_h, this)); // Paper
        }

        if (this.field_70962_h instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192138_r.func_192234_a((EntityPlayerMP) this.field_70962_h, this, merchantrecipe.func_77397_d());
        }

    }

    public void func_110297_a_(ItemStack itemstack) {
        if (!this.field_70170_p.field_72995_K && this.field_70757_a > -this.func_70627_aG() + 20) {
            this.field_70757_a = -this.func_70627_aG();
            this.func_184185_a(itemstack.func_190926_b() ? SoundEvents.field_187913_gm : SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
        }

    }

    @Nullable
    public MerchantRecipeList func_70934_b(EntityPlayer entityhuman) {
        if (this.field_70963_i == null) {
            this.func_175554_cu();
        }

        return this.field_70963_i;
    }

    public void func_175554_cu() { // CraftBukkit private -> public // PAIL rename populateTrades
        EntityVillager.ITradeList[][][] aentityvillager_imerchantrecipeoption = EntityVillager.field_175561_bA[this.func_70946_n()];

        if (this.field_175563_bv != 0 && this.field_175562_bw != 0) {
            ++this.field_175562_bw;
        } else {
            this.field_175563_bv = this.field_70146_Z.nextInt(aentityvillager_imerchantrecipeoption.length) + 1;
            this.field_175562_bw = 1;
        }

        if (this.field_70963_i == null) {
            this.field_70963_i = new MerchantRecipeList();
        }

        int i = this.field_175563_bv - 1;
        int j = this.field_175562_bw - 1;

        if (i >= 0 && i < aentityvillager_imerchantrecipeoption.length) {
            EntityVillager.ITradeList[][] aentityvillager_imerchantrecipeoption1 = aentityvillager_imerchantrecipeoption[i];

            if (j >= 0 && j < aentityvillager_imerchantrecipeoption1.length) {
                EntityVillager.ITradeList[] aentityvillager_imerchantrecipeoption2 = aentityvillager_imerchantrecipeoption1[j];
                EntityVillager.ITradeList[] aentityvillager_imerchantrecipeoption3 = aentityvillager_imerchantrecipeoption2;
                int k = aentityvillager_imerchantrecipeoption2.length;

                for (int l = 0; l < k; ++l) {
                    EntityVillager.ITradeList entityvillager_imerchantrecipeoption = aentityvillager_imerchantrecipeoption3[l];

                    // CraftBukkit start
                    // this is a hack. this must be done because otherwise, if
                    // mojang adds a new type of villager merchant option, it will need to
                    // have event handling added manually. this is better than having to do that.
                    MerchantRecipeList list = new MerchantRecipeList();
                    entityvillager_imerchantrecipeoption.func_190888_a(this, list, this.field_70146_Z);
                    for (MerchantRecipe recipe : list) {
                        VillagerAcquireTradeEvent event = new VillagerAcquireTradeEvent((Villager) getBukkitEntity(), recipe.asBukkit());
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            this.field_70963_i.add(CraftMerchantRecipe.fromBukkit(event.getRecipe()).toMinecraft());
                        }
                    }
                    // CraftBukkit end
                }
            }

        }
    }

    public World func_190670_t_() {
        return this.field_70170_p;
    }

    public BlockPos func_190671_u_() {
        return new BlockPos(this);
    }

    public ITextComponent func_145748_c_() {
        Team scoreboardteambase = this.func_96124_cp();
        String s = this.func_95999_t();

        if (s != null && !s.isEmpty()) {
            TextComponentString chatcomponenttext = new TextComponentString(ScorePlayerTeam.func_96667_a(scoreboardteambase, s));

            chatcomponenttext.func_150256_b().func_150209_a(this.func_174823_aP());
            chatcomponenttext.func_150256_b().func_179989_a(this.func_189512_bd());
            return chatcomponenttext;
        } else {
            if (this.field_70963_i == null) {
                this.func_175554_cu();
            }

            String s1 = null;

            switch (this.func_70946_n()) {
            case 0:
                if (this.field_175563_bv == 1) {
                    s1 = "farmer";
                } else if (this.field_175563_bv == 2) {
                    s1 = "fisherman";
                } else if (this.field_175563_bv == 3) {
                    s1 = "shepherd";
                } else if (this.field_175563_bv == 4) {
                    s1 = "fletcher";
                }
                break;

            case 1:
                if (this.field_175563_bv == 1) {
                    s1 = "librarian";
                } else if (this.field_175563_bv == 2) {
                    s1 = "cartographer";
                }
                break;

            case 2:
                s1 = "cleric";
                break;

            case 3:
                if (this.field_175563_bv == 1) {
                    s1 = "armor";
                } else if (this.field_175563_bv == 2) {
                    s1 = "weapon";
                } else if (this.field_175563_bv == 3) {
                    s1 = "tool";
                }
                break;

            case 4:
                if (this.field_175563_bv == 1) {
                    s1 = "butcher";
                } else if (this.field_175563_bv == 2) {
                    s1 = "leather";
                }
                break;

            case 5:
                s1 = "nitwit";
            }

            if (s1 != null) {
                TextComponentTranslation chatmessage = new TextComponentTranslation("entity.Villager." + s1, new Object[0]);

                chatmessage.func_150256_b().func_150209_a(this.func_174823_aP());
                chatmessage.func_150256_b().func_179989_a(this.func_189512_bd());
                if (scoreboardteambase != null) {
                    chatmessage.func_150256_b().func_150238_a(scoreboardteambase.func_178775_l());
                }

                return chatmessage;
            } else {
                return super.func_145748_c_();
            }
        }
    }

    public float func_70047_e() {
        return this.func_70631_g_() ? 0.81F : 1.62F;
    }

    @Nullable
    public IEntityLivingData func_180482_a(DifficultyInstance difficultydamagescaler, @Nullable IEntityLivingData groupdataentity) {
        return this.func_190672_a(difficultydamagescaler, groupdataentity, true);
    }

    public IEntityLivingData func_190672_a(DifficultyInstance difficultydamagescaler, @Nullable IEntityLivingData groupdataentity, boolean flag) {
        groupdataentity = super.func_180482_a(difficultydamagescaler, groupdataentity);
        if (flag) {
            this.func_70938_b(this.field_70170_p.field_73012_v.nextInt(6));
        }

        this.func_175552_ct();
        this.func_175554_cu();
        return groupdataentity;
    }

    public void func_82187_q() {
        this.field_82190_bM = true;
    }

    public EntityVillager func_90011_a(EntityAgeable entityageable) {
        EntityVillager entityvillager = new EntityVillager(this.field_70170_p);

        entityvillager.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entityvillager)), (IEntityLivingData) null);
        return entityvillager;
    }

    public boolean func_184652_a(EntityPlayer entityhuman) {
        return false;
    }

    public void func_70077_a(EntityLightningBolt entitylightning) {
        if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
            EntityWitch entitywitch = new EntityWitch(this.field_70170_p);

            // Paper start
            if (org.bukkit.craftbukkit.event.CraftEventFactory.callEntityZapEvent(this, entitylightning, entitywitch).isCancelled()) {
                return;
            }
            // Paper end

            entitywitch.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
            entitywitch.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entitywitch)), (IEntityLivingData) null);
            entitywitch.func_94061_f(this.func_175446_cd());
            if (this.func_145818_k_()) {
                entitywitch.func_96094_a(this.func_95999_t());
                entitywitch.func_174805_g(this.func_174833_aM());
            }

            this.field_70170_p.addEntity(entitywitch, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.LIGHTNING); // Paper - Added lightning spawn reason for this entity
            this.func_70106_y();
        }
    }

    public InventoryBasic func_175551_co() {
        return this.field_175560_bz;
    }

    protected void func_175445_a(EntityItem entityitem) {
        ItemStack itemstack = entityitem.func_92059_d();
        Item item = itemstack.func_77973_b();

        if (this.func_175558_a(item)) {
            ItemStack itemstack1 = this.field_175560_bz.func_174894_a(itemstack);

            if (itemstack1.func_190926_b()) {
                entityitem.func_70106_y();
            } else {
                itemstack.func_190920_e(itemstack1.func_190916_E());
            }
        }

    }

    private boolean func_175558_a(Item item) {
        return item == Items.field_151025_P || item == Items.field_151174_bG || item == Items.field_151172_bF || item == Items.field_151015_O || item == Items.field_151014_N || item == Items.field_185164_cV || item == Items.field_185163_cU;
    }

    public boolean func_175553_cp() {
        return this.func_175559_s(1);
    }

    public boolean func_175555_cq() {
        return this.func_175559_s(2);
    }

    public boolean func_175557_cr() {
        boolean flag = this.func_70946_n() == 0;

        return flag ? !this.func_175559_s(5) : !this.func_175559_s(1);
    }

    private boolean func_175559_s(int i) {
        boolean flag = this.func_70946_n() == 0;

        for (int j = 0; j < this.field_175560_bz.func_70302_i_(); ++j) {
            ItemStack itemstack = this.field_175560_bz.func_70301_a(j);

            if (!itemstack.func_190926_b()) {
                if (itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3 * i || itemstack.func_77973_b() == Items.field_151174_bG && itemstack.func_190916_E() >= 12 * i || itemstack.func_77973_b() == Items.field_151172_bF && itemstack.func_190916_E() >= 12 * i || itemstack.func_77973_b() == Items.field_185164_cV && itemstack.func_190916_E() >= 12 * i) {
                    return true;
                }

                if (flag && itemstack.func_77973_b() == Items.field_151015_O && itemstack.func_190916_E() >= 9 * i) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean func_175556_cs() {
        for (int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
            ItemStack itemstack = this.field_175560_bz.func_70301_a(i);

            if (!itemstack.func_190926_b() && (itemstack.func_77973_b() == Items.field_151014_N || itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF || itemstack.func_77973_b() == Items.field_185163_cU)) {
                return true;
            }
        }

        return false;
    }

    public boolean func_174820_d(int i, ItemStack itemstack) {
        if (super.func_174820_d(i, itemstack)) {
            return true;
        } else {
            int j = i - 300;

            if (j >= 0 && j < this.field_175560_bz.func_70302_i_()) {
                this.field_175560_bz.func_70299_a(j, itemstack);
                return true;
            } else {
                return false;
            }
        }
    }

    public EntityAgeable func_90011_a(EntityAgeable entityageable) {
        return this.func_90011_a(entityageable);
    }

    static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {

        public ItemStack field_179411_a;
        public EntityVillager.PriceInfo field_179409_b;
        public ItemStack field_179410_c;
        public EntityVillager.PriceInfo field_179408_d;

        public ItemAndEmeraldToItem(Item item, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange, Item item1, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange1) {
            this.field_179411_a = new ItemStack(item);
            this.field_179409_b = entityvillager_merchantoptionrandomrange;
            this.field_179410_c = new ItemStack(item1);
            this.field_179408_d = entityvillager_merchantoptionrandomrange1;
        }

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            int i = this.field_179409_b.func_179412_a(random);
            int j = this.field_179408_d.func_179412_a(random);

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.field_179411_a.func_77973_b(), i, this.field_179411_a.func_77960_j()), new ItemStack(Items.field_151166_bC), new ItemStack(this.field_179410_c.func_77973_b(), j, this.field_179410_c.func_77960_j())));
        }
    }

    static class h implements EntityVillager.ITradeList {

        public EntityVillager.PriceInfo a;
        public String b;
        public MapDecoration.Type c;

        public h(EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange, String s, MapDecoration.Type mapicon_type) {
            this.a = entityvillager_merchantoptionrandomrange;
            this.b = s;
            this.c = mapicon_type;
        }

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            int i = this.a.func_179412_a(random);
            World world = imerchant.func_190670_t_();
            if (!world.paperConfig.enableTreasureMaps) return; //Paper
            BlockPos blockposition = world.func_190528_a(this.b, imerchant.func_190671_u_(), world.paperConfig.treasureMapsAlreadyDiscovered); // Paper - pass false to return first structure, regardless of if its been discovered. true returns only undiscovered.

            if (blockposition != null) {
                ItemStack itemstack = ItemMap.func_190906_a(world, (double) blockposition.func_177958_n(), (double) blockposition.func_177952_p(), (byte) 2, true, true);

                ItemMap.func_190905_a(world, itemstack);
                MapData.func_191094_a(itemstack, blockposition, "+", this.c);
                itemstack.func_190924_f("filled_map." + this.b.toLowerCase(Locale.ROOT));
                merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.field_151166_bC, i), new ItemStack(Items.field_151111_aL), itemstack));
            }

        }
    }

    static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {

        public ListEnchantedBookForEmeralds() {}

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            Enchantment enchantment = (Enchantment) Enchantment.field_185264_b.func_186801_a(random);
            int i = MathHelper.func_76136_a(random, enchantment.func_77319_d(), enchantment.func_77325_b());
            ItemStack itemstack = ItemEnchantedBook.func_92111_a(new EnchantmentData(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;

            if (enchantment.func_185261_e()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151166_bC, j), itemstack));
        }
    }

    static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {

        public ItemStack field_179407_a;
        public EntityVillager.PriceInfo field_179406_b;

        public ListEnchantedItemForEmeralds(Item item, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange) {
            this.field_179407_a = new ItemStack(item);
            this.field_179406_b = entityvillager_merchantoptionrandomrange;
        }

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.field_179406_b != null) {
                i = this.field_179406_b.func_179412_a(random);
            }

            ItemStack itemstack = new ItemStack(Items.field_151166_bC, i, 0);
            ItemStack itemstack1 = EnchantmentHelper.func_77504_a(random, new ItemStack(this.field_179407_a.func_77973_b(), 1, this.field_179407_a.func_77960_j()), 5 + random.nextInt(15), false);

            merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class ListItemForEmeralds implements EntityVillager.ITradeList {

        public ItemStack field_179403_a;
        public EntityVillager.PriceInfo field_179402_b;

        public ListItemForEmeralds(Item item, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange) {
            this.field_179403_a = new ItemStack(item);
            this.field_179402_b = entityvillager_merchantoptionrandomrange;
        }

        public ListItemForEmeralds(ItemStack itemstack, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange) {
            this.field_179403_a = itemstack;
            this.field_179402_b = entityvillager_merchantoptionrandomrange;
        }

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.field_179402_b != null) {
                i = this.field_179402_b.func_179412_a(random);
            }

            ItemStack itemstack;
            ItemStack itemstack1;

            if (i < 0) {
                itemstack = new ItemStack(Items.field_151166_bC);
                itemstack1 = new ItemStack(this.field_179403_a.func_77973_b(), -i, this.field_179403_a.func_77960_j());
            } else {
                itemstack = new ItemStack(Items.field_151166_bC, i, 0);
                itemstack1 = new ItemStack(this.field_179403_a.func_77973_b(), 1, this.field_179403_a.func_77960_j());
            }

            merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class EmeraldForItems implements EntityVillager.ITradeList {

        public Item field_179405_a;
        public EntityVillager.PriceInfo field_179404_b;

        public EmeraldForItems(Item item, EntityVillager.PriceInfo entityvillager_merchantoptionrandomrange) {
            this.field_179405_a = item;
            this.field_179404_b = entityvillager_merchantoptionrandomrange;
        }

        public void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.field_179404_b != null) {
                i = this.field_179404_b.func_179412_a(random);
            }

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.field_179405_a, i, 0), Items.field_151166_bC));
        }
    }

    interface ITradeList {

        void func_190888_a(IMerchant imerchant, MerchantRecipeList merchantrecipelist, Random random);
    }

    static class PriceInfo extends Tuple<Integer, Integer> {

        public PriceInfo(int i, int j) {
            super(Integer.valueOf(i), Integer.valueOf(j));
            if (j < i) {
                EntityVillager.field_190674_bx.warn("PriceRange({}, {}) invalid, {} smaller than {}", Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(j), Integer.valueOf(i));
            }

        }

        public int func_179412_a(Random random) {
            return ((Integer) this.func_76341_a()).intValue() >= ((Integer) this.func_76340_b()).intValue() ? ((Integer) this.func_76341_a()).intValue() : ((Integer) this.func_76341_a()).intValue() + random.nextInt(((Integer) this.func_76340_b()).intValue() - ((Integer) this.func_76341_a()).intValue() + 1);
        }
    }
}
