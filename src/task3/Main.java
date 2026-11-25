package task3;

import java.util.Scanner;

/** На вхід — 4 цілих числа: чисельник і знаменник першого та другого дробів
 *  (з командного рядка або з клавіатури). */
public class Main {
    public static void main(String[] args) {
        long[] v = new long[4];
        if (args.length == 4) {
            for (int i = 0; i < 4; i++) v[i] = Long.parseLong(args[i]);
        } else {
            Scanner in = new Scanner(System.in);
            System.out.print("Введіть 4 цілих числа (a1 b1 a2 b2): ");
            for (int i = 0; i < 4; i++) v[i] = in.nextLong();
        }
        try {
            Drib x = new Drib(v[0], v[1]);
            Drib y = new Drib(v[2], v[3]);
            System.out.println("Перший дріб:  " + v[0] + "/" + v[1] + " -> " + x);
            System.out.println("Другий дріб:  " + v[2] + "/" + v[3] + " -> " + y);
            System.out.println(x + " + " + y + " = " + x.add(y));
            System.out.println(x + " - " + y + " = " + x.subtract(y));
            System.out.println(x + " * " + y + " = " + x.multiply(y));
            System.out.println(x + " : " + y + " = " + x.divide(y));
        } catch (ArithmeticException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        Drib s = new Drib("12/-18");
        System.out.printf("Дріб із рядка \"12/-18\": %s = %.6f%n", s, s.toDouble());
        System.out.println("new Drib(4, 6).equals(new Drib(\"2/3\")): "
                + new Drib(4, 6).equals(new Drib("2/3")));
        System.out.println("НСД(84, 36) = " + Drib.gcd(84, 36) + ", ціле 5 як дріб: " + new Drib(5));
        System.out.println("Виконано арифметичних операцій: " + Drib.getOperations());
    }
}
