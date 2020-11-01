/*
 * Decompiled with CFR 0.150.
 */
package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="GUI", category=Module.Category.HUD)
public class ClickGUI
extends Module {
    private Setting<Float> bRed = this.register(Settings.floatBuilder("BGRed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> bGreen = this.register(Settings.floatBuilder("BGGreen").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> bBlue = this.register(Settings.floatBuilder("BGBlue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> bAlpha = this.register(Settings.floatBuilder("BGAlpha").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.95f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> oRed = this.register(Settings.floatBuilder("OutlineRed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> oGreen = this.register(Settings.floatBuilder("OutlineGreen").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> oBlue = this.register(Settings.floatBuilder("OutlineBlue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> sbRed = this.register(Settings.floatBuilder("SetBGRed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> sbGreen = this.register(Settings.floatBuilder("SetBGGreen").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> sbBlue = this.register(Settings.floatBuilder("SetBGBlue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.15f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> sbAlpha = this.register(Settings.floatBuilder("SetBGAlpha").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.95f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> soRed = this.register(Settings.floatBuilder("SetOutlineRed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> soGreen = this.register(Settings.floatBuilder("SetOutlineGreen").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> soBlue = this.register(Settings.floatBuilder("SetOutlineBlue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> btRed = this.register(Settings.floatBuilder("ButtonRed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.64f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> btGreen = this.register(Settings.floatBuilder("ButtonGreen").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<Float> btBlue = this.register(Settings.floatBuilder("ButtonBlue").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(0.0f)).withMaximum(Float.valueOf(1.0f)).build());
    private Setting<ButtonMode> buttonMode = this.register(Settings.e("ButtonMode", ButtonMode.HIGHLIGHT));
    private Setting<Boolean> blur = this.register(Settings.b("Blur", false));
    private Setting<Boolean> particles = this.register(Settings.b("Particles", false));

    @Override
    protected void onEnable() {
        this.disable();
    }

    public boolean getBlur() {
        return this.blur.getValue();
    }

    public boolean getParticles() {
        return this.particles.getValue();
    }

    public Float getBRed() {
        return this.bRed.getValue();
    }

    public Float getBGreen() {
        return this.bGreen.getValue();
    }

    public Float getBBlue() {
        return this.bBlue.getValue();
    }

    public Float getBAlpha() {
        return this.bAlpha.getValue();
    }

    public Float getORed() {
        return this.oRed.getValue();
    }

    public Float getOGreen() {
        return this.oGreen.getValue();
    }

    public Float getOBlue() {
        return this.oBlue.getValue();
    }

    public Float getSBRed() {
        return this.sbRed.getValue();
    }

    public Float getSBGreen() {
        return this.sbGreen.getValue();
    }

    public Float getSBBlue() {
        return this.sbBlue.getValue();
    }

    public Float getSBAlpha() {
        return this.sbAlpha.getValue();
    }

    public Float getSORed() {
        return this.soRed.getValue();
    }

    public Float getSOGreen() {
        return this.soGreen.getValue();
    }

    public Float getSOBlue() {
        return this.soBlue.getValue();
    }

    public Float getBTRed() {
        return this.btRed.getValue();
    }

    public Float getBTGreen() {
        return this.btGreen.getValue();
    }

    public Float getBTBlue() {
        return this.btBlue.getValue();
    }

    public Enum getBTMode() {
        return this.buttonMode.getValue();
    }

    public static enum ButtonMode {
        FONT,
        HIGHLIGHT;

    }
}

