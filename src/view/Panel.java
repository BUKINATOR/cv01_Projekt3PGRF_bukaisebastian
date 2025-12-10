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
                "Ovládání:",
                "Tab - další těleso",
                "Šipky - posun X/Y aktivního tělesa",
                "ArrowUp/ArrowDown - posun Z",
                "R/F - rotace kolem Z, T/G - kolem X, Y/H - kolem Y",
                "+/- - měřítko aktivního tělesa",
                "W/S/A/D - pohyb kamery",
                "Drag myší - rozhlížení kamery",
                "P - přepínání projekce (persp/ortho)"
        };

        int padding = 8;
        int lineHeight = g.getFontMetrics().getHeight();
        int blockHeight = lineHeight * lines.length + padding * 2;
        int blockWidth = 340;

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
