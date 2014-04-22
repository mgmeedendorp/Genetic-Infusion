package seremis.soulcraft.soul.util;

import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class UtilSoulEntity {

    public static void extinguish(IEntitySoulCustom entity) {
        entity.setPersistentVariable("fireTicks", 0);
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
}
