package environment;

import java.lang.Math;

public class Case {
    private final int x;
    private final int y;
    private final NatureTerrain natureTerrain;
    private boolean isOnFire;
    private String imagePath;


    public Case(NatureTerrain natureTerrain, int y, int x) {
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

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public NatureTerrain getNatureTerrain() {
        return natureTerrain;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath() {
        String imagePath = "images/";
        switch (this.getNatureTerrain()) {
            case EAU -> imagePath += "water" + randInt(5) + ".png";
            case ROCHE -> imagePath += "moutain" + randInt(5) + ".png";
            case TERRAIN_LIBRE -> imagePath += "plain" + randInt(5) + ".png";
            case FORET -> imagePath += "forest" + randInt(5) + ".png";
            case HABITAT -> imagePath += "city" + randInt(5) + ".png";
        }
        this.imagePath = imagePath;
    }


    /**
     * @param max maximum du nombre aléatoire à tirer
     * @return random int entre 0 et max
     */
    private int randInt(int max){
        return (int)(Math.random()*(max+1));
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
