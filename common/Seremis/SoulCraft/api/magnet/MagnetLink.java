package Seremis.SoulCraft.api.magnet;

import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.core.geometry.Line3D;

public class MagnetLink {
    
    public IMagnetConnector connector1;
    public IMagnetConnector connector2;
    
    public Line3D line = new Line3D();
    public boolean shouldRender;
    
    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this.connector1 = connector1;
        this.connector2 = connector2;
        if(MagnetLinkHelper.instance.checkConditions(connector1, connector2)) {
            connector1.addLink(this);
            connector2.addLink(this);
        }
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        System.out.println(connector1 + " " + connector2 + " " + line.toString());
    }
    
    public void register() {
        MagnetLinkHelper.instance.addLink(connector2, this);
        MagnetLinkHelper.instance.addLink(connector1, this);
    }
    
    public void writeToNBT(NBTTagCompound compound) {
        line.writeToNBT(compound);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        line.readFromNBT(compound);
    }
    
    public void render() {
        if(shouldRender) {
            GL11.glPushMatrix();
            GL11.glDisable(2896 /* GL_LIGHTING */);
            GL11.glEnable(GL11.GL_3D);
            GL11.glColor3f(0.0f, 1.0f, 0.2f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(line.head.x, line.head.y, line.head.z);
            GL11.glVertex3d(line.tail.x, line.tail.y, line.tail.z);
            GL11.glEnd();
            GL11.glPopMatrix();
        }
    }
}
