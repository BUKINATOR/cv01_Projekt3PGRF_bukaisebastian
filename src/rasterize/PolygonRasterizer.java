package rasterize;

import model.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        // Pokud je méně jak 3 pointy, nevykrelsuju

        // cyklus od i = 0 do konce seznamu pointů
        for (int i = 0; i < polygon.getSize(); i++) {
            int indexA = i;
            int indexB = i + 1;
            // jsemm na konci seznamu?

            // ano - spojím poslední s nultým tzn. nastavím indexB = 0

            // spojím pointy na indexu A a indexu B
            // lineRasterizer.rasterize(line point na indexu A, point na index B);
            // TODO: dodělat
        }

    }
}
