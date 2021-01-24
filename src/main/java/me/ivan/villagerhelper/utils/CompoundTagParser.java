package me.ivan.villagerhelper.utils;

import me.ivan.villagerhelper.VillagerHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CompoundTagParser {
    public static CompoundTag getMemories(CompoundTag tag) {
        if (tag.contains("Brain")) {
            CompoundTag brain = tag.getCompound("Brain");
            if (brain.contains("memories")) {
                return brain.getCompound("memories");
            }
        }
        return null;
    }
    public static BlockPos getHome(CompoundTag tag) {
        CompoundTag memories = getMemories(tag);
        if (memories != null) {
            if (memories.contains("minecraft:home")) {
                CompoundTag home = memories.getCompound("minecraft:home").getCompound("value");
                int[] homePos = home.getIntArray("pos");
                // 1.14 ~ 1.15
                if (homePos.length != 3) {
                    home = memories.getCompound("minecraft:home");
                    homePos = home.getIntArray("pos");
                }
                return new BlockPos(homePos[0], homePos[1], homePos[2]);
            }
        }
        return null;
    }
    public static BlockPos getJobSite(CompoundTag tag) {
        CompoundTag memories = getMemories(tag);
        if (memories != null) {
            if (memories.contains("minecraft:job_site")) {
                CompoundTag jobSite = memories.getCompound("minecraft:job_site").getCompound("value");
                int[] jobSitePos = jobSite.getIntArray("pos");
                // 1.14 ~ 1.15
                if (jobSitePos.length != 3) {
                    jobSite = memories.getCompound("minecraft:job_site");
                    jobSitePos = jobSite.getIntArray("pos");
                }
                return new BlockPos(jobSitePos[0], jobSitePos[1], jobSitePos[2]);
            }
        }
        return null;
    }

    public static Vec3d getPos(CompoundTag tag) {
        if (tag.contains("Pos")) {
            ListTag listTag = tag.getList("Pos", 6);
            return new Vec3d(listTag.getDouble(0), listTag.getDouble(1), listTag.getDouble(2));
        }
        return null;
    }

    public static Pair<String, Integer> getFirstEnchantmentBookTrade(CompoundTag tag) {
        AtomicReference<String> retName = new AtomicReference<>("Unknown");
        int retPrice = -1;

        if (tag.contains("Offers")) {
            ListTag recipes = tag.getCompound("Offers").getList("Recipes", 10);
            int pos = 0;
            AtomicBoolean flag = new AtomicBoolean(false);
            Iterator iterator = recipes.iterator();
            while (iterator.hasNext()) {
                if (flag.get()) break;
                iterator.next();
                CompoundTag recipe = recipes.getCompound(pos);
                CompoundTag buy = recipe.getCompound("buy");
                CompoundTag buyB = recipe.getCompound("buyB");
                CompoundTag sell = recipe.getCompound("sell");
                if (buy.getString("id").equals("minecraft:emerald")) {
                    retPrice = buy.getInt("Count");
                } else if (buyB.getString("id").equals("minecraft:emerald")) {
                    retPrice = buyB.getInt("Count");
                }
                if (sell.getString("id").equals("minecraft:enchanted_book")) {
                    EnchantmentHelper.get(ItemStack.fromTag(sell)).forEach((enchantment, level) -> {
                        retName.set(enchantment.getName(level).getString());
                        flag.set(true);
                    });
                }
                pos ++;
            }
        }
        if (retName.get().equals("Unknown") || retPrice == -1) {
            return null;
        }
        return new Pair<String, Integer>(retName.get(), retPrice);
    }
}
