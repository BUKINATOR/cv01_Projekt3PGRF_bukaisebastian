package rasterize;

public abstract class LineRasterizer {
    protected RasterBufferedImage raster;
    // TODO: vyřešit barvu.

    public LineRasterizer(RasterBufferedImage raster) {
        this.raster = raster;
    }

    public void rasterize(int x1, int y1, int x2, int y2) {

    }
}
