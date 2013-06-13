package Seremis.SoulCraft.entity;

import Seremis.SoulCraft.mod_SoulCraft;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModEntity {
    
    public static int transporterID = EntityRegistry.findGlobalUniqueEntityId();
    
    public static void init() {
        register();
        addNames();
    }
    
    public static void register() {
        EntityRegistry.registerModEntity(EntityTransporter.class, "EntityTransporter", transporterID, mod_SoulCraft.instance, 40, 1, true);
    }
    
    public static void addNames() {
        LanguageRegistry.instance().addStringLocalization("entity.SC.EntityTransporter.name", "en_US", "Plasma Transporter");
    }

}
