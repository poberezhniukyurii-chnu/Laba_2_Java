package task1;

import java.util.Scanner;

/** Демонстрація роботи класу Point. */
public class Main {
    /** Читає дійсне число; приймає і кому, і крапку як десятковий роздільник. */
    static double readDouble(Scanner in) {
        return Double.parseDouble(in.next().replace(',', '.'));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введіть координати першої точки (x y): ");
        Point a = new Point(readDouble(in), readDouble(in));
        System.out.print("Введіть координати другої точки (x y): ");
        Point b = new Point(readDouble(in), readDouble(in));

        Point origin = new Point();                 // конструктор без параметрів
        Point copy = new Point(a);                  // конструктор копіювання
        Point polar = Point.fromPolar(2, 90);       // статичний фабричний метод

        System.out.println("A = " + a + ", B = " + b + ", O = " + origin);
        System.out.println("Копія A = " + copy + ", точка (r = 2, φ = 90°) = " + polar);

        System.out.printf("Відстань від A до початку координат: %.4f%n", a.distanceToOrigin());
        System.out.printf("Відстань між A і B: %.4f (статичний метод: %.4f)%n",
                a.distanceTo(b), Point.distance(a, b));

        double[] p = a.toPolar();
        System.out.printf("A у полярних координатах: r = %.4f, φ = %.2f°%n", p[0], p[1]);

        System.out.println("A.equals(копія A): " + a.equals(copy));
        System.out.println("A.notEquals(B): " + a.notEquals(b));
        System.out.println("(0; 2) == точка з полярних (2; 90°): " + new Point(0, 2).equals(polar));

        System.out.print("Зсув точки A вздовж осей (dx dy): ");
        double dx = readDouble(in), dy = readDouble(in);
        a.moveX(dx);
        a.moveY(dy);
        System.out.println("A після переміщення: " + a);
        System.out.println("A.equals(копія A) після переміщення: " + a.equals(copy));

        a.setX(b.getX());
        a.setY(b.getY());
        System.out.println("Після setX/setY координати A = B: " + a.equals(b));
        System.out.println("Усього створено точок: " + Point.getCreated());
        in.close();
    }
}
