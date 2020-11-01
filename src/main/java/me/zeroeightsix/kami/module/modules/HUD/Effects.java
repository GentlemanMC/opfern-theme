/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.PotionEffect
 */
package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.potion.PotionEffect;

@Module.Info(name="Effects", category=Module.Category.HUD)
public class Effects
extends Module {
    private Setting<Float> x = this.register(Settings.f("X", 300.0f));
    private Setting<Float> y = this.register(Settings.f("Y", 400.0f));
    private float posY;

    @Override
    public void onRender() {
        this.posY = this.y.getValue().floatValue();
        for (PotionEffect p : Wrapper.getPlayer().getActivePotionEffects()) {
            String text = p.getPotion().getName() + " - " + p.getDuration();
            Effects.mc.fontRenderer.drawStringWithShadow(text, this.x.getValue().floatValue(), this.posY, 0xFFFFFF);
            this.posY += (float)Effects.mc.fontRenderer.FONT_HEIGHT;
        }
    }
}

