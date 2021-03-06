package net.minecraft.world.gen;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;


public class FlatLayerInfo {

    private final int field_175902_a;
    private IBlockState field_175901_b;
    private int field_82664_a;
    private int field_82661_d;

    public FlatLayerInfo(int i, Block block) {
        this(3, i, block);
    }

    public FlatLayerInfo(int i, int j, Block block) {
        this.field_82664_a = 1;
        this.field_175902_a = i;
        this.field_82664_a = j;
        this.field_175901_b = block.func_176223_P();
    }

    public FlatLayerInfo(int i, int j, Block block, int k) {
        this(i, j, block);
        this.field_175901_b = block.func_176203_a(k);
    }

    public int func_82657_a() {
        return this.field_82664_a;
    }

    public IBlockState func_175900_c() {
        return this.field_175901_b;
    }

    private Block func_151536_b() {
        return this.field_175901_b.func_177230_c();
    }

    private int func_82658_c() {
        return this.field_175901_b.func_177230_c().func_176201_c(this.field_175901_b);
    }

    public int func_82656_d() {
        return this.field_82661_d;
    }

    public void func_82660_d(int i) {
        this.field_82661_d = i;
    }

    public String toString() {
        String s;

        if (this.field_175902_a >= 3) {
            ResourceLocation minecraftkey = (ResourceLocation) Block.field_149771_c.func_177774_c(this.func_151536_b());

            s = minecraftkey == null ? "null" : minecraftkey.toString();
            if (this.field_82664_a > 1) {
                s = this.field_82664_a + "*" + s;
            }
        } else {
            s = Integer.toString(Block.func_149682_b(this.func_151536_b()));
            if (this.field_82664_a > 1) {
                s = this.field_82664_a + "x" + s;
            }
        }

        int i = this.func_82658_c();

        if (i > 0) {
            s = s + ":" + i;
        }

        return s;
    }
}
