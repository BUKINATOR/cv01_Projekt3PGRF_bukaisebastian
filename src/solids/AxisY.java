package solids;

import transforms.Col;
import transforms.Point3D;

public class AxisY extends Solid {
    public AxisY() {
        color = new Col(0x00ff00);

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(0, 1, 0));

        addIndices(0, 1);
    }
}
