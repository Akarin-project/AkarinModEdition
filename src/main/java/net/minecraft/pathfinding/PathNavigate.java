package net.minecraft.pathfinding;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Blocks;
import net.minecraft.server.MCUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate {

    protected EntityLiving field_75515_a; public Entity getEntity() { return field_75515_a; } // Paper - OBFHELPER
    protected World field_75513_b;
    @Nullable
    protected Path field_75514_c;
    protected double field_75511_d;
    private final IAttributeInstance field_75512_e;
    protected int field_75510_g;
    private int field_75520_h;
    private Vec3d field_75521_i;
    private Vec3d field_188557_k;
    private long field_188558_l;
    private long field_188559_m;
    private double field_188560_n;
    protected float field_188561_o;
    protected boolean field_188562_p;
    private long field_188563_q;
    protected NodeProcessor field_179695_a;
    private BlockPos field_188564_r;
    private final PathFinder field_179681_j;

    public PathNavigate(EntityLiving entityinsentient, World world) {
        this.field_75521_i = Vec3d.field_186680_a;
        this.field_188557_k = Vec3d.field_186680_a;
        this.field_188561_o = 0.5F;
        this.field_75515_a = entityinsentient;
        this.field_75513_b = world;
        this.field_75512_e = entityinsentient.func_110148_a(SharedMonsterAttributes.field_111265_b);
        this.field_179681_j = this.func_179679_a();
    }

    protected abstract PathFinder func_179679_a();

    public void func_75489_a(double d0) {
        this.field_75511_d = d0;
    }

    public float func_111269_d() {
        return (float) this.field_75512_e.func_111126_e();
    }

    public boolean func_188553_i() {
        return this.field_188562_p;
    }

    public void func_188554_j() {
        if (this.field_75513_b.func_82737_E() - this.field_188563_q > 20L) {
            if (this.field_188564_r != null) {
                this.field_75514_c = null;
                this.field_75514_c = this.func_179680_a(this.field_188564_r);
                this.field_188563_q = this.field_75513_b.func_82737_E();
                this.field_188562_p = false;
            }
        } else {
            this.field_188562_p = true;
        }

    }

    @Nullable
    public final Path func_75488_a(double d0, double d1, double d2) {
        return this.func_179680_a(new BlockPos(d0, d1, d2));
    }

    @Nullable
    public Path func_179680_a(BlockPos blockposition) {
        if (!getEntity().func_130014_f_().func_175723_af().isInBounds(blockposition)) return null; // Paper - don't path out of world border
        if (!this.func_75485_k()) {
            return null;
        } else if (this.field_75514_c != null && !this.field_75514_c.func_75879_b() && blockposition.equals(this.field_188564_r)) {
            return this.field_75514_c;
        } else {
            if (!new com.destroystokyo.paper.event.entity.EntityPathfindEvent(getEntity().getBukkitEntity(), MCUtil.toLocation(getEntity().field_70170_p, blockposition), null).callEvent()) { return null; } // Paper
            this.field_188564_r = blockposition;
            float f = this.func_111269_d();

            this.field_75513_b.field_72984_F.func_76320_a("pathfind");
            BlockPos blockposition1 = new BlockPos(this.field_75515_a);
            int i = (int) (f + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.field_75513_b, blockposition1.func_177982_a(-i, -i, -i), blockposition1.func_177982_a(i, i, i), 0);
            Path pathentity = this.field_179681_j.func_186336_a(chunkcache, this.field_75515_a, this.field_188564_r, f);

            this.field_75513_b.field_72984_F.func_76319_b();
            return pathentity;
        }
    }

    @Nullable
    public Path func_75494_a(Entity entity) {
        if (!this.func_75485_k()) {
            return null;
        } else {
            BlockPos blockposition = new BlockPos(entity);
            if (!getEntity().func_130014_f_().func_175723_af().isInBounds(blockposition)) return null; // Paper - don't path out of world border

            if (this.field_75514_c != null && !this.field_75514_c.func_75879_b() && blockposition.equals(this.field_188564_r)) {
                return this.field_75514_c;
            } else {
                if (!new com.destroystokyo.paper.event.entity.EntityPathfindEvent(getEntity().getBukkitEntity(), MCUtil.toLocation(entity.field_70170_p, blockposition), entity.getBukkitEntity()).callEvent()) { return null; } // Paper
                this.field_188564_r = blockposition;
                float f = this.func_111269_d();

                this.field_75513_b.field_72984_F.func_76320_a("pathfind");
                BlockPos blockposition1 = (new BlockPos(this.field_75515_a)).func_177984_a();
                int i = (int) (f + 16.0F);
                ChunkCache chunkcache = new ChunkCache(this.field_75513_b, blockposition1.func_177982_a(-i, -i, -i), blockposition1.func_177982_a(i, i, i), 0);
                Path pathentity = this.field_179681_j.func_186333_a(chunkcache, this.field_75515_a, entity, f);

                this.field_75513_b.field_72984_F.func_76319_b();
                return pathentity;
            }
        }
    }

    public boolean func_75492_a(double d0, double d1, double d2, double d3) {
        return this.func_75484_a(this.func_75488_a(d0, d1, d2), d3);
    }

    public boolean func_75497_a(Entity entity, double d0) {
        // Paper start - Pathfinding optimizations
        if (this.pathfindFailures > 10 && this.field_75514_c == null && MinecraftServer.currentTick < this.lastFailure + 40) {
            return false;
        }

        Path pathentity = this.func_75494_a(entity);

        if (pathentity != null && this.func_75484_a(pathentity, d0)) {
            this.lastFailure = 0;
            this.pathfindFailures = 0;
            return true;
        } else {
            this.pathfindFailures++;
            this.lastFailure = MinecraftServer.currentTick;
            return false;
        }
    }
    private int lastFailure = 0;
    private int pathfindFailures = 0;
    // Paper end

    public boolean func_75484_a(@Nullable Path pathentity, double d0) {
        if (pathentity == null) {
            this.field_75514_c = null;
            return false;
        } else {
            if (!pathentity.func_75876_a(this.field_75514_c)) {
                this.field_75514_c = pathentity;
            }

            this.func_75487_m();
            if (this.field_75514_c.func_75874_d() <= 0) {
                return false;
            } else {
                this.field_75511_d = d0;
                Vec3d vec3d = this.func_75502_i();

                this.field_75520_h = this.field_75510_g;
                this.field_75521_i = vec3d;
                return true;
            }
        }
    }

    @Nullable
    public Path func_75505_d() {
        return this.field_75514_c;
    }

    public void func_75501_e() {
        ++this.field_75510_g;
        if (this.field_188562_p) {
            this.func_188554_j();
        }

        if (!this.func_75500_f()) {
            Vec3d vec3d;

            if (this.func_75485_k()) {
                this.func_75508_h();
            } else if (this.field_75514_c != null && this.field_75514_c.func_75873_e() < this.field_75514_c.func_75874_d()) {
                vec3d = this.func_75502_i();
                Vec3d vec3d1 = this.field_75514_c.func_75881_a(this.field_75515_a, this.field_75514_c.func_75873_e());

                if (vec3d.field_72448_b > vec3d1.field_72448_b && !this.field_75515_a.field_70122_E && MathHelper.func_76128_c(vec3d.field_72450_a) == MathHelper.func_76128_c(vec3d1.field_72450_a) && MathHelper.func_76128_c(vec3d.field_72449_c) == MathHelper.func_76128_c(vec3d1.field_72449_c)) {
                    this.field_75514_c.func_75872_c(this.field_75514_c.func_75873_e() + 1);
                }
            }

            this.func_192876_m();
            if (!this.func_75500_f()) {
                vec3d = this.field_75514_c.func_75878_a((Entity) this.field_75515_a);
                BlockPos blockposition = (new BlockPos(vec3d)).func_177977_b();
                AxisAlignedBB axisalignedbb = this.field_75513_b.func_180495_p(blockposition).func_185900_c(this.field_75513_b, blockposition);

                vec3d = vec3d.func_178786_a(0.0D, 1.0D - axisalignedbb.field_72337_e, 0.0D);
                this.field_75515_a.func_70605_aq().func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, this.field_75511_d);
            }
        }
    }

    protected void func_192876_m() {}

    protected void func_75508_h() {
        Vec3d vec3d = this.func_75502_i();
        int i = this.field_75514_c.func_75874_d();

        for (int j = this.field_75514_c.func_75873_e(); j < this.field_75514_c.func_75874_d(); ++j) {
            if ((double) this.field_75514_c.func_75877_a(j).field_75837_b != Math.floor(vec3d.field_72448_b)) {
                i = j;
                break;
            }
        }

        this.field_188561_o = this.field_75515_a.field_70130_N > 0.75F ? this.field_75515_a.field_70130_N / 2.0F : 0.75F - this.field_75515_a.field_70130_N / 2.0F;
        Vec3d vec3d1 = this.field_75514_c.func_186310_f();

        if (MathHelper.func_76135_e((float) (this.field_75515_a.field_70165_t - (vec3d1.field_72450_a + 0.5D))) < this.field_188561_o && MathHelper.func_76135_e((float) (this.field_75515_a.field_70161_v - (vec3d1.field_72449_c + 0.5D))) < this.field_188561_o && Math.abs(this.field_75515_a.field_70163_u - vec3d1.field_72448_b) < 1.0D) {
            this.field_75514_c.func_75872_c(this.field_75514_c.func_75873_e() + 1);
        }

        int k = MathHelper.func_76123_f(this.field_75515_a.field_70130_N);
        int l = MathHelper.func_76123_f(this.field_75515_a.field_70131_O);
        int i1 = k;

        for (int j1 = i - 1; j1 >= this.field_75514_c.func_75873_e(); --j1) {
            if (this.func_75493_a(vec3d, this.field_75514_c.func_75881_a(this.field_75515_a, j1), k, l, i1)) {
                this.field_75514_c.func_75872_c(j1);
                break;
            }
        }

        this.func_179677_a(vec3d);
    }

    protected void func_179677_a(Vec3d vec3d) {
        if (this.field_75510_g - this.field_75520_h > 100) {
            if (vec3d.func_72436_e(this.field_75521_i) < 2.25D) {
                this.func_75499_g();
            }

            this.field_75520_h = this.field_75510_g;
            this.field_75521_i = vec3d;
        }

        if (this.field_75514_c != null && !this.field_75514_c.func_75879_b()) {
            Vec3d vec3d1 = this.field_75514_c.func_186310_f();

            if (vec3d1.equals(this.field_188557_k)) {
                this.field_188558_l += System.currentTimeMillis() - this.field_188559_m;
            } else {
                this.field_188557_k = vec3d1;
                double d0 = vec3d.func_72438_d(this.field_188557_k);

                this.field_188560_n = this.field_75515_a.func_70689_ay() > 0.0F ? d0 / (double) this.field_75515_a.func_70689_ay() * 1000.0D : 0.0D;
            }

            if (this.field_188560_n > 0.0D && (double) this.field_188558_l > this.field_188560_n * 3.0D) {
                this.field_188557_k = Vec3d.field_186680_a;
                this.field_188558_l = 0L;
                this.field_188560_n = 0.0D;
                this.func_75499_g();
            }

            this.field_188559_m = System.currentTimeMillis();
        }

    }

    public boolean func_75500_f() {
        return this.field_75514_c == null || this.field_75514_c.func_75879_b();
    }

    public void func_75499_g() {
        this.pathfindFailures = 0; this.lastFailure = 0; // Paper - Pathfinding optimizations
        this.field_75514_c = null;
    }

    protected abstract Vec3d func_75502_i();

    protected abstract boolean func_75485_k();

    protected boolean func_75506_l() {
        return this.field_75515_a.func_70090_H() || this.field_75515_a.func_180799_ab();
    }

    protected void func_75487_m() {
        if (this.field_75514_c != null) {
            for (int i = 0; i < this.field_75514_c.func_75874_d(); ++i) {
                PathPoint pathpoint = this.field_75514_c.func_75877_a(i);
                PathPoint pathpoint1 = i + 1 < this.field_75514_c.func_75874_d() ? this.field_75514_c.func_75877_a(i + 1) : null;
                IBlockState iblockdata = this.field_75513_b.func_180495_p(new BlockPos(pathpoint.field_75839_a, pathpoint.field_75837_b, pathpoint.field_75838_c));
                Block block = iblockdata.func_177230_c();

                if (block == Blocks.field_150383_bp) {
                    this.field_75514_c.func_186309_a(i, pathpoint.func_186283_a(pathpoint.field_75839_a, pathpoint.field_75837_b + 1, pathpoint.field_75838_c));
                    if (pathpoint1 != null && pathpoint.field_75837_b >= pathpoint1.field_75837_b) {
                        this.field_75514_c.func_186309_a(i + 1, pathpoint1.func_186283_a(pathpoint1.field_75839_a, pathpoint.field_75837_b + 1, pathpoint1.field_75838_c));
                    }
                }
            }

        }
    }

    protected abstract boolean func_75493_a(Vec3d vec3d, Vec3d vec3d1, int i, int j, int k);

    public boolean func_188555_b(BlockPos blockposition) {
        return this.field_75513_b.func_180495_p(blockposition.func_177977_b()).func_185913_b();
    }

    public NodeProcessor func_189566_q() {
        return this.field_179695_a;
    }
}
