package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;

public interface IStandardSoulRegistry {

    public void register(IStandardSoul standard);

    public IStandardSoul getStandardSoulForEntity(EntityLiving entity);

    public ISoul getSoulForEntity(EntityLiving entity);
}
