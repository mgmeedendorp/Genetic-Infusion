package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import seremis.geninfusion.api.soul.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TraitAnimation extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(entity.getWorld().isRemote) {
            IAnimationRegistry registry = SoulHelper.animationRegistry();

            List<IAnimation> animations = new ArrayList<IAnimation>(Arrays.asList(registry.getAnimations()));
            List<String> possibleAnimations = new ArrayList<String>();

            for(IAnimation animation : animations) {
                if(animation.canAnimateEntity(entity)) {
                    possibleAnimations.add(registry.getName(animation));
                }
            }

            entity.setStringArray("possibleAnimations", possibleAnimations.toArray(new String[possibleAnimations.size()]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float limbSwing, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        IAnimationRegistry registry = SoulHelper.animationRegistry();

        String[] possibleAnimationsArray = entity.getStringArray("possibleAnimations");

        if(possibleAnimationsArray != null) {
            List<String> possibleAnimations = new ArrayList<String>(Arrays.asList(possibleAnimationsArray));

            String[] activeAnimationArray = entity.getStringArray("activeAnimations");
            List<String> activeAnimations = new ArrayList<String>(Arrays.asList(activeAnimationArray != null ? activeAnimationArray : new String[]{}));

            String[] pendingAnimationArray = entity.getStringArray("pendingAnimations");
            List<String> pendingAnimations = new ArrayList<String>(Arrays.asList(pendingAnimationArray != null ? pendingAnimationArray : new String[]{}));

            for(String name : possibleAnimations) {
                IAnimation animation = registry.getAnimation(name);
                if(activeAnimations.contains(name)) {
                    if(animation.continueAnimation(entity)) {
                        animation.animate(entity, timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale);
                    } else {
                        animation.stopAnimation(entity);
                        activeAnimations.remove(name);
                    }
                } else {
                    if(pendingAnimations.contains(name)) {
                        String sameTypeName = getSameTypeAnimationActive(activeAnimations, name);

                        if(sameTypeName == null) {
                            animation.startAnimation(entity);
                            activeAnimations.add(name);
                        }
                    } else if(animation.shouldStartAnimation(entity)) {
                        String sameTypeName = getSameTypeAnimationActive(activeAnimations, name);
                        IAnimation sameTypeAnimation = sameTypeName != null ? registry.getAnimation(sameTypeName) : null;

                        if(sameTypeName != null && sameTypeAnimation.canBeInterrupted(entity)) {
                            sameTypeAnimation.stopAnimation(entity);
                            activeAnimations.remove(sameTypeName);
                        } else if(sameTypeName != null) {
                            pendingAnimations.add(name);
                        }

                        if(!activeAnimations.contains(sameTypeName)) {
                            animation.startAnimation(entity);
                            activeAnimations.add(name);
                        }
                    }
                }
            }
            entity.setStringArray("activeAnimations", activeAnimations.toArray(new String[activeAnimations.size()]));
            entity.setStringArray("pendingAnimations", pendingAnimations.toArray(new String[pendingAnimations.size()]));
        }
    }

    public String getSameTypeAnimationActive(List<String> activeAnimations, String animation) {
        IAnimationRegistry registry = SoulHelper.animationRegistry();

        if(!registry.getAnimation(animation).getAnimationType().equals(EnumAnimationType.UNDEFINED)) {
            for(String name : activeAnimations) {
                if(registry.getAnimation(name).getAnimationType().equals(registry.getAnimation(animation).getAnimationType())) {
                    return name;
                }
            }
        }
        return null;
    }
}
