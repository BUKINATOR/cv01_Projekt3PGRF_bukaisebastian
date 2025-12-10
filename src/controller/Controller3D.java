package controller;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import render.Renderer;
import solids.*;
import transforms.*;
import view.Panel;

public class Controller3D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private Renderer renderer;

    // Solids
    private Solid cube, pyramid, cylinder, arrow, axisX, axisY, axisZ, curve;
    private final List<Solid> solids = new ArrayList<>();
    private int activeSolidIndex = 0;
    private int lastMouseX, lastMouseY;

    // Camera
    private Camera camera;
    private Mat4 proj;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.lineRasterizer = new LineRasterizerGraphics(panel.getRaster());

        camera = new Camera()
                .withPosition(new Vec3D(0, -5, 2))
                .withAzimuth(Math.toRadians(90))
                .withZenith(Math.toRadians(-15))
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
        cube.mulModel(new Mat4Transl(-1.0, 0, 0));

        pyramid = new Pyramid();
        pyramid.mulModel(new Mat4Transl(0, 0, -1.0));

        cylinder = new Cylinder();
        cylinder.mulModel(new Mat4Transl(1.2, 0, 0));

        arrow = new Arrow();
        curve = new Curve();

        axisX = new AxisX();
        axisY = new AxisY();
        axisZ = new AxisZ();

        solids.add(cube);
        solids.add(pyramid);
        solids.add(cylinder);
        solids.add(arrow);
        solids.add(curve);

        initListeners();

        drawScene();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    translateActive(0.2, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    translateActive(-0.2, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    translateActive(0, 0, 0.2);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    translateActive(0, 0, -0.2);
                }

                if (e.getKeyCode() == KeyEvent.VK_R) {
                    rotateActiveZ(15);
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    rotateActiveZ(-15);
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    rotateActiveX(15);
                }
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    rotateActiveX(-15);
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    rotateActiveY(15);
                }
                if (e.getKeyCode() == KeyEvent.VK_H) {
                    rotateActiveY(-15);
                }

                if (e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_PLUS) {
                    scaleActive(1.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    scaleActive(0.9);
                }

                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    activeSolidIndex = (activeSolidIndex + 1) % solids.size();
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

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                panel.requestFocusInWindow();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastMouseX;
                int dy = e.getY() - lastMouseY;
                lastMouseX = e.getX();
                lastMouseY = e.getY();

                double sens = 0.01;
                camera = camera
                        .addAzimuth(dx * sens)
                        .addZenith(-dy * sens);
                drawScene();
            }
        });


    }

    private Solid activeSolid() {
        return solids.get(activeSolidIndex);
    }

    private void translateActive(double dx, double dy, double dz) {
        activeSolid().mulModel(new Mat4Transl(dx, dy, dz));
    }

    private void rotateActiveX(double degrees) {
        activeSolid().mulModel(new Mat4RotX(Math.toRadians(degrees)));
    }

    private void rotateActiveY(double degrees) {
        activeSolid().mulModel(new Mat4RotY(Math.toRadians(degrees)));
    }

    private void rotateActiveZ(double degrees) {
        activeSolid().mulModel(new Mat4RotZ(Math.toRadians(degrees)));
    }

    private void scaleActive(double factor) {
        activeSolid().mulModel(new Mat4Scale(factor, factor, factor));
    }

    private void drawScene() {
        panel.getRaster().clear();

        renderer.setView(camera.getViewMatrix());

        renderer.render(axisX);
        renderer.render(axisY);
        renderer.render(axisZ);

        renderer.render(cube);
        renderer.render(pyramid);
        renderer.render(cylinder);

        renderer.render(arrow);

        renderer.render(curve);


        panel.repaint();
    }
}
