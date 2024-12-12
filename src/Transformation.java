import static java.lang.Character.*;

public class Transformation {
    String pre;
    ValidityChecks checks;
    Transformation(String pre) {
        this.pre = prepFormat(pre);
        checks = new ValidityChecks(this.pre);
    }
    boolean transform() {
//        if (!checks.checkValid()){
//            return null;
//        };
//        return pre;
        return checks.Valid();
    }
    private String prepFormat(String pre){
        //standardize the mathematical expressions,
        //no space and all lowercase
        StringBuilder br = new StringBuilder();
        for (int i = 0; i < pre.length(); i++) {
            if (isUpperCase(pre.charAt(i))) {
                br.append(toLowerCase(pre.charAt(i)));
            }else if (isSpaceChar(pre.charAt(i))) {
                continue;
            }else {
                br.append(pre.charAt(i));
            }
        }
        return br.toString();
    }
}
