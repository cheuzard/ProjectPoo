class Log extends OperationUnaire {
    public Log(double valeur) {
        super(valeur);
    }

    @Override
    public double Calculer() {
        //to get log base 10
        return Math.log(valeur) / Math.log(10);
    }
}
