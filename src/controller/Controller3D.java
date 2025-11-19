package controller;


import render.Renderer;
import solids.Cube;
import solids.Solid;
import view.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D {
    private final Panel panel;
    private Renderer renderer;

    // Solids
    private Solid cube;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.renderer = new Renderer();

        cube = new Cube();

        initListeners();
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

        renderer.render(cube);

        panel.repaint();
    }
}
