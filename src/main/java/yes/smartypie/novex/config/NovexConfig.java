package yes.smartypie.novex.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class NovexConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("novex.json");

    public boolean shieldOptimizer = true;
    public boolean fireOverlay = false;
    public boolean totemPop = true;
    public boolean performanceOptimizer = true;
    public boolean noFog = true;
    public boolean armorHud = true;
    public boolean inventoryHud = true;
    public boolean scoreboardHud = true;
    public boolean effectHud = true;

    public float fireOpacity = 0.35f;
    public float totemScale = 0.75f;
    public int totemDurationTicks = 10;
    public float armorScale = 1.0f;
    public float inventoryScale = 1.0f;
    public float scoreboardScale = 1.0f;
    public float effectScale = 1.0f;

    public static NovexConfig load() {
        try {
            if (Files.exists(FILE)) {
                NovexConfig loaded = GSON.fromJson(Files.readString(FILE), NovexConfig.class);
                if (loaded != null) return loaded;
            }
        } catch (Exception ignored) {
        }
        NovexConfig config = new NovexConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(this));
        } catch (IOException ignored) {
        }
    }
}
