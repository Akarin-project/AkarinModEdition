package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerShearEntityEvent;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerShearEntityEvent;
// CraftBukkit end

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {

    private static final DataParameter<Byte> field_184749_a = EntityDataManager.func_187226_a(EntitySnowman.class, DataSerializers.field_187191_a);

    public EntitySnowman(World world) {
        super(world);
        this.func_70105_a(0.7F, 1.9F);
    }

    public static void func_189783_b(DataFixer dataconvertermanager) {
        EntityLiving.func_189752_a(dataconvertermanager, EntitySnowman.class);
    }

    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(1, new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
        this.field_70714_bg.func_75776_a(2, new EntityAIWanderAvoidWater(this, 1.0D, 1.0000001E-5F));
        this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
        this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.field_82192_a));
    }

    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
    }

    protected void func_70088_a() {
        super.func_70088_a();
        this.field_70180_af.func_187214_a(EntitySnowman.field_184749_a, Byte.valueOf((byte) 16));
    }

    public void func_70014_b(NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.func_74757_a("Pumpkin", this.func_184748_o());
    }

    public void func_70037_a(NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        if (nbttagcompound.func_74764_b("Pumpkin")) {
            this.func_184747_a(nbttagcompound.func_74767_n("Pumpkin"));
        }

    }

    public void func_70636_d() {
        super.func_70636_d();
        if (!this.field_70170_p.field_72995_K) {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);

            if (this.func_70026_G()) {
                this.func_70097_a(DamageSource.field_76369_e, 1.0F);
            }

            if (this.field_70170_p.func_180494_b(new BlockPos(i, 0, k)).func_180626_a(new BlockPos(i, j, k)) > 1.0F) {
                this.func_70097_a(CraftEventFactory.MELTING, 1.0F); // CraftBukkit - DamageSource.BURN -> CraftEventFactory.MELTING
            }

            if (!this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
                return;
            }

            for (int l = 0; l < 4; ++l) {
                i = MathHelper.func_76128_c(this.field_70165_t + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.func_76128_c(this.field_70163_u);
                k = MathHelper.func_76128_c(this.field_70161_v + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockposition = new BlockPos(i, j, k);

                if (this.field_70170_p.func_180495_p(blockposition).func_185904_a() == Material.field_151579_a && this.field_70170_p.func_180494_b(blockposition).func_180626_a(blockposition) < 0.8F && Blocks.field_150431_aC.func_176196_c(this.field_70170_p, blockposition)) {
                    org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockFormEvent(this.field_70170_p, blockposition, Blocks.field_150431_aC.func_176223_P(), this); // CraftBukkit
                }
            }
        }

    }

    @Nullable
    protected ResourceLocation func_184647_J() {
        return LootTableList.field_186444_z;
    }

    public void func_82196_d(EntityLivingBase entityliving, float f) {
        EntitySnowball entitysnowball = new EntitySnowball(this.field_70170_p, this);
        double d0 = entityliving.field_70163_u + (double) entityliving.func_70047_e() - 1.100000023841858D;
        double d1 = entityliving.field_70165_t - this.field_70165_t;
        double d2 = d0 - entitysnowball.field_70163_u;
        double d3 = entityliving.field_70161_v - this.field_70161_v;
        float f1 = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;

        entitysnowball.func_70186_c(d1, d2 + (double) f1, d3, 1.6F, 12.0F);
        this.func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
        this.field_70170_p.func_72838_d(entitysnowball);
    }

    public float func_70047_e() {
        return 1.7F;
    }

    protected boolean func_184645_a(EntityPlayer entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.func_184586_b(enumhand);

        if (itemstack.func_77973_b() == Items.field_151097_aZ && this.func_184748_o() && !this.field_70170_p.field_72995_K) {
            // CraftBukkit start
            PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player) entityhuman.getBukkitEntity(), this.getBukkitEntity());
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
            // CraftBukkit end

            this.func_184747_a(false);
            itemstack.func_77972_a(1, entityhuman);
        }

        return super.func_184645_a(entityhuman, enumhand);
    }

    public boolean func_184748_o() {
        return (((Byte) this.field_70180_af.func_187225_a(EntitySnowman.field_184749_a)).byteValue() & 16) != 0;
    }

    public void func_184747_a(boolean flag) {
        byte b0 = ((Byte) this.field_70180_af.func_187225_a(EntitySnowman.field_184749_a)).byteValue();

        if (flag) {
            this.field_70180_af.func_187227_b(EntitySnowman.field_184749_a, Byte.valueOf((byte) (b0 | 16)));
        } else {
            this.field_70180_af.func_187227_b(EntitySnowman.field_184749_a, Byte.valueOf((byte) (b0 & -17)));
        }

    }

    @Nullable
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187799_fB;
    }

    @Nullable
    protected SoundEvent func_184601_bQ(DamageSource damagesource) {
        return SoundEvents.field_187803_fD;
    }

    @Nullable
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187801_fC;
    }

    public void func_184724_a(boolean flag) {}
}
