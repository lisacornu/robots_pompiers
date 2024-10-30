package environment;

public class Incendie {
    private Case position;
    private int intensite; // nb de litre nécéssaire pour éteindre

    public Incendie(Case position, int intensite) {
        this.position = position;
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
