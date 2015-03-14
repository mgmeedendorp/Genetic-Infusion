package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;

public interface IStandardSoulRegistry {

    public void register(IStandardSoul standard, Class<? extends EntityLiving> entity);

    public IStandardSoul getStandardSoulForEntity(EntityLiving entity);

    public IStandardSoul getStandardSoulForEntity(Class<? extends EntityLiving> entity);

    public ISoul getSoulForEntity(EntityLiving entity);
}
