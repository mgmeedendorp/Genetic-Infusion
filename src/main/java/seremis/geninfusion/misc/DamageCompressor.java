package seremis.geninfusion.misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import seremis.geninfusion.core.proxy.CommonProxy;

public class DamageCompressor extends DamageSource {

    public DamageCompressor(String par1Str) {
        super(par1Str);
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase entity) {
        return new ChatComponentText(CommonProxy.instance.playerName() + " was compressed in an Atomic Compressor.");
    }

}
