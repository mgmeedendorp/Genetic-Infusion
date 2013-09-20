package Seremis.SoulCraft.misc.bush;

import java.util.HashMap;

public class BushManager {

    public static HashMap<Integer, BushType> types = new HashMap<Integer, BushType>();

    public static BushTypeNormal typeNormal = new BushTypeNormal();
    public static BushTypeBlue typeBlue = new BushTypeBlue();

    public static int lastIndex = 0;

    public static void init() {
        registerType(typeNormal);
        registerType(typeBlue);
    }

    public static void registerType(BushType type) {
        if(!types.containsValue(type)) {
            types.put(lastIndex, type);
            lastIndex++;
        }
    }

    public static int getTypeIndex(BushType type) {
        if(types.containsValue(type)) {
            int index = 0;
            for(BushType typ : types.values()) {
                if(typ == type) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public static BushType getIndexType(int index) {
        return types.get(index);
    }
}
