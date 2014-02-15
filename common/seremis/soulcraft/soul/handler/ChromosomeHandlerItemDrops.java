package seremis.soulcraft.soul.handler;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import seremis.soulcraft.util.inventory.Inventory;

public class ChromosomeHandlerItemDrops extends EntityEventHandler {

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        if(CommonProxy.proxy.isRenderWorld(entity.getWorld())) return;
        
        int lootingLevel = 0;
        
        if (entity instanceof EntityPlayer) {
            lootingLevel = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
        }

        if (entity.getWorld().getGameRules().getGameRuleBooleanValue("doMobLoot")) {
            dropNormalItems(entity, lootingLevel);
            dropEquipment(entity, lootingLevel);
            entity.setRecentlyHit(100);
            if (entity.getRecentlyHit() > 0) {
                int chance = new Random().nextInt(200) - lootingLevel;
                System.out.println(chance);
                if (chance < 5) {
                    dropRareItems(entity, lootingLevel);
                }
            }
        }
    }
    
    private void dropNormalItems(IEntitySoulCustom entity, int lootingLevel) {
        Random rand = new Random();
        
        int chance = rand.nextInt(3);

        if (lootingLevel > 0) {
            chance += rand.nextInt(lootingLevel + 1);
        }
        
        IChromosome chromosome = SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.ITEM_DROPS);
        Inventory drops = ((AlleleInventory)chromosome.getActive()).inventory;

        for (int l = 0; l < chance; ++l) {
            for(int i = 0; i < drops.getSizeInventory(); i++) {
                entity.dropItems(drops.getStackInSlot(i));
            }
        }
    }
    
    public void dropRareItems(IEntitySoulCustom entity, int lootingLevel) {        
        IChromosome chromosome = SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.RARE_ITEM_DROPS);
        
        Inventory drops = ((AlleleInventory)chromosome.getActive()).inventory;
            
        entity.dropItems(drops.getStackInSlot(new Random().nextInt(drops.getSizeInventory())));
    }
    
    public void dropEquipment(IEntitySoulCustom entity, int lootingLevel) {
        Random rand = new Random();

        float[] equipmentDropChances = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.EQUIPMENT_DROP_CHANCES).getActive()).value;

        boolean recentlyHit = entity.getRecentlyHit() < 1;
        
        for (int i = 0; i < 5; ++i) {
            ItemStack itemstack = entity.getCurrentItemOrArmor(i);
            boolean flag = equipmentDropChances[i] > 1.0F;
            
            if (itemstack != null && (recentlyHit || flag) && rand.nextFloat() - (float)lootingLevel * 0.01F < equipmentDropChances[i]) {
                
                if (!flag && itemstack.isItemStackDamageable()) {
                    int chance = Math.max(itemstack.getMaxDamage() - 25, 1);
                    int itemdamage = itemstack.getMaxDamage() - rand.nextInt(rand.nextInt(chance) + 1);

                    if (itemdamage > chance)
                    {
                        itemdamage = chance;
                    }

                    if (itemdamage < 1)
                    {
                        itemdamage = 1;
                    }

                    itemstack.setItemDamage(itemdamage);
                }

                entity.dropItems(itemstack);
            }
        }
    }
}
