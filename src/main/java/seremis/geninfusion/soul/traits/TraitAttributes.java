package seremis.geninfusion.soul.traits;

import net.minecraft.entity.SharedMonsterAttributes;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Trait;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class TraitAttributes extends Trait {

    @Override
    public void onInit(IEntitySoulCustom entity) {
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange);

        float maxHealth = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MAX_HEALTH)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);

        float attackDamage = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_ATTACK_DAMAGE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage);

        float movementSpeed = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MOVEMENT_SPEED)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);

        float knockbackResistance = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_KNOCKBACK_RESISTANCE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance);

        float followRange = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_FOLLOW_RANGE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange);

        entity.getDataWatcher().addObject(6, Float.valueOf(1.0F));
        entity.getDataWatcher().addObject(7, Integer.valueOf(0));
        entity.getDataWatcher().addObject(8, Byte.valueOf((byte)0));
        entity.getDataWatcher().addObject(9, Byte.valueOf((byte)0));
        entity.getDataWatcher().addObject(10, "");
        entity.getDataWatcher().addObject(11, Byte.valueOf((byte)0));
    }

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setVariable("creatureAttribute", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CREATURE_ATTRIBUTE)).value);

        if (!entity.getBoolean("aiEnabled")) {
            entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
        }
    }
}
