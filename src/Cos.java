class Cos extends OperationUnaire {
    public Cos(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.cos(valeur * (Math.PI / 180));
    }
}
