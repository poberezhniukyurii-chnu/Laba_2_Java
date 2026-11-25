package task1;

/**
 * Точка на площині в декартових координатах.
 * Лабораторна робота №2, завдання 1 (варіант 3). Побережнюк Ю.В., група 241б.
 */
public class Point {
    /** Точність порівняння координат. */
    public static final double EPS = 1e-9;

    private static int created;      // скільки точок створено (статичне поле)

    private double x;
    private double y;

    // Блок ініціалізації: виконується при створенні кожного об'єкта перед тілом конструктора
    {
        created++;
    }

    /** Точка на початку координат. */
    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Конструктор копіювання. */
    public Point(Point other) {
        this(other.x, other.y);
    }

    /** Статичний фабричний метод: точка за полярними координатами (r, кут у градусах). */
    public static Point fromPolar(double r, double angleDegrees) {
        double phi = Math.toRadians(angleDegrees);
        return new Point(r * Math.cos(phi), r * Math.sin(phi));
    }

    public static int getCreated() {
        return created;
    }

    /** Статичний метод: відстань між двома точками. */
    public static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    /** Переміщення вздовж осі X. */
    public void moveX(double dx) {
        x += dx;
    }

    /** Переміщення вздовж осі Y. */
    public void moveY(double dy) {
        y += dy;
    }

    /** Відстань до початку координат. */
    public double distanceToOrigin() {
        return Math.hypot(x, y);
    }

    /** Відстань до іншої точки. */
    public double distanceTo(Point other) {
        return distance(this, other);
    }

    /** Полярний радіус r. */
    public double getRadius() {
        return distanceToOrigin();
    }

    /** Полярний кут φ у градусах у діапазоні [0; 360). */
    public double getAngleDegrees() {
        double deg = Math.toDegrees(Math.atan2(y, x));
        return deg < 0 ? deg + 360 : deg;
    }

    /** Перетворення в полярні координати: масив {r, φ°}. */
    public double[] toPolar() {
        return new double[]{getRadius(), getAngleDegrees()};
    }

    /** Порівняння на неспівпадання. */
    public boolean notEquals(Point other) {
        return !equals(other);
    }

    private static long key(double v) {
        return Math.round(v / EPS);   // координати порівнюються з точністю EPS
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;
        Point p = (Point) obj;
        return key(x) == key(p.x) && key(y) == key(p.y);
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(key(x)) + Long.hashCode(key(y));
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}
