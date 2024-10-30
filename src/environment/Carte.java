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
