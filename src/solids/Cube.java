package solids;

import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {

    public Cube() {
        color = new Col(0x00ffff);

        // 8 vrcholů krychle o hraně 1, střed v počátku
        double s = 0.5;
        vertexBuffer.add(new Point3D(-s, -s, -s)); // 0
        vertexBuffer.add(new Point3D(+s, -s, -s)); // 1
        vertexBuffer.add(new Point3D(+s, +s, -s)); // 2
        vertexBuffer.add(new Point3D(-s, +s, -s)); // 3
        vertexBuffer.add(new Point3D(-s, -s, +s)); // 4
        vertexBuffer.add(new Point3D(+s, -s, +s)); // 5
        vertexBuffer.add(new Point3D(+s, +s, +s)); // 6
        vertexBuffer.add(new Point3D(-s, +s, +s)); // 7

        // 12 hran (po dvou indexech)
        addIndices(
                0, 1, 1, 2, 2, 3, 3, 0,       // spodní čtverec
                4, 5, 5, 6, 6, 7, 7, 4,       // horní čtverec
                0, 4, 1, 5, 2, 6, 3, 7        // svislé hrany
        );
    }
}
