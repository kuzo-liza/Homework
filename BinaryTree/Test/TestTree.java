import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTree {
    @Test
    public void testing() {
        BinaryTree<Integer> test = new BinaryTree<>();
        test.add(8);
        test.add(2);
        test.add(10);
        test.add(9);
        test.add(7);
        test.add(3);
        test.add(1);
        test.add(5);
        test.add(4);
        test.add(6);
        test.remove(3);
        assertFalse(test.contains(3));
        assertEquals(9, test.size());

        Iterator<Integer> iterator = test.iterator();
        assertEquals(1, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(7, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(9, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
    }
}
