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
            //TODO replace all entity.getInteger("air") with datawatcher thingies
            int air = entity.getInteger("air");
            boolean inWater = entity.getBoolean("inWater");
            boolean isDead = entity.getBoolean("isDead");
            if(!isDead && inWater) {
                entity.setInteger("air", air - 1);
                --air;

                if(air == -20) {
                    entity.setInteger("air", 0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }

        boolean drownsInAir = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_DROWNS_IN_AIR)).value;

        if(drownsInAir) {
            int air = entity.getInteger("air");
            boolean inWater = entity.getBoolean("inWater");
            boolean isDead = entity.getBoolean("isDead");
            boolean handleLava = UtilSoulEntity.handleLavaMovement(entity);
            if(!isDead && !inWater && !handleLava) {
                entity.setInteger("air", air - 1);
                --air;

                if(air == -20) {
                    entity.setInteger("air", 0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
    }
}
