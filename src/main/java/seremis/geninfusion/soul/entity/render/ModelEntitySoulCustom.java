package seremis.geninfusion.soul.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.soul.TraitHandler;

public class ModelEntitySoulCustom extends ModelBase {

    @Override
    public void render(Entity entity, float timeModifier, float limbSwing, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        TraitHandler.render((IEntitySoulCustom) entity, timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale);
    }
}
