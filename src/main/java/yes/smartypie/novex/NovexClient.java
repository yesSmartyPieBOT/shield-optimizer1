package yes.smartypie.novex;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import yes.smartypie.novex.config.NovexConfig;
import yes.smartypie.novex.hud.NovexHud;
import yes.smartypie.novex.screen.NovexConfigScreen;

public final class NovexClient implements ClientModInitializer {
    public static final String MOD_ID = "novex";
    public static final NovexConfig CONFIG = NovexConfig.load();

    private static KeyMapping openConfig;
    private static KeyMapping toggleScoreboard;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "category"));

        openConfig = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.novex.open_config", InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, category));
        toggleScoreboard = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.novex.scoreboard", InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O, category));

        HudElementRegistry.addLast(id("hud"), NovexHud::render);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfig.consumeClick()) {
                client.setScreen(new NovexConfigScreen(client.screen));
            }
            while (toggleScoreboard.consumeClick()) {
                CONFIG.scoreboardHud = !CONFIG.scoreboardHud;
                CONFIG.save();
            }
        });
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
