package task3;

/**
 * Простий дріб numerator/denominator, завжди нескоротний, знаменник додатний.
 * Лабораторна робота №2, завдання 3 (варіант 1). Побережнюк Ю.В., група 241б.
 */
public class Drib {
    private static int operations;          // кількість виконаних арифметичних операцій

    // Статичний блок ініціалізації: виконується один раз під час завантаження класу
    static {
        operations = 0;
    }

    private final long numerator;
    private final long denominator;

    /** Дріб numerator/denominator; одразу скорочується. */
    public Drib(long numerator, long denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("знаменник не може дорівнювати нулю");
        }
        if (denominator < 0) {              // знак зберігаємо в чисельнику
            numerator = -numerator;
            denominator = -denominator;
        }
        long g = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / g;
        this.denominator = denominator / g;
    }

    /** Ціле число як дріб n/1. */
    public Drib(long n) {
        this(n, 1);
    }

    /** Дріб із рядка "a/b" або "a". */
    public Drib(String s) {
        this(parse(s)[0], parse(s)[1]);
    }

    private static long[] parse(String s) {
        String[] p = s.trim().split("/");
        long n = Long.parseLong(p[0].trim());
        long d = p.length > 1 ? Long.parseLong(p[1].trim()) : 1;
        return new long[]{n, d};
    }

    /** Найбільший спільний дільник (алгоритм Евкліда). */
    public static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : a;
    }

    public static int getOperations() {
        return operations;
    }

    public long getNumerator() { return numerator; }
    public long getDenominator() { return denominator; }

    public Drib add(Drib o) {
        operations++;
        return new Drib(numerator * o.denominator + o.numerator * denominator,
                denominator * o.denominator);
    }

    public Drib subtract(Drib o) {
        operations++;
        return new Drib(numerator * o.denominator - o.numerator * denominator,
                denominator * o.denominator);
    }

    public Drib multiply(Drib o) {
        operations++;
        return new Drib(numerator * o.numerator, denominator * o.denominator);
    }

    public Drib divide(Drib o) {
        if (o.numerator == 0) {
            throw new ArithmeticException("ділення на нульовий дріб");
        }
        operations++;
        return new Drib(numerator * o.denominator, denominator * o.numerator);
    }

    /** Окрема операція скорочення: дріб уже зберігається скороченим, тож повертає копію. */
    public Drib reduce() {
        return new Drib(numerator, denominator);
    }

    public double toDouble() {
        return (double) numerator / denominator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Drib)) return false;
        Drib d = (Drib) obj;
        return numerator == d.numerator && denominator == d.denominator;  // обидва нескоротні
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(numerator) + Long.hashCode(denominator);
    }

    @Override
    public String toString() {
        return denominator == 1 ? String.valueOf(numerator) : numerator + "/" + denominator;
    }
}
