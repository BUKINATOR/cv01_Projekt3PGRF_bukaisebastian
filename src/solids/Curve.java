package solids;
import transforms.Cubic;
import transforms.Point3D;

public class Curve extends Solid {
    public Curve() {
        Cubic cubic = new Cubic(Cubic.BEZIER,new Point3D(0,2,0),new Point3D(0,4,2),new Point3D(4,0,1),new Point3D(0,1,1));

        int n = 10;
        for(int i = 0; i < n;i++) {
            float step = i / (float) n;
            //vertexBuffer.add(new Point3D(step,0,0));
            vertexBuffer.add(cubic.compute(step));
        }

        for (int i = 0; i < vertexBuffer.size() - 1; i++) {
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }

    }
}
