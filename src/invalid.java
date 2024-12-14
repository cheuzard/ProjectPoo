public class invalid extends RuntimeException {
    public invalid() {}
    @Override
    public String getMessage() {
        return "mathematical expression is invalid";
    }
}
