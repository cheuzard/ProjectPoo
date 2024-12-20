package calc;

class Sqrt extends OperationUnaire {
    public Sqrt(double valeur)  throws ArithmeticException {
        super(valeur);
    }

    @Override
    public double Calculer()
    {
        if (valeur < 0) throw new ArithmeticException("no negative numbers in sqrt ");
        return Math.sqrt(valeur);
    }
}
