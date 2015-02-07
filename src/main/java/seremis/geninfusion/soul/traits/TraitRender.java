package seremis.geninfusion.soul.traits;

import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;

public class TraitRender extends Trait {

    @Override
    public void render(IEntitySoulCustom entity) {
        ModelPart[] head = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_HEAD)).value;
        ModelPart[] body = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_BODY)).value;
        ModelPart[] armLeft = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_ARM_LEFT)).value;
        ModelPart[] armRight = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_ARM_RIGHT)).value;
        ModelPart[] legLeft = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_LEG_LEFT)).value;
        ModelPart[] legRight = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_LEG_RIGHT)).value;

        for(ModelPart model : head) {
            model.render(0.0625F);
        }

        body[0].render(0.0625F);

        for(ModelPart model : armLeft) {
            model.render(0.0625F);
        }

        for(ModelPart model : armRight) {
            model.render(0.0625F);
        }

        for(ModelPart model : legLeft) {
            model.render(0.0625F);
        }

        for(ModelPart model : legRight) {
            model.render(0.0625F);
        }
    }
}
