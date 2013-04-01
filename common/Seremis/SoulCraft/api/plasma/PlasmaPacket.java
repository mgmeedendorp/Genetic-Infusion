package Seremis.SoulCraft.api.plasma;

import net.minecraft.nbt.NBTTagCompound;


public class PlasmaPacket {
    
    public int amount;
    
    public PlasmaPacket(int amount) {
        this.amount = amount;
    }
    
    public PlasmaPacket() {
        this(0);
    }
    
    public PlasmaPacket mergePackets(PlasmaPacket pack) {
        this.amount += pack.getAmount();
        return this;
    }
    
    /**
     * This decreases the amount of this PlasmaPacket with the amount 
     * of plasma in the parameter Packet.
     * @param pack The EnergyPacket to decrease with
     * @return The packet requested, or a packet with the maximum amount of plasma available.
     */
    public PlasmaPacket decreasePacket(PlasmaPacket pack) {
        if(this.amount >= pack.getAmount()) {
            this.amount -= pack.getAmount();
            return pack;
        } else {
            return this;
        }
    }
    
    public PlasmaPacket decreasePacket(int amount) {
        return decreasePacket(new PlasmaPacket(amount));
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void addAmount(int amountToAdd) {
        this.amount += amountToAdd;
    }
    
    public PlasmaPacket clone() {
        return new PlasmaPacket(amount);
    }
    
    public void writeToNBT(NBTTagCompound nbt) {
        if(nbt != null) {
            nbt.setInteger("amount", amount);
        }
    }
    
    public PlasmaPacket readFromNBT(NBTTagCompound nbt) {
        if(nbt != null && nbt.hasKey("amount")) {
            amount = nbt.getInteger("amount");
        }
        return this;
    }
    
    public void empty() {
        amount = 0;
    }
}
