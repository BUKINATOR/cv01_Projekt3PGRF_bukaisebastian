package controller;


import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import render.Renderer;
import solids.*;
import transforms.*;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private Renderer renderer;

    // Solids
    private Solid cube, arrow, axisX, axisY, axisZ;

    // Camera
    private Camera camera;
    private Mat4 proj;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.lineRasterizer = new LineRasterizerGraphics(panel.getRaster());

        camera = new Camera()
                .withPosition(new Vec3D(0.4, -1.5, 1))
                .withAzimuth(Math.toRadians(90))
                .withZenith(Math.toRadians(-25))
                .withFirstPerson(true);

        proj = new Mat4PerspRH(
                Math.toRadians(90),
                panel.getRaster().getHeight() / (double) panel.getRaster().getWidth(),
                0.1,
                100);

        this.renderer = new Renderer(
                lineRasterizer,
                panel.getRaster().getWidth(),
                panel.getRaster().getHeight(),
                camera.getViewMatrix(),
                proj);

        cube = new Cube();
        arrow = new Arrow();

        axisX = new AxisX();
        axisY = new AxisY();
        axisZ = new AxisZ();

        initListeners();

        drawScene();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    arrow.mulModel(new Mat4Transl(0.5, 0, 0));
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    arrow.mulModel(new Mat4Transl(-0.5, 0, 0));
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    arrow.mulModel(
                            new Mat4Transl(-0.5, 0, 0)
                                    .mul(new Mat4RotZ(Math.toRadians(15)))
                                    .mul(new Mat4Transl(0.5, 0, 0))
                    );
                }

                if(e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.forward(0.5);
                }

                if(e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(0.5);
                }

                if(e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.backward(0.5);
                }

                if(e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(0.5);
                }

                drawScene();
            }
        });


    }

    private void drawScene() {
        panel.getRaster().clear();

        renderer.setView(camera.getViewMatrix());

        renderer.render(axisX);
        renderer.render(axisY);
        renderer.render(axisZ);

        renderer.render(arrow);


        panel.repaint();
    }
}
