package Seremis.SoulCraft.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate2D;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.Line3D;

public class EntityTransporterLogic {
    
    private List<Coordinate3D> turnPoints = new ArrayList<Coordinate3D>();
    private List<Coordinate2D> turnPointRotations = new ArrayList<Coordinate2D>();
    private List<Coordinate3D> turnPointMovements = new ArrayList<Coordinate3D>();
    
    private EntityTransporter entity;
    
    private int currentIndex = 0;
    
    public boolean hasEntity = false;
    private boolean hasChanged = false;
    private boolean update = false;
    
    public EntityTransporterLogic(List<Coordinate3D> turnPoints, List<Coordinate2D> turnPointRotations, List<Coordinate3D> turnPointMovements) {
        this.turnPoints = turnPoints;
        this.turnPointRotations = turnPointRotations;
        this.turnPointMovements = turnPointMovements;
    }
    
    public EntityTransporterLogic() {
        
    }

    public void init(EntityTransporter ent) {
        this.entity = ent;
        update = true;
        hasEntity = true;
    }
    
    public void update() {
        if(entity != null && update) {
            hasChanged = false;

            if(inBlock(turnPoints.get(turnPoints.size()-1))) {
                entity.arrive();
                update = false;
                return;
            }
            
            if(inBlock(turnPoints.get(currentIndex+1))) {
                hasChanged = true;
                currentIndex++;
            }
            
            entity.rotationPitch = (float) turnPointRotations.get(currentIndex).x;
            entity.rotationYaw = (float) turnPointRotations.get(currentIndex).y;
            
            double motionX = turnPointMovements.get(currentIndex).x*0.1;
            double motionY = turnPointMovements.get(currentIndex).y*0.1; motionY = 0;
            double motionZ = turnPointMovements.get(currentIndex).z*0.1;
            
            entity.moveEntity(motionX, motionY, motionZ);
        }
    }
    
    private boolean inBlock(Coordinate3D block) {
        return block.x == Math.floor(entity.posX) && block.z == Math.floor(entity.posZ) && block.y == Math.floor(entity.posY);
    }
    
    @Override
    public String toString() {
        return "EntityTransporterLogic[entity: " + entity +"\n turnPoints: " + turnPoints + "\n turnPointRotations: " + turnPointRotations + "\n turnPointMovements: " + turnPointMovements + "]";
    }
    
    public EntityTransporterLogic refresh() {
        return new EntityTransporterLogic(turnPoints, turnPointRotations, turnPointMovements);
    }
    
    public boolean hasChanged() {
        return hasChanged;
    }
    
    public List<Coordinate3D> getTurnPoints() {
        return this.turnPoints;
    }
    
    public List<Coordinate2D> getTurnPointRotations() {
        return this.turnPointRotations;
    }
    
    public List<Coordinate3D> getTurnPointMovements() {
        return this.turnPointMovements;
    }
    
    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        
        for(Coordinate3D coord : getTurnPoints()) {
            NBTTagCompound compound3 = new NBTTagCompound();
            compound3.setDouble("logicX", coord.x);
            compound3.setDouble("logicY", coord.y);
            compound3.setDouble("logicZ", coord.z);
            list.appendTag(compound3);
        }
        compound.setTag("TurnPoints", list);
        
        NBTTagList list1 = new NBTTagList();
        for(Coordinate2D coord : getTurnPointRotations()) {
            NBTTagCompound compound4 = new NBTTagCompound();
            compound4.setDouble("logicX", coord.x);
            compound4.setDouble("logicY", coord.y);
            list1.appendTag(compound4);
        }
        compound.setTag("TurnPointRotations", list1);
        
        for(Coordinate3D coord : getTurnPointMovements()) {
            NBTTagCompound compound3 = new NBTTagCompound();
            compound3.setDouble("logicX", coord.x);
            compound3.setDouble("logicY", coord.y);
            compound3.setDouble("logicZ", coord.z);
            list.appendTag(compound3);
        }
        compound.setTag("TurnPointMovements", list);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("TurnPoints");
        
        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list.tagAt(i);
            
