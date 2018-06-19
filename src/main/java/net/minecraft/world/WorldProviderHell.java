package net.minecraft.world;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.IChunkGenerator;


public class WorldProviderHell extends WorldProvider {

    public WorldProviderHell() {}

    public void func_76572_b() {
        this.field_76578_c = new BiomeProviderSingle(Biomes.field_76778_j);
        this.field_76575_d = true;
        this.field_76576_e = true;
    }

    protected void func_76556_a() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float) i / 15.0F;

            this.field_76573_f[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }

    }

    public IChunkGenerator func_186060_c() {
        return new ChunkGeneratorHell(this.field_76579_a, this.field_76579_a.func_72912_H().func_76089_r(), this.field_76579_a.func_72905_C());
    }

    public boolean func_76569_d() {
        return false;
    }

    public boolean func_76566_a(int i, int j) {
        return false;
    }

    public float func_76563_a(long i, float f) {
        return 0.5F;
    }

    public boolean func_76567_e() {
        return false;
    }

    public WorldBorder func_177501_r() {
        return new WorldBorder() {
            public double func_177731_f() {
                return super.func_177731_f(); // CraftBukkit
            }

            public double func_177721_g() {
                return super.func_177721_g(); // CraftBukkit
            }
        };
    }

    public DimensionType func_186058_p() {
        return DimensionType.NETHER;
    }
}
