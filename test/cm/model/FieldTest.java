package cm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cm.controller.ExplosionException;

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

    @Test
    void testDefaultValueFlaggedAttribute(){
        assertFalse(field.isFlagged());
    }

    @Test
    void testSwitchFlag(){
        field.switchFlag();
        assertTrue(field.isFlagged());
    }

    @Test
    void testSwitchFlagTwoTimes(){
        field.switchFlag();
        field.switchFlag();
        assertFalse(field.isFlagged());
    }

    @Test
    void testClearUnminedUnflagged(){
        assertTrue(field.clear());
    }

    @Test
    void testClearUnminedFlagged(){
        field.switchFlag();
        assertFalse(field.clear());
    }

    @Test
    void testClearMinedFlagged(){
        field.switchFlag();
        field.mine();
        assertFalse(field.clear());
    }

    @Test
    void testClearMinedUnflagged(){
        field.mine();

        assertThrows(ExplosionException.class, () -> {
            field.clear();
        });
    }

    @Test
    void testClearWithNeighbors(){
        Field field11 = new Field(1,1);
        Field field12 = new Field(1,2);
        field12.mine();

        Field field22 = new Field(2,2);
        field22.addNeighbor(field11);
        field22.addNeighbor(field12);

        field.addNeighbor(field22);
        field.clear();

        assertTrue(field22.isClear() && !field11.isClear());
    }

}
