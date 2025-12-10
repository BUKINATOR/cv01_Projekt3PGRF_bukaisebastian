package controller;


import clip.Clipper;
import fill.Filler;
import fill.ScanLineFiller;
import fill.SeedFiller;
import model.Line;
import model.Point;
import model.Polygon;
import rasterize.*;
import view.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller2D {
    private final Panel panel;
    private int color = 0xffffff;

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;

    // To draw
    private Polygon polygon = new Polygon();
    private Polygon polygonClipper = new Polygon();

    private Filler seedFiller;
    private Point seedFillerStartPoint;

    // Ukázka zapamatování si seznamu úseček
    private ArrayList<Line> lines = new ArrayList<>();
    ;
    private int startX, startY;
    private boolean isLineStartSet;

    public Controller2D(Panel panel) {
        this.panel = panel;

        //lineRasterizer = new LineRasterizerColorTransition(panel.getRaster());
        //lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        initListeners();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    polygonClipper.addPoint(new Point(e.getX(), e.getY()));
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    polygon.addPoint(new Point(e.getX(), e.getY()));
                }

//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    seedFillerStartPoint = new Point(e.getX(), e.getY());
//                }

                drawScene();
            }
        });


    }

    private void drawScene() {
        panel.getRaster().clear();

        // rasterizuju ořezávací i ořezávaný polygon
        polygonRasterizer.rasterize(polygonClipper);
        polygonRasterizer.rasterize(polygon);

        // Provedu ořezání
        Clipper clipper = new Clipper();
        List<Point> clippedPoints = clipper.clip(polygonClipper.getPoints(), polygon.getPoints());

        // Použiju scanline na výsledek ořezání
        Filler scanLine = new ScanLineFiller(lineRasterizer, polygonRasterizer, new Polygon(new ArrayList<>(clippedPoints)));
        scanLine.fill();



        // použít seed filler
//        if (seedFillerStartPoint != null) {
//            seedFiller = new SeedFiller(panel.getRaster(), 0x00ff00, seedFillerStartPoint.getX(), seedFillerStartPoint.getY());
//            seedFiller.fill();
//        }

        panel.repaint();
    }
}
