package solids;

import transforms.Col;
import transforms.Point3D;

/**
 * Jednoduchá čtyřboká pyramida s hranou podstavy 1 a výškou 1.
 */
public class Pyramid extends Solid {

    public Pyramid() {
        color = new Col(0xff8800);

        double s = 0.5;   // polovina hrany podstavy
        double h = 0.5;   // výška vrchol nad středem podstavy

        // Vrcholy
        vertexBuffer.add(new Point3D(-s, -s, 0)); // 0
        vertexBuffer.add(new Point3D(+s, -s, 0)); // 1
        vertexBuffer.add(new Point3D(+s, +s, 0)); // 2
        vertexBuffer.add(new Point3D(-s, +s, 0)); // 3
        vertexBuffer.add(new Point3D(0, 0, h));   // 4 špička

        // Hrany podstavy
        addIndices(0, 1, 1, 2, 2, 3, 3, 0);
        // Hrany stěn
        addIndices(0, 4, 1, 4, 2, 4, 3, 4);
    }
}

