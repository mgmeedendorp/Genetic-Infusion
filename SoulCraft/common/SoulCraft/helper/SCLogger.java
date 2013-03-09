package SoulCraft.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class SCLogger {
	
	private static Logger logger = Logger.getLogger("SoulCraft");

	public static void init()
	{
	logger.setParent(FMLLog.getLogger());
	}

	public static void log(Level level, String message)
	{
	logger.log(level, message);
	}
}
