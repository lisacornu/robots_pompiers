package environment;

public class Case {
    private final int x;
    private final int y;
    private final NatureTerrain natureTerrain;

    public Case(int x, int y, NatureTerrain natureTerrain) {
        this.x = x;
        this.y = y;
        this.natureTerrain = natureTerrain;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public NatureTerrain getNatureTerrain() {
        return natureTerrain;
    }

    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                ", natureTerrain=" + natureTerrain +
                '}';
    }
}
