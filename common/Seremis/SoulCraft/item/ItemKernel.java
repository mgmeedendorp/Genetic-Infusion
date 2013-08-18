package Seremis.SoulCraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import Seremis.SoulCraft.block.ModBlocks;

public class ItemKernel extends SCItem implements IPlantable {
    
    private String[] subNames = { "kernel0", "kernel1", "kernel2", "kernel3" };
    
    public ItemKernel(int ID) {
        super(ID);
        setHasSubtypes(true);
        setNumbersofMetadata(2);
        setMaxDamage(0);
        setUnlocalizedName("kernel");
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(side == 1) {
            int blockID = world.getBlockId(x, y, z);
            Block blockClicked = Block.blocksList[blockID];

            if(blockClicked != null && blockClicked.canSustainPlant(world, x, y, z, ForgeDirection.UP, this) && world.isAirBlock(x, y + 1, z)) {
                world.setBlock(x, y+1, z, ModBlocks.bushBerry.blockID,  stack.getItemDamage(), 1);
                stack.stackSize--;
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z) {
        return ModBlocks.bushBerry.blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z) {
        return 0;
    }
}
