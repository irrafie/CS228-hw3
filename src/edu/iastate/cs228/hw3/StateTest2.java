package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.FileNotFoundException;

public class StateTest2 {
    private int[][] finalC;
    private int[][] initial;
    private State initialState;
    private State finalState;
    private State tempo;

    public StateTest2() {
        initial = new int[][]{{2,3,1},{8,0,4},{7,6,5}};
        int[][] temp = new int[][]{{2,0,1}, {8,3,4}, {7,6,5}};
        finalC = new int[][]{{1,2,3},{8,0,4},{7,6,5}};
        initialState = new State(initial);
        finalState = new State(finalC);
        tempo = new State(temp);
    }

    @Test
    public void testSolvable() {
        assertTrue(initialState.solvable());
    }

    @Test
    public void testClone() {
        System.out.println(initialState.toString());
        State copy = (State) initialState.clone();
        System.out.println(copy.toString());
    }

    @Test
    public void testEquals() {
        assertFalse(initialState.equals(finalState));
    }

    @Test
    public void testIsGoalState() {
        assertFalse(initialState.isGoalState());
    }

    @Test
    public void testSuccessor(){
        assertEquals(initialState.successorState(Move.DOWN),tempo);
        assertEquals(initialState.predecessor, initialState);
    }

    @Test
    public void testNew(){
        int[][] b1 = new int[][] {{1,2,3},{4,5,6},{7,8,0}};
        int[][] b2 = new int[][] {{4,1,0},{5,3,2},{8,6,7}};
        State a = new State(b1);
        State b = new State(b2);
        System.out.println(a.toString());
        System.out.println(a.successorState(Move.RIGHT));
        System.out.println(a.successorState(Move.UP));
        System.out.println(a.successorState(Move.LEFT));
        System.out.println(a.predecessor.toString());
        //System.out.println(b.toString());
        System.out.println(a.equals(b));
    }

    @Test
    public void manhat() throws FileNotFoundException {
        State a= new State("test.txt");
        int[][] b1 = new int[][] {{1,2,3},{4,5,6},{7,8,0}};
        a.heu = Heuristic.ManhattanDist;
        System.out.println(a.cost());
        a.heu = Heuristic.TileMismatch;
        System.out.println(a.cost());
    }
}