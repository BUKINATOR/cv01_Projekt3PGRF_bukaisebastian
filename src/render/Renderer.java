package render;

import model.Line;
import rasterize.LineRasterizer;
import solids.Solid;
import transforms.Point3D;
import transforms.Vec3D;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;

    public Renderer(LineRasterizer lineRasterizer, int width, int height) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
    }

    public void render(Solid solid) {
        // TODO: naprogramovat
        for (int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D a = solid.getVertexBuffer().get(indexA);
            Point3D b = solid.getVertexBuffer().get(indexB);

            // Tranformace do okna obrazovky
            Vec3D vecA = new Vec3D(a);
            Vec3D vecB = new Vec3D(b);
            // FIXME: Pro tohle vytvoříme metodu
            vecA = vecA.mul(new Vec3D(1, -1, 1))
                    .add(new Vec3D(1, 1, 0))
                    .mul(new Vec3D((double)(width - 1) / 2, (double)(height - 1) / 2, 1));
            vecB = vecB.mul(new Vec3D(1, -1, 1))
                    .add(new Vec3D(1, 1, 0))
                    .mul(new Vec3D((double)(width - 1) / 2, (double)(height - 1) / 2, 1));

            lineRasterizer.rasterize(
                    (int)Math.round(vecA.getX()),
                    (int)Math.round(vecA.getY()),
                    (int)Math.round(vecB.getX()),
                    (int)Math.round(vecB.getY())
            );
        }
        // - mám vb a ib
        // - procházím ib, podle indexu si vezmu 2 vrcholy z vb
        // - spojím se úsečkou
    }

}
