package seremis.geninfusion.core;

import net.minecraftforge.common.config.Configuration;

public class GIConfig {

    public static void configure(Configuration config) {
        config.load();
        
        //Empty for now...

        config.save();
    }

}