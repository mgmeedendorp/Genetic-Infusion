package seremis.soulcraft.api.soul.util;

import seremis.soulcraft.api.soul.IEntitySoulCustom;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class UtilSoulEntity {

    public static void extinguish(IEntitySoulCustom entity) {
        entity.setPersistentVariable("fire", 0);
    }
    
    public static ItemStack getCurrentItemOrArmor(IEntitySoulCustom entity, int slot) {
        return entity.getPersistentItemStack("equipment" + slot);
    }
    
    public static void setCurrentItemOrArmor(IEntitySoulCustom entity, int slot, ItemStack stack) {
        entity.setPersistentVariable("equipment" + slot, stack);
    }
    
    public static boolean handleLavaMovement(IEntitySoulCustom entity) {
        return entity.getWorld().isMaterialInBB(entity.getBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
    }
    
    public static boolean isEntityInsideOpaqueBlock(IEntitySoulCustom entity)
    {
    	float width = entity.getFloat("width");
    	double posX = entity.getPersistentDouble("posX");
    	double posY = entity.getPersistentDouble("posY");
    	double posZ = entity.getPersistentDouble("posZ");
    	
        for (int i = 0; i < 8; ++i)
        {
            float f = ((float)((i >> 0) % 2) - 0.5F) * width * 0.8F;
            float f1 = ((float)((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float)((i >> 2) % 2) - 0.5F) * width * 0.8F;
            int x = MathHelper.floor_double(posX + (double)f);
            //int y = MathHelper.floor_double(posY + (double)this.getEyeHeight() + (double)f1);
            int y = MathHelper.floor_double(posY + (double)f1);
            int z = MathHelper.floor_double(posZ + (double)f2);

            if (entity.getWorld().getBlock(x, y, z).isNormalCube()) {
                return true;
            }
        }

        return false;
    }
}
