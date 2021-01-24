package me.ivan.villagerhelper.config;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.glfw.GLFW;

import static me.ivan.villagerhelper.VillagerHelper.MOD_ID;

public interface KeyBindings {
    String KEY_MOD_ID_DOT = "key." + MOD_ID + ".";
    String MOD_ID_CATEGORY = "category." + MOD_ID;

    KeyBinding ENABLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_MOD_ID_DOT + "enable",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            MOD_ID_CATEGORY
    ));

    KeyBinding LIBRARIAN_TRADE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_MOD_ID_DOT + "librarian_trade",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            MOD_ID_CATEGORY
    ));

    KeyBinding JOB_SITE_LINE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_MOD_ID_DOT + "job_site",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            MOD_ID_CATEGORY
    ));

    KeyBinding BED_LINE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_MOD_ID_DOT + "bed",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            MOD_ID_CATEGORY
    ));

    // not using an actual map because we don't look-up, we just iterate over
    Iterable<MutablePair<KeyBinding, Boolean>> KEY_BINDING_CONFIG_MAP = Lists.newArrayList(
            MutablePair.of(ENABLE_KEY, Configs.ENABLE),
            MutablePair.of(LIBRARIAN_TRADE_KEY, Configs.LIBRARIAN_TRADE),
            MutablePair.of(JOB_SITE_LINE_KEY, Configs.JOB_SITE_LINE),
            MutablePair.of(BED_LINE_KEY, Configs.BED_LINE)
    );

    static void init() { }

    static boolean wasKeyJustPressed(KeyBinding keyBinding) {
        return keyBinding.isPressed() && !keyBinding.wasPressed();
    }
}
