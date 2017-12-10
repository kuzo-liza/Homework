import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

public class Example {

    @Test
    public void testing() {
        List<Integer> inputListTest1 = Arrays.asList(16, 23, 45, 74, -51, 8, -3, 29);
        List<Integer> outputListTest1 = Arrays.asList(16, 23, 45, 74);

        List<Integer> inputListTest2 = Arrays.asList(10, 25, -5, 70, 80);
        List<Integer> outputListTest2 = Arrays.asList(10, 25, -5, 70, 80);

        assertEquals(outputListTest1, MaxSubArray.search(inputListTest1));
        assertEquals(outputListTest2, MaxSubArray.search(inputListTest2));
    }
}
