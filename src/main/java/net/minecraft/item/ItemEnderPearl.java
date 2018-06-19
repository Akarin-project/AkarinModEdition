package net.minecraft.item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;


public class ItemEnderPearl extends Item {

    public ItemEnderPearl() {
        this.field_77777_bU = 16;
        this.func_77637_a(CreativeTabs.field_78026_f);
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.func_184586_b(enumhand);

        // CraftBukkit start - change order
        if (!world.field_72995_K) {
            EntityEnderPearl entityenderpearl = new EntityEnderPearl(world, entityhuman);

            entityenderpearl.func_184538_a(entityhuman, entityhuman.field_70125_A, entityhuman.field_70177_z, 0.0F, 1.5F, 1.0F);
            if (!world.func_72838_d(entityenderpearl)) {
                if (entityhuman instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) entityhuman).getBukkitEntity().updateInventory();
                }
                return new ActionResult(EnumActionResult.FAIL, itemstack);
            }
        }

        if (!entityhuman.field_71075_bZ.field_75098_d) {
            itemstack.func_190918_g(1);
        }

        world.func_184148_a((EntityPlayer) null, entityhuman.field_70165_t, entityhuman.field_70163_u, entityhuman.field_70161_v, SoundEvents.field_187595_bc, SoundCategory.NEUTRAL, 0.5F, 0.4F / (ItemEnderPearl.field_77697_d.nextFloat() * 0.4F + 0.8F));
        entityhuman.func_184811_cZ().func_185145_a(this, 20);
        // CraftBukkit end

        entityhuman.func_71029_a(StatList.func_188057_b((Item) this));
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
}
