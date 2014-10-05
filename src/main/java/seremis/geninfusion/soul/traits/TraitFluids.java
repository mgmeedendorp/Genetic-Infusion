package seremis.geninfusion.soul.traits;

import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * @author Seremis
 */
public class TraitFluids extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean drownsInWater = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_DROWNS_IN_WATER)).value;

        if(drownsInWater) {
            //TODO replace all entity.getPersistentInteger("air") with datawatcher thingies
            int air = entity.getPersistentInteger("air");
            boolean inWater = entity.getBoolean("inWater");
            boolean isDead = entity.getPersistentBoolean("isDead");
            if (!isDead && inWater) {
                entity.setPersistentVariable("air", air-1);
                --air;

                if(air == -20) {
                    entity.setPersistentVariable("air", 0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }

        boolean drownsInAir = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_DROWNS_IN_AIR)).value;

        if(drownsInAir) {
            int air = entity.getPersistentInteger("air");
            boolean inWater = entity.getBoolean("inWater");
            boolean isDead = entity.getPersistentBoolean("isDead");
            boolean handleLava = UtilSoulEntity.handleLavaMovement(entity);
            if (!isDead && !inWater && !handleLava) {
                entity.setPersistentVariable("air", air-1);
                --air;

                if(air == -20) {
                    entity.setPersistentVariable("air", 0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
    }
}
