package Seremis.SoulCraft.handler;

import java.util.EnumSet;

import Seremis.SoulCraft.core.lib.Strings;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
//		for(int i = 0; i>PlasmaRegistry.instance.getNextID(); i++) {
//		    PlasmaRegistry.instance.getNetworkFromID(i).dividePlasma();
//		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return Strings.nameTickHandler;
	}

}
