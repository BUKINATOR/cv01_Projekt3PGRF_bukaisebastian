package render;
import model.Point;
import rasterize.LineRasterizer;
import solids.Solid;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;
    private Mat4 view, proj;

    public Renderer(LineRasterizer lineRasterizer, int width, int height, Mat4 view, Mat4 proj) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
        this.view = view;
        this.proj = proj;
    }

    public void render(Solid solid) {
        lineRasterizer.setColor(solid.getColor());
        for (int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            // Model space
            Point3D a = solid.getVertexBuffer().get(indexA);
            Point3D b = solid.getVertexBuffer().get(indexB);

            // pronásobit model maticí
            // Model space -> World space
            a = a.mul(solid.getModel());
            b = b.mul(solid.getModel());

            // pronásobit view maticí
            // World space -> View space
            a = a.mul(view);
            b = b.mul(view);

            // pronásobit proj maticí
            // View space -> Clip space
            a = a.mul(proj);
            b = b.mul(proj);

            // TODO: ořezání
            // TODO: dehomogenizace - pozor na dělení 0
            // Clip space -> NDC
            a = a.mul(1 / a.getW());
            b = b.mul(1 / b.getW());

            // Tranformace do okna obrazovky
            Vec3D vecA = transformToWindow(a);
            Vec3D vecB = transformToWindow(b);

            lineRasterizer.rasterize(
                    (int)Math.round(vecA.getX()),
                    (int)Math.round(vecA.getY()),
                    (int)Math.round(vecB.getX()),
                    (int)Math.round(vecB.getY())
            );
        }
    }

    private Vec3D transformToWindow(Point3D p) {
        return new Vec3D(p).mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((double)(width - 1) / 2, (double)(height - 1) / 2, 1));
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
}
