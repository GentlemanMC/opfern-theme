/*
 * Decompiled with CFR 0.150.
 */
package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="PvPInfo", category=Module.Category.HUD)
public class PvPInfo
extends Module {
    private Setting<Enum> mode = this.register(Settings.e("Mode", sex.Longhand));

    public Enum getSex() {
        return this.mode.getValue();
    }

    @Override
    protected void onEnable() {
        this.disable();
    }

    public static enum sex {
        Longhand,
        Shorthand;

    }
}

