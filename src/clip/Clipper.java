package clip;

import model.Point;

import java.util.ArrayList;
import java.util.List;

public class Clipper {
    public List<Point> clip(List<Point> clipperPoints, List<Point> pointsToClip) {
        List<Point> pointsToReturn = new ArrayList<>();

        // TODO: Dodělat - slide 21. ořezání

        // Poznámky:
        // - umět spočítat tečná vektor - slide 28. ořezání
        // - umět spočítát normálu - slide 28. ořezání
        // - umět spočítat vektor k bodu, o kterém určuji, jestli je vlevo nebo vpravo - slide 28. ořezání
        // - umět dot product (sklarání součin) - přednáška lineární algebra
        // - vásledkem skalárního součinu je úhel, podle znamenka určím, jestli je vlevo nebo vpravo
        // - pokud vyjde kladné, point je na stejné straně jako normála


        return pointsToReturn;
    }
}
