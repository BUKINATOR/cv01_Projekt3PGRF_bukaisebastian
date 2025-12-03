package solids;

import transforms.Col;
import transforms.Point3D;

import java.awt.*;

public class AxisX extends Solid {
    public AxisX() {
        color = new Col(0xff0000);

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(1, 0, 0));

        addIndices(0, 1);
    }
}
