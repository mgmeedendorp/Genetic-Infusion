package seremis.geninfusion.soul.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.soul.TraitHandler;

public class ModelEntitySoulCustom extends ModelBase {

    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        TraitHandler.render((IEntitySoulCustom) entity);
    }

}
