package net.minecraft.entity.ai;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class EntityAIMoveTowardsRestriction extends EntityAIBase {

    private final EntityCreature field_75436_a;
    private double field_75434_b;
    private double field_75435_c;
    private double field_75432_d;
    private final double field_75433_e;

    public EntityAIMoveTowardsRestriction(EntityCreature entitycreature, double d0) {
        this.field_75436_a = entitycreature;
        this.field_75433_e = d0;
        this.func_75248_a(1);
    }

    public boolean func_75250_a() {
        if (this.field_75436_a.func_110173_bK()) {
            return false;
        } else {
            BlockPos blockposition = this.field_75436_a.func_180486_cf();
            Vec3d vec3d = RandomPositionGenerator.func_75464_a(this.field_75436_a, 16, 7, new Vec3d((double) blockposition.func_177958_n(), (double) blockposition.func_177956_o(), (double) blockposition.func_177952_p()));

            if (vec3d == null) {
                return false;
            } else {
                this.field_75434_b = vec3d.field_72450_a;
                this.field_75435_c = vec3d.field_72448_b;
                this.field_75432_d = vec3d.field_72449_c;
                return true;
            }
        }
    }

    public boolean func_75253_b() {
        return !this.field_75436_a.func_70661_as().func_75500_f();
    }

    public void func_75249_e() {
        this.field_75436_a.func_70661_as().func_75492_a(this.field_75434_b, this.field_75435_c, this.field_75432_d, this.field_75433_e);
    }
}
