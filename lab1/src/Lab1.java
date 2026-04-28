import java.util.ArrayList;
import java.util.List;

public class Lab1 {
    public static List<String> findCommonIgnoreCase(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();


        if (list1 == null || list2 == null) {
            return null;
        }

        for (String s1 : list1) {
            for (String s2 : list2) {

                if (s1.equalsIgnoreCase(s2)) {

                    if (!result.contains(s1.toLowerCase())) {
                        result.add(s1);
                    }
                }
            }
        }
        return result;
    }
}