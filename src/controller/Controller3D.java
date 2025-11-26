package controller;


import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import render.Renderer;
import solids.Arrow;
import solids.Cube;
import solids.Solid;
import view.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private Renderer renderer;

    // Solids
    private Solid cube, arrow;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        this.renderer = new Renderer(lineRasterizer, panel.getRaster().getWidth(), panel.getRaster().getHeight());

        cube = new Cube();
        arrow = new Arrow();

        initListeners();

        drawScene();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawScene();
            }
        });
    }

    private void drawScene() {
        panel.getRaster().clear();

        renderer.render(arrow);

        panel.repaint();
    }
}
