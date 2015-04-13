package seremis.geninfusion.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.lib.Items;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;

import java.util.List;

public class ItemDebugger extends GIItem {

    private String[] subNames = {Items.DEBUGGER_META_0_UNLOCALIZED_NAME(), Items.DEBUGGER_META_1_UNLOCALIZED_NAME(), Items.DEBUGGER_META_2_UNLOCALIZED_NAME(), Items.DEBUGGER_META_3_UNLOCALIZED_NAME()};

    public ItemDebugger() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setNumbersofMetadata(4);
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
                    Entity ent = (Entity) obj;
                    if(ent instanceof IEntitySoulCustom) {
                        ent.setDead();
                        ((EntityLiving) ent).onDeath(DamageSource.causePlayerDamage(player));
                    }
                }
            }
            if(stack.getItemDamage() == 1) {
                EntityLivingBase entity = (EntityLivingBase) SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySkeleton(world)), x + 0.5F, y + 1, z + 0.5F);
                world.spawnEntityInWorld(entity);
            }
            if(stack.getItemDamage() == 2) {
                EntityLivingBase entity = (EntityLivingBase) SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySkeleton(world)), SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world))), x + 0.5F, y + 1, z + 0.5F);
                world.spawnEntityInWorld(entity);
            }
            if(stack.getItemDamage() == 3) {
                List entities = world.getEntitiesWithinAABB(EntitySoulCustomCreature.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 2, y + 2, z + 2));
                for(Object obj : entities) {
                    EntityLiving entity = (EntityLiving) obj;
                    NBTTagCompound nbt = new NBTTagCompound();
                    entity.writeToNBT(nbt);
                    System.out.println(nbt);

                    System.out.println(SoulHelper.geneRegistry.getValueBoolean((IEntitySoulCustom) entity, Genes.GENE_AI_ATTACK_ON_COLLIDE));
                }
            }
        }
        return true;
    }
}
