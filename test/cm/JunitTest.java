package cm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JunitTest {

    @Test
    void testIfEqualsTwo() {
        int a = 1 + 1;
        assertEquals(2, a);
    }
    @Test
    void testIfEqualsThree() {
        int a = 2 + 10 - 9;
        assertEquals(3, a);
    }
}
