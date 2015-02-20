package seremis.geninfusion.soul.traits;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class TraitInitialValues extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        BaseAttributeMap attributeMap = ((EntityLiving) entity).getAttributeMap();

        float maxHealth = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MAX_HEALTH)).value;
        attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);

        entity.setFloat("health", maxHealth);

        float attackDamage = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_ATTACK_DAMAGE)).value;
        attributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage);

        double movementSpeed = ((AlleleDouble) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MOVEMENT_SPEED)).value;
        attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);

        float knockbackResistance = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_KNOCKBACK_RESISTANCE)).value;
        attributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance);

        float followRange = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_FOLLOW_RANGE)).value;
        attributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange);

        entity.setInteger("creatureAttribute", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CREATURE_ATTRIBUTE)).value);
        entity.setInteger("experienceValue", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_EXPERIENCE_VALUE)).value);

        entity.setFloatArray("equipmentDropChances", SoulHelper.geneRegistry.getValueFloatArray(entity, Genes.GENE_EQUIPMENT_DROP_CHANCES));

        if(!entity.getBoolean("aiEnabled")) {
            attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
        }
    }
}
