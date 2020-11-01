/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  javax.annotation.Nonnull
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.item.EntityMinecart
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityEgg
 *  net.minecraft.entity.projectile.EntitySnowball
 *  net.minecraft.entity.projectile.EntityWitherSkull
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.text.TextFormatting
 */
package me.zeroeightsix.kami.gui.kami;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.kami.component.Radar;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiTheme;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Scrollpane;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.TickListener;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.component.use.Label;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.HUD.PvPInfo;
import me.zeroeightsix.kami.util.ColourHolder;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.LagCompensator;
import me.zeroeightsix.kami.util.Pair;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class KamiGUI
extends GUI {
    public static final RootFontRenderer fontRenderer = new RootFontRenderer(1.0f);
    public Theme theme = this.getTheme();
    public static ColourHolder primaryColour = new ColourHolder(29, 29, 29);
    private static final int DOCK_OFFSET = 0;

    public KamiGUI() {
        super(new KamiTheme());
    }

    @Override
    public void drawGUI() {
        super.drawGUI();
    }

    @Override
    public void initializeGUI() {
        int y;
        ScaledResolution scaledResolution = new ScaledResolution(Wrapper.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>>();
        for (final Module module : ModuleManager.getModules()) {
            Scrollpane scrollpane;
            if (module.getCategory().isHidden()) continue;
            Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey((Object)moduleCategory)) {
                Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                scrollpane = new Scrollpane(this.getTheme(), stretcherlayout, 300, 260);
                scrollpane.setMaximumHeight(260);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<Scrollpane, SettingsPanel>(scrollpane, new SettingsPanel(this.getTheme(), null)));
            }
            final Pair pair = (Pair)categoryScrollpaneHashMap.get((Object)moduleCategory);
            scrollpane = (Scrollpane)pair.getKey();
            final CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());
            checkButton.addTickListener(() -> {
                checkButton.setToggled(module.isEnabled());
                checkButton.setName(module.getName());
            });
            checkButton.addMouseListener(new MouseListener(){

                @Override
                public void onMouseDown(MouseListener.MouseButtonEvent event) {
                    if (event.getButton() == 1) {
                        if (((SettingsPanel)pair.getValue()).getModule() == module) {
                            ((SettingsPanel)pair.getValue()).kill();
                            ((SettingsPanel)pair.getValue()).setModule(null);
                        } else {
                            ((SettingsPanel)pair.getValue()).setModule(module);
                            ((SettingsPanel)pair.getValue()).setVisible(true);
                            ((SettingsPanel)pair.getValue()).setX(checkButton.getX() + 4);
                            ((SettingsPanel)pair.getValue()).setY(checkButton.getHeight() + checkButton.getY());
                        }
                    }
                }

                @Override
                public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                }

                @Override
                public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                }

                @Override
                public void onMouseMove(MouseListener.MouseMoveEvent event) {
                }

                @Override
                public void onScroll(MouseListener.MouseScrollEvent event) {
                }
            });
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>(){

                @Override
                public void execute(CheckButton component, CheckButton.CheckButtonPoof.CheckButtonPoofInfo info) {
                    if (info.getAction().equals((Object)CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }
        int x = 10;
        int nexty = y = 10;
        for (Map.Entry entry : categoryScrollpaneHashMap.entrySet()) {
            Stretcherlayout stretcherlayout = new Stretcherlayout(1);
            stretcherlayout.COMPONENT_OFFSET_Y = 1;
            Frame frame = new Frame(this.getTheme(), stretcherlayout, ((Module.Category)((Object)entry.getKey())).getName());
            Scrollpane scrollpane = (Scrollpane)((Pair)entry.getValue()).getKey();
            frame.addChild(scrollpane);
            frame.addChild((Component)((Pair)entry.getValue()).getValue());
            scrollpane.setOriginOffsetY(0);
            scrollpane.setOriginOffsetX(0);
            frame.setCloseable(false);
            frame.setX(x);
            frame.setY(y);
            this.addChild(frame);
            nexty = Math.max(y + frame.getHeight() + 10, nexty);
            if (!((float)(x += frame.getWidth() + 10) > (float)Wrapper.getMinecraft().displayWidth / 1.2f)) continue;
            nexty = y = nexty;
        }
        this.addMouseListener(new MouseListener(){

            private boolean isBetween(int min, int val, int max) {
                return val <= max && val >= min;
            }

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                List<SettingsPanel> panels = ContainerHelper.getAllChildren(SettingsPanel.class, KamiGUI.this);
                for (SettingsPanel settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) continue;
                    int[] real = GUI.calculateRealPosition(settingsPanel);
                    int pX = event.getX() - real[0];
                    int pY = event.getY() - real[1];
                    if (this.isBetween(0, pX, settingsPanel.getWidth()) && this.isBetween(0, pY, settingsPanel.getHeight())) continue;
                    settingsPanel.setVisible(false);
                }
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
        ArrayList<Frame> frames = new ArrayList<Frame>();
        Frame frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Active modules");
        frame.setCloseable(false);
        frame.addChild(new ActiveModules());
        frame.setPinneable(true);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Info");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label information = new Label("");
        information.setShadow(true);
        information.addTickListener(() -> {
            information.setText("");
            information.addLine("\u00a7a\u1d0f\u1d18\ua730\u1d07\u0280\u0274\u00a78 1.3.0Beta");
            information.addLine("\u00a7a" + Math.round(LagCompensator.INSTANCE.getTickRate()) + "\u00a78 tps");
            StringBuilder stringBuilder = new StringBuilder().append("\u00a7a");
            Wrapper.getMinecraft();
            information.addLine(stringBuilder.append(Minecraft.debugFPS).append("\u00a78 fps").toString());
        });
        frame.addChild(information);
        information.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Safe");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label safe = new Label("");
        safe.setShadow(false);
        frame.addChild(safe);
        safe.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "PvPInfo");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label pinfo = new Label("");
        pinfo.setShadow(false);
        pinfo.addTickListener(() -> {
            pinfo.setText("");
            PvPInfo pvp = (PvPInfo)ModuleManager.getModuleByName("PvPInfo");
            if (pvp.getSex() == PvPInfo.sex.Longhand) {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    pinfo.addLine("\u00a7aAuto Crystal");
                } else {
                    pinfo.addLine("\u00a74Auto Crystal");
                }
                if (ModuleManager.getModuleByName("OffhandAC").isEnabled()) {
                    pinfo.addLine("\u00a7aOffhandAC");
                } else {
                    pinfo.addLine("\u00a74OffhandAC");
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    pinfo.addLine("\u00a7aSurround");
                } else {
                    pinfo.addLine("\u00a74Surround");
                }
                if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
                    pinfo.addLine("\u00a7aTrap");
                } else {
                    pinfo.addLine("\u00a74Trap");
                }
                if (ModuleManager.getModuleByName("KillAura").isEnabled()) {
                    pinfo.addLine("\u00a7aKillAura");
                } else {
                    pinfo.addLine("\u00a74KillAura");
                }
                if (ModuleManager.getModuleByName("HoleFill").isEnabled()) {
                    pinfo.addLine("\u00a7aHoleFill");
                } else {
                    pinfo.addLine("\u00a74HoleFill");
                }
            }
            if (pvp.getSex() == PvPInfo.sex.Shorthand) {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    pinfo.addLine("\u00a7aAC");
                } else {
                    pinfo.addLine("\u00a74AC");
                }
                if (ModuleManager.getModuleByName("OffhandAC").isEnabled()) {
                    pinfo.addLine("\u00a7aOAC");
                } else {
                    pinfo.addLine("\u00a74OAC");
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    pinfo.addLine("\u00a7aSU");
                } else {
                    pinfo.addLine("\u00a74SU");
                }
                if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
                    pinfo.addLine("\u00a7aAT");
                } else {
                    pinfo.addLine("\u00a74AT");
                }
                if (ModuleManager.getModuleByName("KillAura").isEnabled()) {
                    pinfo.addLine("\u00a7aKA");
                } else {
                    pinfo.addLine("\u00a74KA");
                }
                if (ModuleManager.getModuleByName("HoleFill").isEnabled()) {
                    pinfo.addLine("\u00a7aHF");
                } else {
                    pinfo.addLine("\u00a74HF");
                }
            }
        });
        frame.addChild(pinfo);
        pinfo.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Crystals");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label crystals = new Label("");
        crystals.setShadow(false);
        crystals.addTickListener(() -> {
            crystals.setText("");
            int total = 0;
            for (int i = 0; i < 45; ++i) {
                ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemEndCrystal)) continue;
                total += stack.stackSize;
            }
            crystals.addLine("\u00a7fCrystals: \u00a7a" + Integer.toString(total));
        });
        frame.addChild(crystals);
        crystals.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Totems");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label totems = new Label("");
        totems.setShadow(false);
        totems.addTickListener(() -> {
            totems.setText("");
            int total = 0;
            for (int i = 0; i < 45; ++i) {
                ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || stack.getItem() != Items.TOTEM_OF_UNDYING) continue;
                total += stack.stackSize;
            }
            totems.addLine("\u00a7fTotems: \u00a7a" + Integer.toString(total));
        });
        frame.addChild(totems);
        totems.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Gapples");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label gapples = new Label("");
        gapples.setShadow(false);
        gapples.addTickListener(() -> {
            gapples.setText("");
            int total = 0;
            for (int i = 0; i < 45; ++i) {
                ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || stack.getItem() != Items.GOLDEN_APPLE) continue;
                total += stack.stackSize;
            }
            gapples.addLine("\u00a7fGapples: \u00a7a" + Integer.toString(total));
        });
        frame.addChild(gapples);
        gapples.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Obsidian");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label obsidian = new Label("");
        obsidian.setShadow(false);
        obsidian.addTickListener(() -> {
            obsidian.setText("");
            int total = 0;
            for (int i = 0; i < 45; ++i) {
                ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || stack.getItem() != Item.getItemFromBlock((Block)Blocks.OBSIDIAN)) continue;
                total += stack.stackSize;
            }
            obsidian.addLine("\u00a7fObsidian: \u00a7a" + Integer.toString(total));
        });
        frame.addChild(obsidian);
        obsidian.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "PlayerInfo");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label plinfo = new Label("");
        plinfo.setShadow(false);
        plinfo.addTickListener(() -> {
            plinfo.setText("");
            plinfo.addLine("\u00a7fHealth: \u00a7b" + (int)Wrapper.getPlayer().getHealth());
            plinfo.addLine("\u00a7fHunger: \u00a7b" + Wrapper.getPlayer().getFoodStats().getFoodLevel());
            plinfo.addLine("\u00a7fSaturation: \u00a7b" + (int)Wrapper.getPlayer().getFoodStats().getSaturationLevel());
        });
        frame.addChild(plinfo);
        plinfo.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Text Radar");
        Label list = new Label("");
        DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder healthSB = new StringBuilder();
        list.addTickListener(() -> {
            if (!list.isVisible()) {
                return;
            }
            list.setText("");
            Minecraft mc = Wrapper.getMinecraft();
            if (mc.player == null) {
                return;
            }
            List entityList = mc.world.playerEntities;
            Map<String, Integer> players = new HashMap();
            int playerStep = 0;
            /*for (entity : entityList) {
                if (playerStep >= 7) break;
                if (entity.getName().equals(mc.player.getName())) continue;
                float hpRaw = ((EntityLivingBase)entity).getHealth() + ((EntityLivingBase)entity).getAbsorptionAmount();
                String hp = dfHealth.format(hpRaw);
                healthSB.append(Command.SECTIONSIGN());
                if (hpRaw >= 20.0f) {
                    healthSB.append("a");
                } else if (hpRaw >= 10.0f) {
                    healthSB.append("e");
                } else if (hpRaw >= 5.0f) {
                    healthSB.append("6");
                } else {
                    healthSB.append("c");
                }
                healthSB.append(hp);
                int responseTime = entity.getUniqueID() == null ? 0 : (mc.player.connection.getPlayerInfo(entity.getUniqueID()) == null ? 0 : mc.player.connection.getPlayerInfo(entity.getUniqueID()).getResponseTime());
                boolean friend = Friends.isFriend(entity.getName());
                int ping = responseTime;
                players.put((friend ? "\u00a7b" : "\u00a7f") + entity.getName() + " " + healthSB.toString() + (Object)ChatFormatting.WHITE + " " + ping + "ms", (int)mc.player.getDistance(entity));
                healthSB.setLength(0);
                ++playerStep;
            }*/
            if (players.isEmpty()) {
                list.setText("");
                return;
            }
            players = KamiGUI.sortByValue(players);
            for (Map.Entry entry : players.entrySet()) {
                list.addLine(Command.SECTIONSIGN() + "7" + (String)entry.getKey() + " " + Command.SECTIONSIGN() + "4" + entry.getValue());
            }
        });
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.setMinimumWidth(75);
        list.setShadow(true);
        frame.addChild(list);
        list.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Entities");
        final Label entityLabel = new Label("");
        frame.setCloseable(false);
        entityLabel.addTickListener(new TickListener(){
            Minecraft mc = Wrapper.getMinecraft();

            @Override
            public void onTick() {
                if (mc.player == null || !entityLabel.isVisible()) return;

                final List<Entity> entityList = new ArrayList<>(mc.world.loadedEntityList);
                if (entityList.size() <= 1) {
                    entityLabel.setText("");
                    return;
                }
                final Map<String, Integer> entityCounts = entityList.stream()
                        .filter(Objects::nonNull)
                        .filter(e -> !(e instanceof EntityPlayer))
                        .collect(Collectors.groupingBy(KamiGUI::getEntityName,
                                Collectors.reducing(0, ent -> {
                                    if (ent instanceof EntityItem)
                                        return ((EntityItem)ent).getItem().getCount();
                                    return 1;
                                }, Integer::sum)
                        ));
                entityLabel.setText("");
                entityCounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(entry -> (Object)TextFormatting.GRAY + (String)entry.getKey() + " " + (Object)TextFormatting.DARK_GRAY + "x" + entry.getValue()).forEach(entityLabel::addLine);
            }
        });
        frame.addChild(entityLabel);
        frame.setPinneable(true);
        entityLabel.setShadow(true);
        entityLabel.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Coordinates");
        frame.setCloseable(false);
        frame.setPinneable(true);
        final Label coordsLabel = new Label("");
        coordsLabel.addTickListener(new TickListener(){
            Minecraft mc = Minecraft.getMinecraft();

            @Override
            public void onTick() {
                boolean inHell = this.mc.world.getBiome(this.mc.player.getPosition()).getBiomeName().equals("Hell");
                int posX = (int)this.mc.player.posX;
                int posY = (int)this.mc.player.posY;
                int posZ = (int)this.mc.player.posZ;
                float f = !inHell ? 0.125f : 8.0f;
                int hposX = (int)(this.mc.player.posX * (double)f);
                int hposZ = (int)(this.mc.player.posZ * (double)f);
                coordsLabel.setText(String.format(" %sf%,d%s7, %sf%,d%s7, %sf%,d %s7(%sf%,d%s7, %sf%,d%s7, %sf%,d%s7)", Character.valueOf(Command.SECTIONSIGN()), posX, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posY, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posZ, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), hposX, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posY, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), hposZ, Character.valueOf(Command.SECTIONSIGN())));
            }
        });
        frame.addChild(coordsLabel);
        coordsLabel.setFontRenderer(fontRenderer);
        coordsLabel.setShadow(true);
        frame.setHeight(20);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Radar");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        frame.addChild(new Radar());
        frame.setWidth(100);
        frame.setHeight(100);
        frames.add(frame);
        for (Frame frame1 : frames) {
            frame1.setX(x);
            frame1.setY(y);
            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            x += frame1.getWidth() + 10;
            if ((float)(x * DisplayGuiScreen.getScale()) > (float)Wrapper.getMinecraft().displayWidth / 1.2f) {
                nexty = y = nexty;
                x = 10;
            }
            this.addChild(frame1);
        }
    }

    private static String getEntityName(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return (Object)TextFormatting.DARK_AQUA + ((EntityItem)entity).getItem().getItem().getItemStackDisplayName(((EntityItem)entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return (Object)TextFormatting.DARK_GRAY + "Wither skull";
        }
        if (entity instanceof EntityEnderCrystal) {
            return (Object)TextFormatting.LIGHT_PURPLE + "End crystal";
        }
        if (entity instanceof EntityEnderPearl) {
            return "Thrown ender pearl";
        }
        if (entity instanceof EntityMinecart) {
            return "Minecart";
        }
        if (entity instanceof EntityItemFrame) {
            return "Item frame";
        }
        if (entity instanceof EntityEgg) {
            return "Thrown egg";
        }
        if (entity instanceof EntitySnowball) {
            return "Thrown snowball";
        }
        return entity.getName();
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, Comparator.comparing(o -> (Comparable)o.getValue()));
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void destroyGUI() {
        this.kill();
    }

    public static void dock(Frame component) {
        Docking docking = component.getDocking();
        if (docking.isTop()) {
            component.setY(0);
        }
        if (docking.isBottom()) {
            component.setY(Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale() - component.getHeight() - 0);
        }
        if (docking.isLeft()) {
            component.setX(0);
        }
        if (docking.isRight()) {
            component.setX(Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale() - component.getWidth() - 0);
        }
        if (docking.isCenterHorizontal()) {
            component.setX(Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2);
        }
        if (docking.isCenterVertical()) {
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);
        }
    }
}

