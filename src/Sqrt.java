class Sqrt extends OperationUnaire {
    public Sqrt(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.sqrt(valeur);
    }
}
