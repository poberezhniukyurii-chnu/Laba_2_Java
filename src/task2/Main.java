package task2;

import java.util.Arrays;
import java.util.Scanner;

/** Демонстрація роботи класу QuadraticEquation. */
public class Main {

    static double readDouble(Scanner in) {
        return Double.parseDouble(in.next().replace(',', '.'));
    }

    static String format(double[] xs) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < xs.length; i++) {
            sb.append(i > 0 ? "; " : "").append(String.format("%.4f", xs[i]));
        }
        return sb.append("]").toString();
    }

    static void solve(QuadraticEquation eq) {
        int count = eq.rootCount();
        double[] roots = eq.roots();
        System.out.printf("Рівняння: %s, D = %.2f%n", eq, eq.discriminant());
        if (count == QuadraticEquation.INFINITE) {
            System.out.println("  коренів нескінченно багато, roots() = " + Arrays.toString(roots));
        } else if (roots == null) {
            System.out.println("  дійсних коренів немає, roots() = null");
        } else {
            System.out.println("  кількість коренів: " + count + ", корені: " + format(roots));
            for (double x : roots) {
                System.out.printf("  перевірка: f(%.4f) = %.2e%n", x, eq.valueAt(x));
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введіть коефіцієнти a b c: ");
        QuadraticEquation user = new QuadraticEquation(readDouble(in), readDouble(in), readDouble(in));
        solve(user);

        System.out.println("--- Інші способи створення ---");
        solve(new QuadraticEquation());                 // блок ініціалізації: x² − 1 = 0
        solve(new QuadraticEquation("1 2 5"));          // рядок, D < 0
        solve(new QuadraticEquation("0 2 -8"));         // лінійне рівняння
        solve(QuadraticEquation.fromRoots(3, 3));       // фабричний метод, подвійний корінь
        solve(new QuadraticEquation(0, 0, 0));          // 0 = 0

        QuadraticEquation e1 = new QuadraticEquation(1, -3, 2);
        QuadraticEquation e2 = new QuadraticEquation("2 -6 4");
        System.out.println("--- Порівняння ---");
        System.out.println(e1 + " equals " + e2 + ": " + e1.equals(e2)
                + ", hashCode однакові: " + (e1.hashCode() == e2.hashCode()));
        System.out.println(e1 + " equals " + user + ": " + e1.equals(user));
        System.out.println("Метод roots() викликано разів: " + QuadraticEquation.getSolvedCount());
        in.close();
    }
}
