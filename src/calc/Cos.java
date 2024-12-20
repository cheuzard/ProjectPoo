package calc;

class Cos extends OperationUnaire {
    public Cos(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        return Math.cos(Math.toRadians(valeur));
    }
}
