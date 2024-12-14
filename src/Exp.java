class Exp extends OperationUnaire {
    public Exp(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.exp(valeur);
    }
}
