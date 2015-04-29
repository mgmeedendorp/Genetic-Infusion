package seremis.geninfusion.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.lib.Items;

public class ItemDebugger extends GIItem {

    private String[] subNames = {Items.DEBUGGER_META_0_UNLOCALIZED_NAME(), Items.DEBUGGER_META_1_UNLOCALIZED_NAME(), Items.DEBUGGER_META_2_UNLOCALIZED_NAME(), Items.DEBUGGER_META_3_UNLOCALIZED_NAME()};

    public ItemDebugger() {
        super();
        setHasSubtypes(true);
        setMaxDurability(0);
        setNumbersofMetadata(4);
        setUnlocalizedName(Items.THERMOMETER_UNLOCALIZED_NAME());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getMetadata()];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(GeneticInfusion.commonProxy().isServerWorld(world)) {
            if(stack.getMetadata() == 0) {
                for(Object obj : world.getLoadedEntityList()) {
                    Entity ent = (Entity) obj;
                    if(ent instanceof IEntitySoulCustom) {
                        ent.setDead();
                        ((EntityLiving) ent).onDeath(DamageSource.causePlayerDamage(player));
                    }
                }
            }
            if(stack.getMetadata() == 1) {
                try {
                    EntityLivingBase entity = (EntityLivingBase) SoulHelper.instanceHelper().getSoulEntityInstance(world, SoulHelper.standardSoulRegistry().getSoulForEntity(new EntityCreeper(world)), x + 0.5F, y + 1, z + 0.5F);
                    world.spawnEntityInWorld(entity);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            if(stack.getMetadata() == 2) {
                EntityLivingBase entity = (EntityLivingBase) SoulHelper.instanceHelper().getSoulEntityInstance(world, SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry().getSoulForEntity(new EntityCreeper(world)), SoulHelper.standardSoulRegistry().getSoulForEntity(new EntityCreeper(world))), x + 0.5F, y + 1, z + 0.5F);
                world.spawnEntityInWorld(entity);
            }
        }
        if(stack.getMetadata() == 3) {
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
        }
        return true;
    }
}
