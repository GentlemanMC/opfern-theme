/*
 * Decompiled with CFR 0.150.
 */
package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="ArrayList", category=Module.Category.HUD)
public class ArrayList
extends Module {
    private Setting<Float> Red = this.register(Settings.floatBuilder("Red").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(1.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> Green = this.register(Settings.floatBuilder("Green").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(1.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> Blue = this.register(Settings.floatBuilder("Blue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(1.0f)).withMaximum(Float.valueOf(1.0f)).build());

    public Float getRed() {
        return this.Red.getValue();
    }

    public Float getGreen() {
        return this.Green.getValue();
    }

    public Float getBlue() {
        return this.Blue.getValue();
    }

    @Override
    protected void onEnable() {
        this.disable();
    }
}

