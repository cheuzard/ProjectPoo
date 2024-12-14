class Division extends OperationBinaire {
    public Division(double valeur1, double valeur2) {
        super(valeur1, valeur2);
    }

    @Override
    public double Calculer() {
        if (valeur2 == 0) {
            throw new ArithmeticException("Division par zéro impossible");
        }
        return valeur1 / valeur2;
    }
}
