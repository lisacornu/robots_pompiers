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



    public void setCaseMatrice (int lig, int col, NatureTerrain nature) {
        this.matriceCase[lig][col] = new Case(nature,lig, col);
    }

    public Case getCase (int lig, int col) {
        return this.matriceCase[lig][col];
    }

    public boolean voisinExiste (Case src, Direction dir) {
        boolean voisinEx = false;
        switch (dir) {
            case EST    -> voisinEx = src.getX() < this.nbCol - 1;
            case OUEST  -> voisinEx = src.getX() > 0;
            case NORD   -> voisinEx = src.getY() > 0;
            case SUD    -> voisinEx = src.getY() < this.nblignes - 1;
        }
        return voisinEx;
    }

    public Case getVoisin (Case src, Direction dir) {
        if (!voisinExiste(src, dir))
            throw new IllegalArgumentException("La case direction " + dir + " de la case (" + src.getX() + "," + src.getY() + ") n'existe pas.");
        switch (dir) {
            case EST    -> { return this.getCase(src.getY(), src.getX() + 1);   }
            case OUEST  -> { return this.getCase(src.getY(), src.getX() - 1);   }
            case NORD   -> { return this.getCase(src.getY() - 1, src.getX());   }
            case SUD    -> { return this.getCase(src.getY() + 1, src.getX());}
            default     -> throw new IllegalArgumentException("Impossible de trouver le voisin.");
        }
    }

    public void printMatriceCase () {
        for (int i = 0; i<this.nblignes; i++) {
            for (Case c : this.matriceCase[i]) {
                System.out.println(c);
            }
        }
    }


}
