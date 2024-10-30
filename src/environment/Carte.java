package environment;

public class Carte {
    private final int tailleCases;
    private final int nblignes;
    private final int nbCol;
    private final Case [][] matriceCase;

    public Carte(int tailleCases, int nbLignes, int nbCol) {
        this.tailleCases = tailleCases;
        this.nblignes = nbLignes;
        this.nbCol = nbCol;
        this.matriceCase = new Case[nbLignes][nbCol];
    }

    public int getTailleCases() {
        return tailleCases;
    }

    public int getNblignes() {
        return nblignes;
    }

    public int getNbCol() {
        return nbCol;
    }

    public Case[][] getMatriceCase() {
        return matriceCase;
    }




    public Case getVoisin(Case src, Direction direction) {
        return switch (direction) {
            case NORD -> this.getCase(src.getX(), (Math.min(src.getY() + 1, this.getNbCol())));
            case SUD -> this.getCase(src.getX(), Math.max(src.getY() - 1, 0));
            case EST -> this.getCase(Math.min(src.getX() + 1, this.getNblignes()), src.getY());
            case OUEST -> this.getCase(Math.max(src.getX() - 1, 0), src.getY());
        };
    }

    public void setCaseMatrice (int lig, int col, NatureTerrain nature) {
        this.matriceCase[lig][col] = new Case(lig, col, nature);
    }

    public Case getCase (int lig, int col) {
        return this.matriceCase[lig][col];
    }

    public void printMatriceCase () {
        for (int i = 0; i<this.nblignes; i++) {
            for (Case c : this.matriceCase[i]) {
                System.out.println(c);
            }
        }
    }


}
