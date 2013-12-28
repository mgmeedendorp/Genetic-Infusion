package seremis.soulcraft.entity;

import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.lib.EntityIds;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModEntity {

    public static void init() {
        register();
        addNames();
    }

    public static void register() {
        EntityRegistry.registerModEntity(EntityTransporter.class, "EntityTransporter", EntityIds.transporterID, SoulCraft.instance, 80, 1, false);
    }

    public static void addNames() {
        LanguageRegistry.instance().addStringLocalization("entity.SC.EntityTransporter.name", "en_US", "Plasma Transporter");
    }
}
