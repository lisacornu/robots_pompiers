package environment;

public class Case {
    private final int x;
    private final int y;
    private final NatureTerrain natureTerrain;
    private boolean isOnFire;



    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public Case(boolean isOnFire, NatureTerrain natureTerrain, int y, int x) {
        this.isOnFire = isOnFire;
        this.natureTerrain = natureTerrain;
        this.y = y;
        this.x = x;
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
