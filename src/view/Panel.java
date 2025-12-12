package view;

import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final RasterBufferedImage raster;

    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setFocusTraversalKeysEnabled(false); // umožní zachytit Tab pro přepínání těles

        raster = new RasterBufferedImage(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(raster.getImage(), 0, 0, null);

        // Ovládací nápověda v levém horním rohu
        Graphics2D g2 = (Graphics2D) g.create();
        String[] lines = new String[]{
                "=== OVLÁDÁNÍ ===",
                "",
                "VÝBĚR TĚLESA:",
                "  Tab - další těleso",
                "",
                "POSUN TĚLESA:",
                "  ← →  - posun X (doleva/doprava)",
                "  ↑ ↓  - posun Y (nahoru/dolů)",
                "  X / Shift+X - posun X (alternativa)",
                "  Z / Shift+Z - posun Z (dopředu/dozadu)",
                "",
                "ROTACE TĚLESA:",
                "  R/F - rotace kolem osy Z",
                "  T/G - rotace kolem osy X",
                "  Y/H - rotace kolem osy Y",
                "",
                "MĚŘÍTKO:",
                "  + / = - zvětšení",
                "  - / _ - zmenšení",
                "",
                "KAMERA:",
                "  W/S/A/D - pohyb kamery",
                "  Drag myší - rozhlížení",
                "  P - přepínání projekce (persp/ortho)"
        };

        int padding = 8;
        int lineHeight = g.getFontMetrics().getHeight();
        int blockHeight = lineHeight * lines.length + padding * 2;
        int blockWidth = 380;

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(6, 6, blockWidth, blockHeight, 10, 10);

        g2.setColor(Color.WHITE);
        int y = 6 + padding + g.getFontMetrics().getAscent();
        for (String line : lines) {
            g2.drawString(line, 12, y);
            y += lineHeight;
        }
        g2.dispose();
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }
}
