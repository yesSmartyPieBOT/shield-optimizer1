package yes.smartypie.novex.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import yes.smartypie.novex.NovexClient;

public final class NovexHud {
    private NovexHud() {}

    public static void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        int x = 8;
        int y = 8;
        if (NovexClient.CONFIG.armorHud) y = renderArmor(graphics, x, y, NovexClient.CONFIG.armorScale);
        if (NovexClient.CONFIG.inventoryHud) y = renderInventory(graphics, x, y + 6, NovexClient.CONFIG.inventoryScale);
        if (NovexClient.CONFIG.effectHud) y = renderEffects(graphics, x, y + 6, NovexClient.CONFIG.effectScale);
        if (NovexClient.CONFIG.scoreboardHud) renderScoreboard(graphics, mc, NovexClient.CONFIG.scoreboardScale);
    }

    private static int renderArmor(GuiGraphics graphics, int x, int y, float scale) {
        Minecraft mc = Minecraft.getInstance();
        int row = 0;
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale);
        int sx = Math.round(x / scale);
        int sy = Math.round(y / scale);
        graphics.drawString(mc.font, "Armor", sx, sy, 0x55FFFF, true);
        sy += 11;
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = mc.player.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                int durability = stack.isDamageableItem() ? stack.getMaxDamage() - stack.getDamageValue() : 0;
                graphics.drawString(mc.font, stack.getHoverName().getString() + (stack.isDamageableItem() ? " " + durability : ""), sx, sy + row * 10, 0xFFFFFF);
                row++;
            }
        }
        if (row == 0) graphics.drawString(mc.font, "No armor equipped", sx, sy, 0xAAAAAA);
        graphics.pose().popPose();
        return y + Math.max(22, row * 10 + 11);
    }

    private static int renderInventory(GuiGraphics graphics, int x, int y, float scale) {
        Minecraft mc = Minecraft.getInstance();
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale);
        int sx = Math.round(x / scale);
        int sy = Math.round(y / scale);
        graphics.drawString(mc.font, "Inventory / Hotbar", sx, sy, 0x55FF55, true);
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
                if (line.length() > 0) line.append(" | ");
                line.append(i + 1).append(":").append(stack.getHoverName().getString()).append(" x").append(stack.getCount());
            }
        }
        if (line.length() == 0) line.append("Hotbar empty");
        graphics.drawString(mc.font, line.toString(), sx, sy + 11, 0xFFFFFF);
        graphics.pose().popPose();
        return y + 22;
    }

    private static int renderEffects(GuiGraphics graphics, int x, int y, float scale) {
        Minecraft mc = Minecraft.getInstance();
        int row = 0;
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale);
        int sx = Math.round(x / scale);
        int sy = Math.round(y / scale);
        graphics.drawString(mc.font, "Effects", sx, sy, 0xFFFF55, true);
        sy += 11;
        for (MobEffectInstance effect : mc.player.getActiveEffects()) {
            int seconds = Math.max(0, effect.getDuration() / 20);
            int amplifier = effect.getAmplifier() + 1;
            graphics.drawString(mc.font, effect.getEffect().value().getDisplayName().getString() + " " + amplifier + " " + formatTime(seconds), sx, sy + row * 10, 0xFFFFFF);
            row++;
        }
        if (row == 0) graphics.drawString(mc.font, "No active effects", sx, sy, 0xAAAAAA);
        graphics.pose().popPose();
        return y + Math.max(22, row * 10 + 22);
    }

    private static void renderScoreboard(GuiGraphics graphics, Minecraft mc, float scale) {
        if (mc.level == null) return;
        Scoreboard scoreboard = mc.level.getScoreboard();
        Objective objective = scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
        if (objective == null) return;
        int height = mc.getWindow().getGuiScaledHeight();
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale);
        int x = Math.round(8 / scale);
        int y = Math.round((height - 90) / scale);
        graphics.drawString(mc.font, objective.getDisplayName(), x, y, 0xFFFFFF, true);
        int row = 1;
        for (var score : scoreboard.listPlayerScores(objective)) {
            if (row > 7) break;
            graphics.drawString(mc.font, score.owner() + ": " + score.value(), x, y + row * 10, 0xFFFFFF);
            row++;
        }
        graphics.pose().popPose();
    }

    private static String formatTime(int seconds) {
        return (seconds / 60) + ":" + String.format("%02d", seconds % 60);
    }
}
