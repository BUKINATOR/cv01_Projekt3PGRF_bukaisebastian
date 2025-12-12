package render;
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
        render(solid, true);
    }
    
    public void render(Solid solid, boolean useModel) {
        lineRasterizer.setColor(solid.getColor());
        for (int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            // Model space
            Point3D a = solid.getVertexBuffer().get(indexA);
            Point3D b = solid.getVertexBuffer().get(indexB);

            // pronásobit model maticí (pokud useModel == true)
            // Model space -> World space
            if (useModel) {
                a = a.mul(solid.getModel());
                b = b.mul(solid.getModel());
            }

            // pronásobit view maticí
            // World space -> View space
            a = a.mul(view);
            b = b.mul(view);

            // pronásobit proj maticí
            // View space -> Clip space
            a = a.mul(proj);
            b = b.mul(proj);

            // Ořezání v clip space + bezpečná dehomogenizace
            ClippedLine clipped = clipLine(a, b);
            if (clipped == null) {
                continue; // celý úsek je mimo view volume
            }
            a = clipped.a;
            b = clipped.b;

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

    // --- Clipping ---
    private static class ClippedLine {
        Point3D a, b;

        ClippedLine(Point3D a, Point3D b) {
            this.a = a;
            this.b = b;
        }
    }

    // Cohen–Sutherland-like clipping in clip space against x,y in [-w,w], z in [0,w], w>0
    private ClippedLine clipLine(Point3D a, Point3D b) {
        if (a.getW() <= 0 && b.getW() <= 0) {
            return null; // za kamerou
        }

        // Outcodes
        int codeA = outcode(a);
        int codeB = outcode(b);

        // Triviální reject
        if ((codeA & codeB) != 0) {
            return null;
        }

        Point3D p1 = a;
        Point3D p2 = b;

        // Pokud je alespoň jeden vrchol mimo, provedeme průsečíky s rovinami
        for (int iter = 0; iter < 6; iter++) {
            codeA = outcode(p1);
            codeB = outcode(p2);

            if ((codeA | codeB) == 0) {
                // oba uvnitř
                break;
            }

            int codeOut = codeA != 0 ? codeA : codeB;

            // vyber rovinu podle bitu
            double t;
            Point3D newPoint = null;

            if ((codeOut & 1) != 0) { // x < -w
                t = ( -p1.getW() - p1.getX()) / ((p2.getX() - p1.getX()) + (p2.getW() - p1.getW()));
            } else if ((codeOut & 2) != 0) { // x > w
                t = ( p1.getW() - p1.getX()) / ((p2.getX() - p1.getX()) - (p2.getW() - p1.getW()));
            } else if ((codeOut & 4) != 0) { // y < -w
                t = ( -p1.getW() - p1.getY()) / ((p2.getY() - p1.getY()) + (p2.getW() - p1.getW()));
            } else if ((codeOut & 8) != 0) { // y > w
                t = ( p1.getW() - p1.getY()) / ((p2.getY() - p1.getY()) - (p2.getW() - p1.getW()));
            } else if ((codeOut & 16) != 0) { // z < 0
                t = (0 - p1.getZ()) / (p2.getZ() - p1.getZ());
            } else { // z > w
                t = (p1.getW() - p1.getZ()) / ((p2.getZ() - p1.getZ()) - (p2.getW() - p1.getW()));
            }

            t = Math.max(0, Math.min(1, t));
            newPoint = interpolate(p1, p2, t);

            if (codeOut == codeA) {
                p1 = newPoint;
            } else {
                p2 = newPoint;
            }
        }

        // Dehomogenizace (bezpečně, w > 0)
        if (p1.getW() <= 0 || p2.getW() <= 0) {
            return null;
        }
        p1 = p1.mul(1 / p1.getW());
        p2 = p2.mul(1 / p2.getW());

        return new ClippedLine(p1, p2);
    }

    private int outcode(Point3D p) {
        int code = 0;
        double x = p.getX(), y = p.getY(), z = p.getZ(), w = p.getW();
        if (x < -w) code |= 1;   // left
        if (x >  w) code |= 2;   // right
        if (y < -w) code |= 4;   // bottom
        if (y >  w) code |= 8;   // top
        if (z <  0) code |= 16;  // near
        if (z >  w) code |= 32;  // far
        return code;
    }

    private Point3D interpolate(Point3D a, Point3D b, double t) {
        return new Point3D(
                a.getX() + (b.getX() - a.getX()) * t,
                a.getY() + (b.getY() - a.getY()) * t,
                a.getZ() + (b.getZ() - a.getZ()) * t,
                a.getW() + (b.getW() - a.getW()) * t
        );
    }
}
