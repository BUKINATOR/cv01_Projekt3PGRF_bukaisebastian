package solids;

import transforms.Col;
import transforms.Point3D;

/**
 * Drátový válec se dvěma kruhovými podstavami.
 */
public class Cylinder extends Solid {

    public Cylinder() {
        this(0.5, 1.0, 24);
    }

    public Cylinder(double radius, double height, int segments) {
        color = new Col(0x8844ff);

        double h = height / 2.0;
        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            // spodní a horní kružnice
            vertexBuffer.add(new Point3D(x, y, -h)); // i
            vertexBuffer.add(new Point3D(x, y, +h)); // i+1
        }

        // Hrany po obvodu + svislé hrany
        for (int i = 0; i < segments; i++) {
            int base = 2 * i;
            int next = (2 * ((i + 1) % segments));

            // spodní
            addIndices(base, next);
            // horní
            addIndices(base + 1, next + 1);
            // svislá hrana
            addIndices(base, base + 1);
        }
    }
}

