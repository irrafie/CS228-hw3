package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class OrderedStateTest {
    private State Adam;
    private int [][]adamboard=null;
    private int [][] left=null;
    private int [][] right=null;
    private int [][] up=null;
    private int [][] down=null;
    State LEFT=null;
    State RIGHT=null;
    State UP=null;
    State DOWN=null;
    private OrderedStateList OPEN;

    @Before
    public void Setup() {
        adamboard= new int [][]{{1,2,3},{8,0,4},{7,6,5}};
        Adam=new State(adamboard);
        left=new int[][] {{1,2,3},{8,4,0},{7,6,5}};
        right=new int[][] {{1,2,3},{0,8,4},{7,6,5}};
        up=new int[][] {{1,2,3},{8,6,4},{7,0,5}};
        down=new int[][] {{1,0,3},{8,2,4},{7,6,5}};
        LEFT=new State(left);
        RIGHT=new State(right);
        UP=new State(up);
        DOWN=new State(down);
        OPEN=new OrderedStateList(Heuristic.TileMismatch,true);

    }

    @Test
    public void Addstate() {
        //testing for successorState
        assertEquals(LEFT,Adam.successorState(Move.LEFT));
        assertEquals(RIGHT,Adam.successorState(Move.RIGHT));
        assertEquals(UP,Adam.successorState(Move.UP));
        assertEquals(DOWN,Adam.successorState(Move.DOWN));
        //adding to the linked list
        OPEN.addState(Adam);
        assertEquals(1,OPEN.size()); //should be one if not, check to make sure you increase size++;
        OPEN.addState(Adam.successorState(Move.LEFT));
        assertEquals(2,OPEN.size());

    }
    @Test(expected = IllegalStateException.class)
    public void Addstate_double() {
        //make sure you have IllegalStateException()
        OPEN.addState(Adam.successorState(Move.LEFT));
        OPEN.addState(Adam.successorState(Move.LEFT));

    }
    @Test(expected = IllegalStateException.class)
    public void Addstate_null() {
        OPEN.addState(null);
    }
    @Test
    public void Addstate_order() {
        OPEN.addState(Adam.successorState(Move.LEFT));
        OPEN.addState(Adam.successorState(Move.RIGHT));
        OPEN.addState(Adam.successorState(Move.UP));
        OPEN.addState(Adam.successorState(Move.DOWN));

        //they have the same cost, the last state added to the list
        //should be the first to come out and so on

        assertEquals(DOWN,OPEN.remove());
        assertEquals(UP,OPEN.remove());
        assertEquals(RIGHT,OPEN.remove());
        assertEquals(LEFT,OPEN.remove());


    }

    @Test
    public void findState() {
        assertEquals(null,OPEN.findState(DOWN));
        OPEN.addState(DOWN);
        assertEquals(DOWN,OPEN.findState(DOWN));
        OPEN.addState(Adam);
        assertEquals(Adam,OPEN.findState(Adam));
        OPEN.addState(UP);
        assertEquals(UP,OPEN.findState(UP));
        OPEN.addState(LEFT);
        assertEquals(LEFT,OPEN.findState(LEFT));
    }

    @Test(expected = IllegalStateException.class)
    public void Remove_state() {
        OPEN.addState(Adam.successorState(Move.LEFT));
        OPEN.addState(Adam.successorState(Move.RIGHT));
        OPEN.addState(Adam.successorState(Move.UP));
        OPEN.addState(Adam.successorState(Move.DOWN));
        OPEN.removeState(LEFT);
        OPEN.removeState(LEFT);

        OPEN.removeState(RIGHT);
        OPEN.removeState(RIGHT);

    }

    @Test(expected = IllegalStateException.class)
    public void Remove_state_Head() {
        OPEN.addState(Adam.successorState(Move.LEFT));
        OPEN.addState(Adam.successorState(Move.RIGHT));
        OPEN.addState(Adam.successorState(Move.UP));
        OPEN.addState(Adam.successorState(Move.DOWN));
        //the head is removed Down should not be in the list anymore
        assertEquals(DOWN,OPEN.remove());
        OPEN.remove();

        OPEN.removeState(DOWN);



    }








}
