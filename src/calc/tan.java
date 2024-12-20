package calc;

class tan extends OperationUnaire {
    public tan(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.tan(Math.toRadians(valeur));
    }
}
