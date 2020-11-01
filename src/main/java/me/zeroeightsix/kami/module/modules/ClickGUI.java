/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.zeroeightsix.kami.module.modules;

import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.gui.GuiScreen;

@Module.Info(name="clickGUI", description="Opens the Click GUI", category=Module.Category.HIDDEN)
public class ClickGUI
extends Module {
    public ClickGUI() {
        this.getBind().setKey(22);
    }

    @Override
    protected void onEnable() {
        if (!(ClickGUI.mc.currentScreen instanceof DisplayGuiScreen)) {
            mc.displayGuiScreen((GuiScreen)new DisplayGuiScreen(ClickGUI.mc.currentScreen));
        }
        this.disable();
    }
}

