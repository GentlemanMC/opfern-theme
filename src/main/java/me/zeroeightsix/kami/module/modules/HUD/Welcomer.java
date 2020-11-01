/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.zeroeightsix.kami.module.modules.HUD;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.time.ZonedDateTime;
import java.util.Random;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Wrapper;

@Module.Info(name="Welcomer", category=Module.Category.HUD)
public class Welcomer
extends Module {
    private Setting<Float> x = this.register(Settings.f("X", 400.0f));
    private Setting<Float> y = this.register(Settings.f("Y", 0.0f));
    Random r = new Random();
    ZonedDateTime time = ZonedDateTime.now();
    private int counter = 0;

    @Override
    public void onRender() {
        float xPos = this.x.getValue().floatValue();
        String timer = this.time.getHour() <= 11 ? "Good Morning " : (this.time.getHour() <= 18 && this.time.getHour() > 11 ? "Good Afternoon " : (this.time.getHour() <= 23 && this.time.getHour() > 18 ? "Good Evening " : ""));
        Welcomer.mc.fontRenderer.drawStringWithShadow(timer + (Object)ChatFormatting.WHITE + Wrapper.getPlayer().getDisplayNameString() + (Object)ChatFormatting.RESET + ", Welcome To " + (Object)ChatFormatting.RED + "Opfern", xPos, this.y.getValue().floatValue(), 0xFFFFFF);
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).getRGB();
    }
}

