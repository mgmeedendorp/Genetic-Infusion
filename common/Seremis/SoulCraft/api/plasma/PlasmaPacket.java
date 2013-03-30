package Seremis.SoulCraft.api.plasma;


public class PlasmaPacket {
    
    private int amount;
    
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
        if(this.containsEnergy(pack)) {
            this.amount -= pack.getAmount();
            return pack;
        } else {
            return this;
        }
    }
    
    public boolean containsEnergy(PlasmaPacket pack) {
        return this.amount >= pack.getAmount();
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void addAmount(int amountToAdd ) {
        this.amount += amountToAdd;
    }
    
    public PlasmaPacket clone() {
        return new PlasmaPacket(amount);
    }
}
