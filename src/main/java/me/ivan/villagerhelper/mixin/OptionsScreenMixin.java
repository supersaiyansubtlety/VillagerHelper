package me.ivan.villagerhelper.mixin;

import me.ivan.villagerhelper.VillagerHelper;
import me.ivan.villagerhelper.gui.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    private OptionsScreenMixin() {
        super(null);
    }

    private ButtonWidget villagerHelperToggleButton;

    @Inject(at = @At("RETURN"), method = "init")
    private void drawMenuButton(CallbackInfo ci) {
        this.villagerHelperToggleButton = this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, new TranslatableText("villagerhelper.gui.config_button"), (button -> {
            MinecraftClient.getInstance().openScreen(new ConfigScreen(this));
        })));
    }

}
