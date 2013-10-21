package Seremis.SoulCraft.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

public class DamageCompressor extends DamageSource {

    public DamageCompressor(String par1Str) {
        super(par1Str);
    }

    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase entity) {
        ChatMessageComponent message = new ChatMessageComponent();
        message.addText(((EntityPlayer) entity).username + " was compressed in an Atomic Compressor.");
        return message;
    }

}
