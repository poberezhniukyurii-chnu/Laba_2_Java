package task2;

import java.util.Arrays;

/**
 * Квадратне рівняння a·x² + b·x + c = 0.
 * Лабораторна робота №2, завдання 2 (варіант 11). Побережнюк Ю.В., група 241б.
 */
public class QuadraticEquation {
    /** Повертається з rootCount(), якщо коренів нескінченно багато (0 = 0). */
    public static final int INFINITE = -1;
    private static final double EPS = 1e-12;

    private static int solvedCount;     // скільки разів викликався roots()

    private double a;
    private double b;
    private double c;

    // Блок ініціалізації: значення за замовчуванням x² − 1 = 0
    {
        a = 1;
        b = 0;
        c = -1;
    }

    /** Рівняння x² − 1 = 0 (значення з блоку ініціалізації). */
    public QuadraticEquation() {
    }

    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /** Ініціалізація рядком із трьома коефіцієнтами, напр. "1 -3 2". */
    public QuadraticEquation(String coefficients) {
        String[] parts = coefficients.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("потрібно три коефіцієнти: " + coefficients);
        }
        a = Double.parseDouble(parts[0]);
        b = Double.parseDouble(parts[1]);
        c = Double.parseDouble(parts[2]);
    }

    /** Статичний фабричний метод: зведене рівняння з коренями x1, x2 (теорема Вієта). */
    public static QuadraticEquation fromRoots(double x1, double x2) {
        return new QuadraticEquation(1, -(x1 + x2), x1 * x2);
    }

    public static int getSolvedCount() {
        return solvedCount;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    /** Дискримінант D = b² − 4ac. */
    public double discriminant() {
        return b * b - 4 * a * c;
    }

    /** Значення лівої частини в точці x (для перевірки коренів). */
    public double valueAt(double x) {
        return (a * x + b) * x + c;
    }

    /** Кількість дійсних коренів: 0, 1, 2 або INFINITE. */
    public int rootCount() {
        if (Math.abs(a) < EPS) {                    // лінійне рівняння b·x + c = 0
            if (Math.abs(b) < EPS) {
                return Math.abs(c) < EPS ? INFINITE : 0;
            }
            return 1;
        }
        double d = discriminant();
        if (Math.abs(d) < EPS) return 1;
        return d > 0 ? 2 : 0;
    }

    /** Корені рівняння: масив з одного або двох чисел, або null, якщо коренів немає
     *  (або їх нескінченно багато). */
    public double[] roots() {
        solvedCount++;
        switch (rootCount()) {
            case 1:
                if (Math.abs(a) < EPS) return new double[]{-c / b};
                return new double[]{-b / (2 * a)};
            case 2:
                double sqrtD = Math.sqrt(discriminant());
                double x1 = (-b - sqrtD) / (2 * a);
                double x2 = (-b + sqrtD) / (2 * a);
                return new double[]{Math.min(x1, x2), Math.max(x1, x2)};
            default:
                return null;
        }
    }

    /** Коефіцієнти, поділені на перший ненульовий (для порівняння рівносильних рівнянь). */
    private double[] normalized() {
        double k = Math.abs(a) > EPS ? a : Math.abs(b) > EPS ? b : (Math.abs(c) > EPS ? c : 1);
        return new double[]{a / k, b / k, c / k};
    }

    /** Рівняння рівні, якщо їх коефіцієнти пропорційні (2x² − 6x + 4 = 0 ≡ x² − 3x + 2 = 0). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuadraticEquation)) return false;
        double[] p = normalized(), q = ((QuadraticEquation) obj).normalized();
        for (int i = 0; i < 3; i++) {
            if (Math.abs(p[i] - q[i]) > 1e-9) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        double[] p = normalized();
        long[] r = new long[3];
        for (int i = 0; i < 3; i++) r[i] = Math.round(p[i] * 1e6);
        return Arrays.hashCode(r);
    }

    private static String term(double k, String x, boolean first) {
        if (Math.abs(k) < EPS) return "";
        String sign = k < 0 ? (first ? "-" : " - ") : (first ? "" : " + ");
        double abs = Math.abs(k);
        String num = (abs == Math.rint(abs)) ? String.valueOf((long) abs) : String.valueOf(abs);
        if (!x.isEmpty() && abs == 1) num = "";
        return sign + num + x;
    }

    @Override
    public String toString() {
        String s = term(a, "x²", true);
        s += term(b, "x", s.isEmpty());
        s += term(c, "", s.isEmpty());
        return (s.isEmpty() ? "0" : s) + " = 0";
    }
}
