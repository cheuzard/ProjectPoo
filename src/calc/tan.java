package calc;

class tan extends OperationUnaire {
    public tan(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        System.out.println(valeur);
        return Math.tan(Math.toRadians(valeur));
    }
}
