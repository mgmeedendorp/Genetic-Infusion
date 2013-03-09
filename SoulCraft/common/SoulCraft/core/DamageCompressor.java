package SoulCraft.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class DamageCompressor extends DamageSource {

	public DamageCompressor(String par1Str) {
		super(par1Str);
	}
	
	@Override
	public String getDeathMessage(EntityPlayer player) {
		return player.username + " was compressed in a compressor.";	
	}
	
}
