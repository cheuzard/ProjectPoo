class Log extends OperationUnaire {
    public Log(double valeur) throws ArithmeticException {
        super(valeur);
    }

    @Override
    public double Calculer() {
        if (valeur <= 0) throw new ArithmeticException("no 0 or negative number in log ");
        //to get log base 10
        return Math.log(valeur) / Math.log(10);
    }
}
