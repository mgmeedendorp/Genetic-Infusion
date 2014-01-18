package seremis.soulcraft.soul;

import seremis.soulcraft.core.lib.AlleleNames;
import seremis.soulcraft.soul.actions.ActionBurnsInDaylight;

public class ModAlleleActions {
    
    public static void init() {
        AlleleRegistry.registry.addAllele(AlleleNames.BURNS_IN_DAYLIGHT, new ActionBurnsInDaylight());
    }
}
