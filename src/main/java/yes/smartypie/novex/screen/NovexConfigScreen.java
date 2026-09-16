package yes.smartypie.novex.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import yes.smartypie.novex.NovexClient;

public final class NovexConfigScreen extends Screen {
    private final Screen parent;

    public NovexConfigScreen(Screen parent) {
        super(Component.literal("Novex Client"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 110;
        int y = 45;
        int gap = 25;

        addRenderableWidget(toggle("Shield Optimizer", () -> NovexClient.CONFIG.shieldOptimizer = !NovexClient.CONFIG.shieldOptimizer, x, y)); y += gap;
        addRenderableWidget(toggle("Fire Overlay", () -> NovexClient.CONFIG.fireOverlay = !NovexClient.CONFIG.fireOverlay, x, y)); y += gap;
        addRenderableWidget(toggle("Totem Pop", () -> NovexClient.CONFIG.totemPop = !NovexClient.CONFIG.totemPop, x, y)); y += gap;
        addRenderableWidget(toggle("Performance Optimizer", () -> NovexClient.CONFIG.performanceOptimizer = !NovexClient.CONFIG.performanceOptimizer, x, y)); y += gap;
        addRenderableWidget(toggle("No Fog", () -> NovexClient.CONFIG.noFog = !NovexClient.CONFIG.noFog, x, y)); y += gap;
        addRenderableWidget(toggle("Armor HUD", () -> NovexClient.CONFIG.armorHud = !NovexClient.CONFIG.armorHud, x, y)); y += gap;
        addRenderableWidget(toggle("Inventory HUD", () -> NovexClient.CONFIG.inventoryHud = !NovexClient.CONFIG.inventoryHud, x, y)); y += gap;
        addRenderableWidget(toggle("Scoreboard", () -> NovexClient.CONFIG.scoreboardHud = !NovexClient.CONFIG.scoreboardHud, x, y)); y += gap;
        addRenderableWidget(toggle("Full Brightness", () -> NovexClient.CONFIG.fullBrightness = !NovexClient.CONFIG.fullBrightness, x, y)); y += gap;
        addRenderableWidget(toggle("Effect HUD", () -> NovexClient.CONFIG.effectHud = !NovexClient.CONFIG.effectHud, x, y));

        addRenderableWidget(Button.builder(Component.literal("Done"), b -> {
            NovexClient.CONFIG.save();
            this.minecraft.setScreen(parent);
        }).bounds(this.width / 2 - 110, this.height - 35, 220, 20).build());
    }

    private Button toggle(String name, Runnable action, int x, int y) {
        return Button.builder(Component.literal(name + "  [toggle]"), b -> {
            action.run();
            NovexClient.CONFIG.save();
        }).bounds(x, y, 220, 20).build();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, delta);
    }
}
