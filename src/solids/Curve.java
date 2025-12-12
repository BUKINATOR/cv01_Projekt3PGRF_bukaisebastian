package solids;
import transforms.Col;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

/**
 * Kubika zadaná čtyřmi pevně zadanými body
 */
public class Curve extends Solid {
    // Pevně zadané 4 body pro všechny kubiky
    private static final Point3D P1 = new Point3D(0, 2, 0);
    private static final Point3D P2 = new Point3D(0, 4, 2);
    private static final Point3D P3 = new Point3D(4, 0, 1);
    private static final Point3D P4 = new Point3D(0, 1, 1);
    
    public Curve() {
        this(Cubic.BEZIER);
    }
    
    public Curve(Mat4 baseMat) {
        // Barva podle typu kubiky
        if (baseMat == Cubic.BEZIER) {
            color = new Col(0xff0000); // červená
        } else if (baseMat == Cubic.FERGUSON) {
            color = new Col(0x00ff00); // zelená
        } else if (baseMat == Cubic.COONS) {
            color = new Col(0x0000ff); // modrá
        } else {
            color = new Col(0xffff00); // žlutá (default)
        }
        
        Cubic cubic = new Cubic(baseMat, P1, P2, P3, P4);

        int n = 50; // Více bodů pro hladší křivku
        for(int i = 0; i <= n; i++) {
            double step = i / (double) n;
            vertexBuffer.add(cubic.compute(step));
        }

        for (int i = 0; i < vertexBuffer.size() - 1; i++) {
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }
    }
}
