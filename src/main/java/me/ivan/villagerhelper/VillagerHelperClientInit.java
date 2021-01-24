package me.ivan.villagerhelper;

import me.ivan.villagerhelper.config.Configs;
import me.ivan.villagerhelper.config.KeyBindings;
import net.fabricmc.api.ClientModInitializer;
import org.apache.commons.lang3.mutable.MutableBoolean;

import static me.ivan.villagerhelper.config.KeyBindings.KEY_BINDING_CONFIG_MAP;
import static net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK;

public class VillagerHelperClientInit implements ClientModInitializer {
	public void onInitializeClient() {
		Configs.readConfigFile();
		KeyBindings.init();
		END_CLIENT_TICK.register(client -> {
			MutableBoolean changed = new MutableBoolean(false);
			KEY_BINDING_CONFIG_MAP.forEach(pair -> {
				if (KeyBindings.wasKeyJustPressed(pair.getLeft())) {
					pair.right = !pair.right;
					System.out.println("toggled: " + pair.left.getTranslationKey() + " " + !pair.right + " -> " + pair.right);
					changed.setTrue();
				}
			});

			if (changed.booleanValue()) Configs.writeConfigFile();
		});
	}

}
