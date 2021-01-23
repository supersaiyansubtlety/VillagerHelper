package me.ivan.villagerhelper.utils;

import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DimensionConvert {
    private final static Map<String, Integer> dimConvert = new HashMap<String, Integer>(){{
        put("minecraft:overworld", 0);
        put("minecraft:the_nether", -1);
        put("minecraft:the_end", 1);
    }};

    public static String getName(int id) {
        Iterator iterator = dimConvert.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getValue().equals(id)) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public static int getId(String name) {
        return dimConvert.get(name);
    }

    public static int getId(World world) {
        if (world.getRegistryKey() == World.OVERWORLD) return 0;
        if (world.getRegistryKey() == World.NETHER) return -1;
        if (world.getRegistryKey() == World.END) return 1;
        return 0;
    }
}
