package seremis.soulcraft.soul.actions;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleIntArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import seremis.soulcraft.util.inventory.Inventory;

public class ChromosomeItemDrops extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {}

    @Override
    public void onUpdate(IEntitySoulCustom entity) {}

    @Override
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player) {}

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
            if (entity.getRecentlyHit() > 0) {
                int chance = new Random().nextInt(200) - lootingLevel;

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

        for (int l = 0; l < chance; ++l) {
            Inventory drops = ((AlleleInventory)chromosome.getActive()).inventory;
            for(int i = 0; i < drops.getSizeInventory(); i++) {
                entity.dropItems(drops.getStackInSlot(i));
            }
        }
    }
    
    public void dropRareItems(IEntitySoulCustom entity, int lootingLevel) {
        int modifier = ((AlleleInteger)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.RARE_ITEM_DROP_CHANCE).getActive()).value;
        
        IChromosome chromosome = SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.RARE_ITEM_DROPS);
        
        if(new Random().nextInt(200-lootingLevel) < 5) {
            if(new Random().nextInt(modifier) == 0) {
                Inventory drops = ((AlleleInventory)chromosome.getActive()).inventory;
                for(int i = 0; i < drops.getSizeInventory(); i++) {
                    entity.dropItems(drops.getStackInSlot(i));
                }
            }
        }
    }
    
    public void dropEquipment(IEntitySoulCustom entity, int lootingLevel) {
        Random rand = new Random();

        int[] equipmentDropChances = ((AlleleIntArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.EQUIPMENT_DROP_CHANCES).getActive()).value;

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

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {}

    @Override
    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {
        return true;
    }

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, EntityLivingData data) {}

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {}
}
