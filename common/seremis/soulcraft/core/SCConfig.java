package seremis.soulcraft.core;

import net.minecraftforge.common.config.Configuration;

public class SCConfig {

    public static void configure(Configuration config) {
        config.load();
        
        //Empty for now...

        config.save();
    }

}