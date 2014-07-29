package seremis.geninfusion.soul.traits;

import net.minecraft.entity.SharedMonsterAttributes;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.TraitDependencies;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Trait;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class TraitInitialValues extends Trait {

    @Override
    @TraitDependencies(dependencies = "first")
    public void firstTick(IEntitySoulCustom entity) {
        //TODO attributes have to be registered in the attributes method in the entity specifically..
        if(entity.getWorld().isRemote)
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        entity.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange);

        float maxHealth = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MAX_HEALTH)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);

        entity.setPersistentVariable("health", maxHealth);

        float attackDamage = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_ATTACK_DAMAGE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage);

        float movementSpeed = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MOVEMENT_SPEED)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);

        float knockbackResistance = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_KNOCKBACK_RESISTANCE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance);

        float followRange = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_FOLLOW_RANGE)).value;
        entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange);

        entity.setVariable("creatureAttribute", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CREATURE_ATTRIBUTE)).value);
        entity.setVariable("experienceValue", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_EXPERIENCE_VALUE)).value);

        if (!entity.getBoolean("aiEnabled")) {
            entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
        }
    }
}
