package net.minecraft.item;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class ItemSign extends Item {

    public ItemSign() {
        this.field_77777_bU = 16;
        this.func_77637_a(CreativeTabs.field_78031_c);
    }

    public EnumActionResult func_180614_a(EntityPlayer entityhuman, World world, BlockPos blockposition, EnumHand enumhand, EnumFacing enumdirection, float f, float f1, float f2) {
        IBlockState iblockdata = world.func_180495_p(blockposition);
        boolean flag = iblockdata.func_177230_c().func_176200_f((IBlockAccess) world, blockposition);

        if (enumdirection != EnumFacing.DOWN && (iblockdata.func_185904_a().func_76220_a() || flag) && (!flag || enumdirection == EnumFacing.UP)) {
            blockposition = blockposition.func_177972_a(enumdirection);
            ItemStack itemstack = entityhuman.func_184586_b(enumhand);

            if (entityhuman.func_175151_a(blockposition, enumdirection, itemstack) && Blocks.field_150472_an.func_176196_c(world, blockposition)) {
                if (world.field_72995_K) {
                    return EnumActionResult.SUCCESS;
                } else {
                    blockposition = flag ? blockposition.func_177977_b() : blockposition;
                    if (enumdirection == EnumFacing.UP) {
                        int i = MathHelper.func_76128_c((double) ((entityhuman.field_70177_z + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

                        world.func_180501_a(blockposition, Blocks.field_150472_an.func_176223_P().func_177226_a(BlockStandingSign.field_176413_a, Integer.valueOf(i)), 11);
                    } else {
                        world.func_180501_a(blockposition, Blocks.field_150444_as.func_176223_P().func_177226_a(BlockWallSign.field_176412_a, enumdirection), 11);
                    }

                    TileEntity tileentity = world.func_175625_s(blockposition);

                    if (tileentity instanceof TileEntitySign && !ItemBlock.func_179224_a(world, entityhuman, blockposition, itemstack)) {
                        entityhuman.func_175141_a((TileEntitySign) tileentity);
                    }

                    if (entityhuman instanceof EntityPlayerMP) {
                        CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP) entityhuman, blockposition, itemstack);
                    }

                    itemstack.func_190918_g(1);
                    return EnumActionResult.SUCCESS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }
}
