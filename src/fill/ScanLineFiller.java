package fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;

import java.util.ArrayList;

public class ScanLineFiller implements Filler {
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Polygon polygon;

    public ScanLineFiller(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
    }

    @Override
    public void fill() {
        // TODO: nechci vyplnit polygon, který má méně, jak 3 vrcholy

        // Potřebujeme vytvořit seznam hran
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < polygon.getSize(); i++) {
            int indexA = i;
            int indexB = i + 1;

            if (indexB == polygon.getSize())
                indexB = 0;

            Point a = polygon.getPoint(indexA);
            Point b = polygon.getPoint(indexB);

            Edge edge = new Edge(a, b);
            // Nechceme přidat horizontální hrany
            if (!edge.isHorizontal()) {
                // Nastavím spárvnou orientaci hrany
                edge.orientate();
                // Přidám
                edges.add(edge);
            }
        }

        // TODO: Najít yMin a yMax
        int yMin = edges.get(0).getY1();
        int yMax = 0;
        // TODO: projít všechny pointy polygonu a najít min a amx

        for (int y = yMin; y <= yMax; y++) {
            // vytvořím seznam průsečíků
            ArrayList<Integer> intersections = new ArrayList<>();

            // Prokaždou hranu:
            for(Edge edge : edges) {
                // zeptám se, jestli existuje průsečík
                if(!edge.isIntersection(y))
                    continue;
                // pokud ano, tak ho spočítám
                int x = edge.getIntersection(y);
                // uložím do seznamu průsečíků
                intersections.add(x);
            }

            // TODO: Seřadit průsečíky od min po max

            // TODO: Spojím (obarvím) průsečíky, 0 - 1, 2 - 3, 4 - 5, 6 - 7
        }

        // TODO: Vykreslím hranici polygonu
    }
}
