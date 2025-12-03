package solids;

import transforms.Col;
import transforms.Point3D;

public class Arrow extends Solid {

    public Arrow() {
        color = new Col(0xffff00);
        // naplním VB - v souřadnicích rasteru
        vertexBuffer.add(new Point3D(0, 0, 0)); // v0
        vertexBuffer.add(new Point3D(0.8, 0, 0)); // v1
        vertexBuffer.add(new Point3D(0.8, -0.2, 0)); // v2
        vertexBuffer.add(new Point3D(1, 0, 0)); // v3
        vertexBuffer.add(new Point3D(0.8, 0.2, 0)); // v4

        // naplním IB
        addIndices(0, 1);
        addIndices(2, 3);
        addIndices(3, 4);
        addIndices(4, 2);

    }
}
