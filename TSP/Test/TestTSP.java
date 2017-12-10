import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTSP {

    @Test
    public void testing() {

        TSP.MakeGraphDistances(1);
        assertEquals(9, TSP.result());

        TSP.MakeGraphDistances(2);
        assertEquals(18, TSP.result());

        TSP.MakeGraphDistances(3);
        assertEquals(27, TSP.result());

        TSP.MakeGraphDistances(4);
        assertEquals(36, TSP.result());

        TSP.MakeGraphDistances(5);
        assertEquals(45, TSP.result());

        TSP.MakeGraphDistances(6);
        assertEquals(54, TSP.result());

        TSP.MakeGraphDistances(7);
        assertEquals(63, TSP.result());

        TSP.MakeGraphDistances(8);
        assertEquals(72, TSP.result());

        TSP.MakeGraphDistances(9);
        assertEquals(81, TSP.result());
    }
}
