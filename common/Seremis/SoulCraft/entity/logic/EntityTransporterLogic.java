package Seremis.SoulCraft.entity.logic;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.MagnetNetwork;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate2D;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.event.TransporterArriveEvent;
import Seremis.SoulCraft.event.TransporterHitBlockEvent;
import Seremis.SoulCraft.tileentity.TileStationController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTransporterLogic {

    private List<Coordinate3D> turnPoints = new ArrayList<Coordinate3D>();
    private List<Coordinate2D> turnPointRotations = new ArrayList<Coordinate2D>();
    private List<Coordinate3D> turnPointMovements = new ArrayList<Coordinate3D>();

    private EntityTransporter entity;

    private int currentIndex = 0;

    public boolean hasEntity = false;
    private boolean hasChanged = false;
    private boolean update = false;
    private boolean nextClientTurnPoint = false;
    private MagnetLink currentLink;

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

        if(CommonProxy.proxy.isServerWorld(ent.worldObj)) {
            sendLogicToClient();
        }
    }

    @SideOnly(Side.CLIENT)
    public void receiveClientLogic(byte[] data) {
        for(int i = 0; i < data.length / 8 / 8; i++) {

            byte[] next = new byte[8 * 8];

            for(int j = 0; j < 8 * 8; j++) {
                next[j] = data[j + i * 8 * 8];
            }

            Coordinate3D turnPoint = new Coordinate3D();
            Coordinate2D turnPointRotation = new Coordinate2D();
            Coordinate3D turnPointMovement = new Coordinate3D();

            ByteBuffer buffer = ByteBuffer.wrap(next);

            turnPoint.x = buffer.getDouble();
            turnPoint.y = buffer.getDouble();
            turnPoint.z = buffer.getDouble();

            turnPointRotation.x = buffer.getDouble();
            turnPointRotation.y = buffer.getDouble();

            turnPointMovement.x = buffer.getDouble();
            turnPointMovement.y = buffer.getDouble();
            turnPointMovement.z = buffer.getDouble();

            turnPoints.add(turnPoint);
            turnPointRotations.add(turnPointRotation);
            turnPointMovements.add(turnPointMovement);
        }
    }

    public void sendLogicToClient() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(turnPoints.size() * 8 * 8);
            DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            for(int i = 0; i < turnPoints.size() - 1; i++) {
                double[] data = new double[] {turnPoints.get(i).x, turnPoints.get(i).y, turnPoints.get(i).z, turnPointRotations.get(i).x, turnPointRotations.get(i).y, turnPointMovements.get(i).x, turnPointMovements.get(i).y, turnPointMovements.get(i).z};

                doubleBuffer.put(data);
            }
            byte[] bytes = byteBuffer.array();

            entity.sendEntityDataToClient(1, bytes);
        } catch(IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        if(entity != null && update) {
            hasChanged = false;
            
            if(inBlock(turnPoints.get(turnPoints.size() - 1))) {
                entity.arrive();
                update = false;
                MinecraftForge.EVENT_BUS.post(new TransporterArriveEvent(entity, (TileStationController) entity.worldObj.getBlockTileEntity((int) turnPoints.get(turnPoints.size() - 1).x, (int) turnPoints.get(turnPoints.size() - 1).y, (int) turnPoints.get(turnPoints.size() - 1).z)));
                return;
            }

            if(inBlock(turnPoints.get(currentIndex + 1)) && !entity.worldObj.isRemote || nextClientTurnPoint) {
                hasChanged = true;
                currentIndex++;
                double posX = turnPoints.get(currentIndex).x;
                double posY = turnPoints.get(currentIndex).y;
                double posZ = turnPoints.get(currentIndex).z;
                entity.setPosition(posX, posY, posZ);

                entity.sendEntityDataToClient(2, new byte[] {(byte)0});
                
                MinecraftForge.EVENT_BUS.post(new TransporterHitBlockEvent(entity, turnPoints.get(currentIndex)));
                nextClientTurnPoint = false;
            }
            
            if(currentLink == null || hasChanged) {
                int posX = (int) Math.floor(turnPoints.get(currentIndex).x);
                int posY = (int) Math.floor(turnPoints.get(currentIndex).y);
                int posZ = (int) Math.floor(turnPoints.get(currentIndex).z);
                
                int posX1 = (int) Math.floor(turnPoints.get(currentIndex+1).x);
                int posY1 = (int) Math.floor(turnPoints.get(currentIndex+1).y);
                int posZ1 = (int) Math.floor(turnPoints.get(currentIndex+1).z);
                
                TileEntity tile = entity.worldObj.getBlockTileEntity(posX, posY, posZ);
                TileEntity tile1 = entity.worldObj.getBlockTileEntity(posX1, posY1, posZ1);
                
                if(tile != null && tile instanceof IMagnetConnector && tile1 != null && tile1 instanceof IMagnetConnector) {
                    for(MagnetLink link : MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector)tile)) {
                        if(link.getOther((IMagnetConnector)tile) == (IMagnetConnector)tile1) {
                            currentLink = link;
                        }
                    }
                    
                }
            }
            
            if(currentLink != null) {
                float heatCost = 2*entity.getSpeed();

                int heat1 = currentLink.connector1.cool((int) heatCost);
                int heat2 = currentLink.connector2.cool((int) heatCost);
                
                if(heat1 > 0 && heat2 == 0) {
                    int formerTemp = currentLink.connector2.getHeat();
                    if(currentLink.connector2.cool(heat1) == 0) {
                        heat1 = 0;
                    } else {
                        currentLink.connector2.cool(currentLink.connector2.getHeat());
                        currentLink.connector2.warm(formerTemp);
                    }
                }
                
                if(heat1 == 0 && heat2 > 0) {
                    int formerTemp = currentLink.connector1.getHeat();
                    if(currentLink.connector1.cool(heat2) == 0) {
                        heat2 = 0;
                    } else {
                        currentLink.connector1.cool(currentLink.connector1.getHeat());
                        currentLink.connector1.warm(formerTemp);
                    }
                }
                
                int totalHeat = 20 - heat1 - heat2;
                
                entity.setHeat(entity.getHeat() - 20);
                entity.setHeat(entity.getHeat() + totalHeat);
                
                if(entity.getHeat() < 0) {
                    entity.setHeat(0);
                }
                
                int heatModifier = entity.getHeat() + totalHeat <= 0 ? (heat1 + heat2)/400 : 1;
    
                
                entity.rotationPitch = (float) turnPointRotations.get(currentIndex).x;
                entity.rotationYaw = (float) turnPointRotations.get(currentIndex).y;
    
                double motionX = turnPointMovements.get(currentIndex).x * 0.04 * entity.getSpeed() * heatModifier;
                double motionY = turnPointMovements.get(currentIndex).y * 0.04 * entity.getSpeed() * heatModifier;
                double motionZ = turnPointMovements.get(currentIndex).z * 0.04 * entity.getSpeed() * heatModifier;
                
                entity.moveEntity(motionX, motionY, motionZ);
            }
        }
    }
    
    public void nextPoint() {
        nextClientTurnPoint = true;
    }

    private boolean inBlock(Coordinate3D block) {
        return entity.getDistance(block.x, block.y, block.z) < 0.1;
    }

    @Override
    public String toString() {
        return "EntityTransporterLogic[entity: " + entity + 
                "\n turnPoints: " + turnPoints + 
                "\n turnPointRotations: " + turnPointRotations + 
                "\n turnPointMovements: " + turnPointMovements + "]";
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
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setDouble("logicX", coord.x);
            compound1.setDouble("logicY", coord.y);
            compound1.setDouble("logicZ", coord.z);
            list.appendTag(compound1);
        }
        compound.setTag("TurnPoints", list);

        NBTTagList list1 = new NBTTagList();
        for(Coordinate2D coord : getTurnPointRotations()) {
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setDouble("logicX", coord.x);
            compound1.setDouble("logicY", coord.y);
            list1.appendTag(compound1);
        }
        compound.setTag("TurnPointRotations", list1);

        NBTTagList list2 = new NBTTagList();
        for(Coordinate3D coord : getTurnPointMovements()) {
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setDouble("logicX", coord.x);
            compound1.setDouble("logicY", coord.y);
            compound1.setDouble("logicZ", coord.z);
            list2.appendTag(compound1);
        }
        compound.setTag("TurnPointMovements", list2);

        compound.setInteger("currentIndex", currentIndex);
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("TurnPoints");

        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list.tagAt(i);

            turnPoints.add(new Coordinate3D(compound1.getDouble("logicX"), compound1.getDouble("logicY"), compound1.getDouble("logicZ")));
        }

        NBTTagList list1 = compound.getTagList("TurnPointRotations");

        for(int i = 0; i < list1.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list1.tagAt(i);

            turnPointRotations.add(new Coordinate2D(compound1.getDouble("logicX"), compound1.getDouble("logicY")));
        }

        NBTTagList list2 = compound.getTagList("TurnPointMovements");

        for(int i = 0; i < list2.tagCount(); i++) {
            NBTTagCompound compound1 = (NBTTagCompound) list2.tagAt(i);

            turnPointMovements.add(new Coordinate3D(compound1.getDouble("logicX"), compound1.getDouble("logicY"), compound1.getDouble("logicZ")));
        }

        currentIndex = compound.getInteger("currentIndex");
    }

    public static EntityTransporterLogic getLogic(IMagnetConnector from, IMagnetConnector to) {
        // Objects on the shortest path
        List<NumberedObject> objs = new ArrayList<NumberedObject>();

        MagnetNetwork network = MagnetLinkHelper.instance.getNetworkFrom(from);

        // Objects on all the paths
        List<NumberedObject> all = new ArrayList<NumberedObject>();

        for(IMagnetConnector conn : network.getConnectors()) {
            all.add(new NumberedObject(conn, Double.POSITIVE_INFINITY, null));
        }

        PriorityQueue<NumberedObject> queue = new PriorityQueue<NumberedObject>();
        NumberedObject fromObj = get(all, from);
        fromObj.number = 0;
        queue.add(fromObj);

        while(!queue.isEmpty()) {
            NumberedObject obj = queue.poll();

            for(MagnetLink link : MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector) obj.obj)) {
                IMagnetConnector other = link.getOther((IMagnetConnector) obj.obj);

                double distance = obj.number + link.line.getLength();

                NumberedObject newObj = get(all, other);

                if(newObj != null && distance < newObj.number) {
                    queue.remove(newObj);
                    newObj.number = distance;
                    newObj.previous = obj;
                    queue.add(newObj);
                }
            }
        }

        for(NumberedObject obj = get(all, to); obj != null; obj = obj.previous) {
            objs.add(obj);
        }
        Collections.reverse(objs);

        List<Coordinate3D> coordList = new ArrayList<Coordinate3D>();
        List<Coordinate2D> rotationList = new ArrayList<Coordinate2D>();
        List<Coordinate3D> movementList = new ArrayList<Coordinate3D>();

        IMagnetConnector last = null;
        boolean first = true;
        for(NumberedObject obj : objs) {
            if(last != null) {
                IMagnetConnector connector = (IMagnetConnector) obj.obj;
                TileEntity tile = connector.getTile();

                Line3D line = new Line3D(new Coordinate3D(last.getTile()), new Coordinate3D(tile));

                line.head = last.applyBeamRenderOffset(new Coordinate3D(last.getTile()), line.getSide(last.getTile()));
                line.tail = connector.applyBeamRenderOffset(new Coordinate3D(tile), line.getSide(tile));

                if(first) {
                    coordList.add(line.head);
                    first = false;
                }

                coordList.add(line.tail);

                Coordinate2D rotation = new Coordinate2D();
                rotation.x = line.getPitch();
                rotation.y = 180 - line.getYaw();
                rotationList.add(rotation);

                Coordinate3D movement = new Coordinate3D();
                
                double dx = line.head.x - line.tail.x;
                double dy = line.head.y - line.tail.y;
                double dz = line.head.z - line.tail.z;
                
                double pitch = Math.atan2(Math.sqrt(dz * dz + dx * dx), dy) + Math.PI;
                double yaw = Math.atan2(dz, dx);
                
                movement.x = Math.sin(pitch) * Math.cos(yaw);
                movement.y = Math.cos(pitch);
                movement.z = Math.sin(pitch) * Math.sin(yaw);

                movementList.add(movement);
            }
            last = (IMagnetConnector) obj.obj;
        }
        return new EntityTransporterLogic(coordList, rotationList, movementList);
    }

    private static NumberedObject get(List<NumberedObject> list, IMagnetConnector conn) {
        for(NumberedObject obj : list) {
            if(obj.obj == conn) {
                return obj;
            }
        }
        return null;
    }

    public static class NumberedObject implements Comparable<NumberedObject> {
        public double number;
        public Object obj;
        public NumberedObject previous;

        public NumberedObject(Object o, double number, NumberedObject previous) {
            obj = o;
            this.number = number;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof NumberedObject && number == ((NumberedObject) obj).number && this.obj == ((NumberedObject) obj).obj && previous == ((NumberedObject) obj).previous;
        }

        @Override
        public String toString() {
            return "NumberedObject [Object: " + obj + ", number: " + number + "]";
        }

        @Override
        public int compareTo(NumberedObject obj) {
            return Double.compare(number, obj.number);
        }
    }
}
