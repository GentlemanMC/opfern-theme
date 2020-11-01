/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.HUD.ClickGUI;
import org.lwjgl.opengl.GL11;

public class KamiSettingsPanelUI
extends AbstractComponentUI<SettingsPanel> {
    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        GL11.glLineWidth((float)2.0f);
        ClickGUI colorBack = (ClickGUI)ModuleManager.getModuleByName("GUI");
        GL11.glColor4f((float)colorBack.getSBRed().floatValue(), (float)colorBack.getSBGreen().floatValue(), (float)colorBack.getSBBlue().floatValue(), (float)colorBack.getSBAlpha().floatValue());
        RenderHelper.drawFilledRectangle(-7.0f, 0.0f, component.getParent().getWidth(), component.getHeight() + 2);
        GL11.glColor3f((float)colorBack.getSORed().floatValue(), (float)colorBack.getSOGreen().floatValue(), (float)colorBack.getSOBlue().floatValue());
        GL11.glLineWidth((float)1.5f);
        GL11.glPushMatrix();
        GL11.glTranslated((double)-7.0, (double)0.0, (double)0.0);
        RenderHelper.drawRectangle(0.0f, 0.0f, component.getParent().getWidth(), component.getHeight() + 2);
        GL11.glPopMatrix();
    }
}

