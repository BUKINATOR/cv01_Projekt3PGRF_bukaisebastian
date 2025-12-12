package solids;

import transforms.Col;
import transforms.Point3D;

/**
 * Obecná parametrická křivka zadaná parametrickými rovnicemi
 * x(t), y(t), z(t) pro t v [0, 1]
 */
public class ParametricCurve extends Solid {
    
    public ParametricCurve() {
        color = new Col(0x00ffff);
        
        // Parametrická křivka - například spirála
        int n = 100; // Počet bodů
        for (int i = 0; i <= n; i++) {
            double t = i / (double) n; // parametr z [0, 1]
            
            // Parametrické rovnice pro 3D spirálu
            double x = 2 * Math.cos(4 * Math.PI * t);
            double y = 2 * Math.sin(4 * Math.PI * t);
            double z = 3 * t - 1.5; // z se mění lineárně
            
            vertexBuffer.add(new Point3D(x, y, z));
        }
        
        // Propojení bodů úsečkami
        for (int i = 0; i < vertexBuffer.size() - 1; i++) {
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }
    }
}

