package com.github.img.netmusicbetterlogin.client.gui.function;

import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface RenderConsumer {
    void accept(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);
}