            turnPoints.add(new Coordinate3D(compound1.getDouble("logicX"), compound.getDouble("logicY"), compound.getDouble("logicZ")));
        }
        
        NBTTagList list1 = compound.getTagList("TurnPointRotations");
        
        for(int i = 0; i < list1.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list1.tagAt(i);
            
            turnPointRotations.add(new Coordinate2D(compound1.getDouble("logicX"), compound1.getDouble("logicY")));
        }
        
        NBTTagList list2 = compound.getTagList("TurnPointMovements");
        
        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list2.tagAt(i);
            
            turnPointMovements.add(new Coordinate3D(compound1.getDouble("logicX"), compound.getDouble("logicY"), compound.getDouble("logicZ")));
        }
    }

    public static EntityTransporterLogic getLogic(IMagnetConnector from, IMagnetConnector to) {
        List<IMagnetConnector> connectors = new ArrayList<IMagnetConnector>();
        connectors.add(from);
        
        List<NumberedObject> objs = new ArrayList<NumberedObject>();
        objs.add(new NumberedObject(from, 0));
        
        int index = 0;
        main:
        while(index < objs.size()) {            
            List<NumberedObject> temp = new ArrayList<NumberedObject>();
            
            List<NumberedObject> copy = new ArrayList<NumberedObject>(objs);
            
            int ind = 0;
            for(MagnetLink link : getLinksConnectedTo(copy)) {
                if(!connectors.contains(link.connector1)) {
                    temp.add(new NumberedObject(link.connector1, objs.get(index).number + link.line.getLength()));
                    connectors.add(link.connector1);
                }
                if(!connectors.contains(link.connector2)) {
                    temp.add(new NumberedObject(link.connector2, objs.get(index).number + link.line.getLength()));
                    connectors.add(link.connector2);
                }
                
                ind++;
                
                if(ind == getLinksConnectedTo(copy).size()) {
                    double shortest = Double.POSITIVE_INFINITY;
                    NumberedObject nearest = null;
                    
                    for(NumberedObject obj : temp) {
                        if(obj.number < shortest) {
                            shortest = obj.number;
                            nearest = obj;
                        }
                        if(obj.obj != null) {
                            if(((IMagnetConnector)obj.obj).getTile().xCoord == to.getTile().xCoord) {
                                if(((IMagnetConnector)obj.obj).getTile().yCoord == to.getTile().yCoord) {
                                    if(((IMagnetConnector)obj.obj).getTile().zCoord == to.getTile().zCoord) {
                                        objs.add(obj);
                                        break main;
                                    }
                                }
                            }
                        }
                    }
                    
                    if(nearest != null) {
                        objs.add(nearest);
                    }
                }
            }
            index++;
        }
        
        List<Coordinate3D> coordList = new ArrayList<Coordinate3D>();
        
        for(NumberedObject obj : objs) {
            TileEntity tile = ((IMagnetConnector)obj.obj).getTile();
            coordList.add(new Coordinate3D(tile));
        }
        
        List<Coordinate2D> rotationList = new ArrayList<Coordinate2D>();
        List<Coordinate3D> movementList = new ArrayList<Coordinate3D>();
        
        Coordinate3D last = null;
        for(NumberedObject obj : objs) {
            if(last != null) {
                Line3D line = new Line3D(last, new Coordinate3D(((IMagnetConnector)obj.obj).getTile()));
                Coordinate2D rotation = new Coordinate2D();
                rotation.x = line.getPitch();
                rotation.y = 180 - line.getYaw();
                rotationList.add(rotation);
                
                Coordinate3D movement = new Coordinate3D();
                movement.x = -Math.sin(Math.toRadians(line.getYaw()));
                movement.y = -Math.cos(Math.toRadians(line.getPitch()));
                movement.z = -Math.cos(Math.toRadians(line.getYaw()));
                movementList.add(movement);
            }
            last = new Coordinate3D(((IMagnetConnector)obj.obj).getTile());
        }
        
        return new EntityTransporterLogic(coordList, rotationList, movementList);
    }
    
    private static List<MagnetLink> getLinksConnectedTo(List<NumberedObject> conns) {
        List<MagnetLink> links = new ArrayList<MagnetLink>();
        
        for(NumberedObject numberedObject : conns) {
            if(numberedObject.obj != null) {
                for(MagnetLink link : MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector) numberedObject.obj)) {
                    if(!links.contains(link)) {
                        links.add(link);
                    }
                }
            }
        }
        return links;
    }
    
    public static class NumberedObject {
        
        public double number;
        public Object obj;
        
        public NumberedObject(Object o, double number) {
            obj = o;
            this.number = number;
        }
        
        public String toString() {
            return "NumberedObject [Object: " + obj + ", number: " + number + "]";
        }
    }
}
