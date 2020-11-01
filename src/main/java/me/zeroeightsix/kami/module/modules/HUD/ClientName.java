/*
 * Decompiled with CFR 0.150.
 */
package me.zeroeightsix.kami.module.modules.HUD;

import java.awt.Font;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="ClientName", category=Module.Category.HUD)
public class ClientName
extends Module {
    private Setting<Integer> x = this.register(Settings.i("X", 2));
    private Setting<Integer> y = this.register(Settings.i("Y", 2));
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Calibri", 3, 23), true, false);

    @Override
    public void onRender() {
        this.cFontRenderer.drawStringWithShadow("Opfern \u00a7fV1.3.0Beta", this.x.getValue().intValue(), this.y.getValue().intValue(), 65301);
    }
}

