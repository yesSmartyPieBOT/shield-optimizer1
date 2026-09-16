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
        int buttonWidth = 190;
        int buttonHeight = 20;
        int gapX = 200;
        int gapY = 25;
        int startX = this.width / 2 - buttonWidth - 5;
        int startY = 48;

        addRenderableWidget(toggle("Shield Optimizer", () -> NovexClient.CONFIG.shieldOptimizer,
                () -> NovexClient.CONFIG.shieldOptimizer = !NovexClient.CONFIG.shieldOptimizer,
                startX, startY, buttonWidth, buttonHeight));
        addRenderableWidget(toggle("Fire Overlay", () -> NovexClient.CONFIG.fireOverlay,
                () -> NovexClient.CONFIG.fireOverlay = !NovexClient.CONFIG.fireOverlay,
                startX + gapX, startY, buttonWidth, buttonHeight));

        addRenderableWidget(toggle("Totem Pop", () -> NovexClient.CONFIG.totemPop,
                () -> NovexClient.CONFIG.totemPop = !NovexClient.CONFIG.totemPop,
                startX, startY + gapY, buttonWidth, buttonHeight));
        addRenderableWidget(toggle("Performance Optimizer", () -> NovexClient.CONFIG.performanceOptimizer,
                () -> NovexClient.CONFIG.performanceOptimizer = !NovexClient.CONFIG.performanceOptimizer,
                startX + gapX, startY + gapY, buttonWidth, buttonHeight));

        addRenderableWidget(toggle("No Fog", () -> NovexClient.CONFIG.noFog,
                () -> NovexClient.CONFIG.noFog = !NovexClient.CONFIG.noFog,
                startX, startY + gapY * 2, buttonWidth, buttonHeight));
        addRenderableWidget(toggle("Armor HUD", () -> NovexClient.CONFIG.armorHud,
                () -> NovexClient.CONFIG.armorHud = !NovexClient.CONFIG.armorHud,
                startX + gapX, startY + gapY * 2, buttonWidth, buttonHeight));

        addRenderableWidget(toggle("Inventory HUD", () -> NovexClient.CONFIG.inventoryHud,
                () -> NovexClient.CONFIG.inventoryHud = !NovexClient.CONFIG.inventoryHud,
                startX, startY + gapY * 3, buttonWidth, buttonHeight));
        addRenderableWidget(toggle("Scoreboard", () -> NovexClient.CONFIG.scoreboardHud,
                () -> NovexClient.CONFIG.scoreboardHud = !NovexClient.CONFIG.scoreboardHud,
                startX + gapX, startY + gapY * 3, buttonWidth, buttonHeight));

        addRenderableWidget(toggle("Effect HUD", () -> NovexClient.CONFIG.effectHud,
                () -> NovexClient.CONFIG.effectHud = !NovexClient.CONFIG.effectHud,
                startX, startY + gapY * 4, buttonWidth, buttonHeight));

        addRenderableWidget(Button.builder(Component.literal("Done"), b -> {
            NovexClient.CONFIG.save();
            this.minecraft.setScreen(parent);
        }).bounds(this.width / 2 - 110, this.height - 28, 220, 20).build());
    }

    private Button toggle(String name, java.util.function.BooleanSupplier state, Runnable action,
                          int x, int y, int width, int height) {
        Button button = Button.builder(Component.empty(), b -> {
            action.run();
            NovexClient.CONFIG.save();
            b.setMessage(Component.literal(label(name, state.getAsBoolean())));
        }).bounds(x, y, width, height).build();
        button.setMessage(Component.literal(label(name, state.getAsBoolean())));
        return button;
    }

    private static String label(String name, boolean enabled) {
        return name + "  [" + (enabled ? "ON" : "OFF") + "]";
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, 0xFF101018);
        graphics.fill(this.width / 2 - 150, 10, this.width / 2 + 150, 37, 0xFF20202A);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        graphics.drawCenteredString(this.font, Component.literal("Novex Client Settings"), this.width / 2, 38, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, delta);
    }
}
