package object;

import java.util.*;

public class Test { //todo delete this fucking shit
    public static void main(String[] args) {
        Map<Integer, String> cc = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            cc.put(i, "cc".repeat(i));
        }
        List<Integer> ccc = new ArrayList<>(cc.keySet());
        System.out.println(ccc.get(3));
    }
}
