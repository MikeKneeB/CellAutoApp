package com.cellularautomata.cellautoapp;

import com.cellularautomata.cellautoapp.internal.LifeGrid;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by mike on 10/03/17.
 */

public class LifeGridTest {

    private LifeGrid lifeGrid_1;

    @Before
    public void before(){
        HashSet<Integer> surviveList = new HashSet<Integer>();
        surviveList.add(2);
        surviveList.add(3);

        HashSet<Integer> createList = new HashSet<Integer>();
        createList.add(3);

        lifeGrid_1 = new LifeGrid(3,3,surviveList, createList);

        lifeGrid_1.printLists();
    }

    @Test
    public void LifeTest1() {
        lifeGrid_1.placeValue(0,0,1);
        lifeGrid_1.placeValue(1,0,1);
        lifeGrid_1.placeValue(2,0,1);

        assertTrue(lifeGrid_1.valAt(0,0) == 1);
        assertTrue(lifeGrid_1.valAt(1,0) == 1);
        assertTrue(lifeGrid_1.valAt(2,0) == 1);
        assertTrue(lifeGrid_1.valAt(1,1) == 0);
    }

    @Test
    public void LifeTest2() {
        lifeGrid_1.placeValue(0,0,1);
        lifeGrid_1.placeValue(1,0,1);
        lifeGrid_1.placeValue(2,0,1);

        lifeGrid_1.evolve();

        assertTrue(lifeGrid_1.valAt(1,1) == 1);
        assertTrue(lifeGrid_1.valAt(0,0) == 0);
        assertTrue(lifeGrid_1.valAt(0,1) == 0);
    }

    @Test
    public void LifeTest3() {
        lifeGrid_1.placeValue(0,0,1);
        lifeGrid_1.placeValue(1,0,1);
        lifeGrid_1.placeValue(2,0,1);

        lifeGrid_1.evolve();

        assertTrue(lifeGrid_1.valAt(1,1) == 1);
        assertTrue(lifeGrid_1.valAt(1,0) == 1);
        assertTrue(lifeGrid_1.valAt(0,1) == 0);

        lifeGrid_1.evolve();

        assertTrue(lifeGrid_1.valAt(1,0) == 0);
        assertTrue(lifeGrid_1.valAt(1,1) == 0);
    }
}
