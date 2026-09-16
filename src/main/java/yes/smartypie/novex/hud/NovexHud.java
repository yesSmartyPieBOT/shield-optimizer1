package yes.smartypie.novex.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import yes.smartypie.novex.NovexClient;

public final class NovexHud {
    private NovexHud() {}

    public static void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        int x = 8;
        int y = 8;

        if (NovexClient.CONFIG.armorHud) {
            y = renderArmor(graphics, x, y);
        }
        if (NovexClient.CONFIG.effectHud) {
            y = renderEffects(graphics, x, y + 4);
        }
        if (NovexClient.CONFIG.scoreboardHud) {
            graphics.drawString(mc.font, "Novex Scoreboard HUD", 8, mc.getWindow().getGuiScaledHeight() - 28, 0xFFFFFF);
        }
    }

    private static int renderArmor(GuiGraphics graphics, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        int slot = 0;
        for (EquipmentSlot equipmentSlot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = mc.player.getItemBySlot(equipmentSlot);
            if (!stack.isEmpty()) {
                int durability = stack.getMaxDamage() > 0 ? stack.getMaxDamage() - stack.getDamageValue() : 0;
                graphics.drawString(mc.font, stack.getHoverName().getString() + " " + durability, x, y + slot * 10, 0xFFFFFF);
                slot++;
            }
        }
        return y + slot * 10;
    }

    private static int renderEffects(GuiGraphics graphics, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        int row = 0;
        for (MobEffectInstance effect : mc.player.getActiveEffects()) {
            String name = effect.getEffect().value().getDisplayName().getString();
            int seconds = effect.getDuration() / 20;
            int amplifier = effect.getAmplifier() + 1;
            graphics.drawString(mc.font, name + " " + amplifier + " " + formatTime(seconds), x, y + row * 10, 0xFFFFFF);
            row++;
        }
        return y + row * 10;
    }

    private static String formatTime(int seconds) {
        return (seconds / 60) + ":" + String.format("%02d", seconds % 60);
    }
}
