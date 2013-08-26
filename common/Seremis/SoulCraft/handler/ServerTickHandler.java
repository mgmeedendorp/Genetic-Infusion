package Seremis.SoulCraft.handler;

import java.util.EnumSet;

import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.core.lib.Strings;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        MagnetLinkHelper.instance.tick();
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return Strings.nameServerTickHandler;
    }

}
