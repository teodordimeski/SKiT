import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Lab1Test {
    
    @Test
    void testHappyPath() {
        List<String> list1 = Arrays.asList("Computer", "PHONE", "MOUSE", "remote");
        List<String> list2 = Arrays.asList("Computer", "MONITOR", "Remote");
        List<String> expected = Arrays.asList("Computer", "remote");

        assertEquals(expected, Lab1.findCommonIgnoreCase(list1, list2));
    }

    @Test
    void testFirstListEmpty() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = Arrays.asList("HUB", "Cable", "adapter");

        assertTrue(Lab1.findCommonIgnoreCase(list1, list2).isEmpty());
    }

    @Test
    void testSecoundListEmpty() {
        List<String> list1 = Arrays.asList("HUB", "Cable", "adapter");
        List<String> list2 = new ArrayList<>();

        assertTrue(Lab1.findCommonIgnoreCase(list1, list2).isEmpty());
    }

    @Test
    void testNoCommonElements() {
        List<String> list1 = Arrays.asList("TV", "remote", "Hub");
        List<String> list2 = Arrays.asList("phone", "charger", "adapter", "COMPUTER");

        assertTrue(Lab1.findCommonIgnoreCase(list1, list2).isEmpty());
    }
}