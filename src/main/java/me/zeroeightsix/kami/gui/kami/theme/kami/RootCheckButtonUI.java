/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import java.awt.Color;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.HUD.ClickGUI;
import org.lwjgl.opengl.GL11;

public class RootCheckButtonUI<T extends CheckButton>
extends AbstractComponentUI<CheckButton> {
    protected Color backgroundColour = new Color(200, 56, 56);
    protected Color backgroundColourHover = new Color(255, 66, 66);
    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);
    protected Color idleColourToggle = new Color(250, 120, 120);
    protected Color downColourToggle = this.idleColourToggle.brighter();

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        int c;
        GL11.glColor4f((float)((float)this.backgroundColour.getRed() / 255.0f), (float)((float)this.backgroundColour.getGreen() / 255.0f), (float)((float)this.backgroundColour.getBlue() / 255.0f), (float)component.getOpacity());
        ClickGUI colorBack = (ClickGUI)ModuleManager.getModuleByName("GUI");
        if (colorBack.getBTMode() == ClickGUI.ButtonMode.HIGHLIGHT) {
            if (component.isToggled()) {
                GL11.glColor4f((float)colorBack.getBTRed().floatValue(), (float)colorBack.getBTGreen().floatValue(), (float)colorBack.getBTBlue().floatValue(), (float)0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, component.getWidth(), component.getHeight() + 2);
                GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
            }
            if (component.isHovered()) {
                GL11.glColor4f((float)0.59f, (float)0.59f, (float)0.59f, (float)0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, component.getWidth(), component.getHeight() + 2);
                GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
            }
            if (component.isPressed()) {
                GL11.glColor4f((float)1.02f, (float)1.01f, (float)1.01f, (float)0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, component.getWidth(), component.getHeight() + 2);
                GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
            }
        }
        String text = component.getName();
        int n = c = component.isToggled() ? 0xFFFFFF : 0xD1D1D1;
        if (colorBack.getBTMode() == ClickGUI.ButtonMode.FONT) {
            int n2 = c = component.isToggled() ? 1769216 : 0xFFFFFF;
            if (component.isHovered()) {
                c = (c & 0x7F7F7F) << 1;
            }
        }
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        KamiGUI.fontRenderer.drawString(2, KamiGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight() + 2);
    }
}

