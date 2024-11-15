package environment;

public class Incendie {
    private Case position;
    private int intensite; // nombre de litre nécéssaire pour éteindre l'incendie

    public Incendie(Case position, int intensite) {
        this.position = position;
        this.intensite = intensite;
    }

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public int getIntensite() {
        return intensite;
    }

    public void setIntensite(int intensite) {
        this.intensite = intensite;
    }

    @Override
    public String toString() {
        return "Incendie{" +
                "position=" + position +
                ", intensite=" + intensite +
                '}';
    }
}
