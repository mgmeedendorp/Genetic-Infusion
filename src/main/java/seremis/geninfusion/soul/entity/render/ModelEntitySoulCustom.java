package seremis.geninfusion.soul.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.soul.TraitHandler;

public class ModelEntitySoulCustom extends ModelBase {

    @Override
    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        TraitHandler.render((IEntitySoulCustom) entity);
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        System.out.println("setRotationAngles");
    }
}
