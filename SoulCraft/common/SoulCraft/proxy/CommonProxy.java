package SoulCraft.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	@SidedProxy(clientSide = "SoulCraft.proxy.ClientProxy", serverSide = "SoulCraft.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public void registerRendering(){}//client only
		
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public void removeEntity(Entity entity) {
		entity.worldObj.removeEntity(entity);
	}
	
	public boolean isRenderWorld(World world) {
		return world.isRemote;
	}
	
	public boolean isServerWorld(World world) {
		return !world.isRemote;
	}
	
	public String playerName() {
		return "";
	}
}
