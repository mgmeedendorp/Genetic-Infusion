package seremis.geninfusion.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ISoul;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.lib.Items;

public class ItemThermometer extends GIItem {

    private String[] subNames = {Items.THERMOMETER_META_0_UNLOCALIZED_NAME(), Items.THERMOMETER_META_1_UNLOCALIZED_NAME()};

    public ItemThermometer() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setNumbersofMetadata(2);
        setUnlocalizedName(Items.THERMOMETER_UNLOCALIZED_NAME());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(GeneticInfusion.serverProxy().isServerWorld(world)) {
            if(stack.getItemDamage() == 0) {
                for(Object obj : world.getLoadedEntityList()) {
                    Entity ent = (Entity)obj;
                    if(ent instanceof IEntitySoulCustom) {
                        ent.setDead();
                    }
                }
            }
            if(stack.getItemDamage() == 1) {
                EntityLivingBase entity = (EntityLivingBase) SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)), x, y + 1, z);
                world.spawnEntityInWorld(entity);
            }
        }
        return true;
    }
}
