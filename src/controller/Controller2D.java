package controller;


import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerColorTransition;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import view.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private int color = 0xffffff;

    private LineRasterizer lineRasterizer;

    // To draw
    private Polygon polygon = new Polygon();

// Ukázka zapamatování si seznamu úseček
    private ArrayList<Line> lines = new ArrayList<>();;
    private int startX, startY;
    private boolean isLineStartSet;

    public Controller2D(Panel panel) {
        this.panel = panel;

        lineRasterizer = new LineRasterizerColorTransition(panel.getRaster());
        //lineRasterizer = new LineRasterizerTrivial(panel.getRaster());

        initListeners();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//             Ukázka zapamatování si seznamu úseček
                if (!isLineStartSet) {
                    startX = e.getX();
                    startY = e.getY();
                    isLineStartSet = true;
                    return;
                }

                isLineStartSet = false;
                Line line = new Line(startX, startY, e.getX(), e.getY());
                lines.add(line);
//                polygon.addPoint(new Point(e.getX(), e.getY()));
                drawScene();
            }
        });


    }

    private void drawScene() {
        panel.getRaster().clear();

//      Ukázka zapamatování si seznamu úseček
        for(Line line : lines)
            lineRasterizer.rasterize(line);


        panel.repaint();
    }
}
