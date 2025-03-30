package cm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldTest {

    private Field field;

    @BeforeEach
    void startField(){
        field = new Field(3, 3);
    }

    @Test
    void testNeighborOneFieldAway(){
        Field neighbor = new Field(3,4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void testNeighborTwoFieldsAway(){
        Field neighbor = new Field(2,4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }
    
    @Test
    void testNotNeighbor(){
        Field neighbor = new Field(2,5);
        boolean result = field.addNeighbor(neighbor);

        assertFalse(result);
    }
}
