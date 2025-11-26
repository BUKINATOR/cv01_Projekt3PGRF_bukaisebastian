package solids;

import transforms.Point3D;

public class Arrow extends Solid {

    public Arrow() {
        // naplním VB - v souřadnicích rasteru
        vertexBuffer.add(new Point3D(-0.5, 0, 1)); // v0
        vertexBuffer.add(new Point3D(0.5, 0, 1)); // v1
        vertexBuffer.add(new Point3D(0.5, -0.1, 1)); // v2
        vertexBuffer.add(new Point3D(0.6, 0, 1)); // v3
        vertexBuffer.add(new Point3D(0.5, 0.1, 1)); // v4

        // naplním IB
        addIndices(0, 1);
        addIndices(2, 3);
        addIndices(3, 4);
        addIndices(4, 2);

    }
}
