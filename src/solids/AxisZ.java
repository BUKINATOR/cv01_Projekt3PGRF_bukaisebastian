package solids;

import transforms.Col;
import transforms.Point3D;

public class AxisZ extends Solid {
    public AxisZ() {
        color = new Col(0x0000ff);

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(0, 0, 1));

        addIndices(0, 1);
    }
}
