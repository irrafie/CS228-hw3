package edu.iastate.cs228.hw3;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StateTest {
    private State[] s;
    private State s0;
    private State s1;
    private State s2;
    private State s3;
    private State s4;
    private State s5;
    private State s6;
    private State s7;
    private State s8;
    private State s9;
    private State s11; // goal state
    private static int[][] goal;
    private static int[][] board;
    private static int[][] board2;
    private static int[][] board3;
    private static int[][] board4;
    private static int[][] board5;
    private static int[][] board6;
    private static int[][] board7;
    private static int[][] board8;
    private static int[][] illegal;
    private static int[][] illegal2;
    private static int[][] illegal3;

    @BeforeClass
    public static void setUpBeforeClass() {
        goal = new int[][] {{1,2,3},{8,0,4},{7,6,5}};
        illegal = new int[][] {{2,3,4},{5,0,2},{}}; // repeated value
        illegal2 = new int[][] {{5,7},{1,0},{2,4},{6,3}}; // illegal size
        illegal3 = new int[][] {{3,4,1},{6,7,9},{-2,5,0}}; // value > 8
        board = new int[][] {{4,1,2},{5,3,0},{8,6,7}};   //s0
        board2 = new int[][] {{2,0,3},{1,8,4},{7,6,5}};
        board3 = new int[][] {{5,2,4},{7,6,0},{3,1,8}};
        board4 = new int[][] {{4,1,2},{3,5,0},{8,6,7}};  //s3
        board5 = new int[][] {{2,3,5},{1,8,4},{0,6,7}};
        board6 = new int[][] {{2,5,3},{1,8,4},{0,6,7}};
        board7 = new int[][] {{0,2,3},{1,8,4},{7,6,5}};
        board8 = new int[][] {{2,8,3},{1,0,4},{7,6,5}};
    }

    @Before
    public void setUp() throws Exception {
        s0 = new State(board);
        s1 = new State(board2);
        s2 = new State(board3);
        s3 = new State(board4);
        s4 = new State(board5);
        s5 = new State(board6);
        s6 = new State(board7);
        s7 = new State(board8);
        s11 = new State(goal);
        s = new State[] {s0,s1,s2,s3,s4,s5,s6,s7};
    }

    @Test
    public void testState() {
        State s0 = new State(goal);
        assertEquals(s0.numMoves, 0);
        assertEquals(s0.board.length, 3);
        assertEquals(s0.board[0].length, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testState1() {
        s0 = new State(illegal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testState2() {
        s0 = new State(illegal2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testState3() {
        s0 = new State(illegal3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testState4() {
        int[][] b = new int[0][0]; // Just for laughs
        s0 = new State(b);
    }

    @Test
    public void testSolvable() {
        assertEquals(s0.solvable(), false);
        assertEquals(s1.solvable(), true);
        assertEquals(s5.solvable(), false);
        assertEquals(s2.solvable(), true);
        assertEquals(s4.solvable(), true);
        assertEquals(s11.solvable(), true);
    }

    @Test
    public void testIsGoalState() {
        for (State i : s) {
            assertEquals(i.isGoalState(), false);
        }
        assertEquals(s11.isGoalState(), true);
    }

    @Test
    public void testToString() {
        // Strings could be different depending on your implementation.
        String str0 = "4 1 2\n5 3  \n8 6 7\n";
        String str1 = "2   3\n1 8 4\n7 6 5\n";
        String str2 = "5 2 4\n7 6  \n3 1 8\n";
        String str3 = "4 1 2\n3 5  \n8 6 7\n";
        String str4 = "2 3 5\n1 8 4\n  6 7\n";
        String str5 = "2 5 3\n1 8 4\n  6 7\n";
        assertEquals(s0.toString(), str0);
        assertEquals(s1.toString(), str1);
        assertEquals(s2.toString(), str2);
        assertEquals(s3.toString(), str3);
        assertEquals(s4.toString(), str4);
        assertEquals(s5.toString(), str5);
    }

    @Test
    public void testClone() {
        for (State i : s) {
            String iString = i.toString();
            State temp = (State) i.clone();
            assertEquals(i.toString(), temp.toString());

            assertEquals(temp.next, null);
            assertEquals(temp.previous, null);
            temp.board[0][0] = 10;
            assertEquals(iString, i.toString());
        }
    }

    @Test
    public void testEquals() {
        // Tests, clone and equals
        for (State i : s) {
            State temp = (State) i.clone();
            assertEquals(i.equals(temp), true);
        }

        // False
        for (int i = 0; i < s.length - 1; i++) {
            State temp = (State) s[i + 1].clone();
            assertEquals(s[i].equals(temp), false);
        }
        State temp = new State(goal);
        assertEquals(s11.equals(temp), true);
    }

    @Test
    public void testCompareTo() {
        set(Heuristic.ManhattanDist);
        // Test each state with itself
        for (State i : s) {
            assertEquals(i.compareTo(i), 0);
        }

        // Test Largest state (state with the highest cost) with other states
        for (State i : s) {
            if (i.equals(s2)) { continue; }
            assertEquals(i.compareTo(s2), -1);
            assertEquals(s2.compareTo(i), 1);
        }

        // Test smallest state (state with the lowest cost) with other states
        for (State i : s) {
            if (i.equals(s6)) { continue; }
            assertEquals(i.compareTo(s6), 1);
        }

        // Random tests
        assertEquals(s3.compareTo(s4), 1);
        assertEquals(s1.compareTo(s2), -1);
        //assertEquals(s0.compareTo(s3), 0);
        assertEquals(s7.compareTo(s3), -1);
        assertEquals(s5.compareTo(s4), 0);
        assertEquals(s2.compareTo(s6), 1);
        assertEquals(s4.compareTo(s1), 1);
    }

    @Test
    public void testCompareTo2() {
        set(Heuristic.TileMismatch);

        // Test each state with itself
        for (State i : s) {
            assertEquals(i.compareTo(i), 0);
        }

        // Test smallest state (state with the lowest cost) with other states
        for (State i : s) {
            if (i.equals(s6)) { continue; }
            assertEquals(i.compareTo(s6), 1);
            assertEquals(s6.compareTo(i), -1);
        }

        // Random tests
        assertEquals(s3.compareTo(s4), 1);
        assertEquals(s1.compareTo(s2), -1);
        assertEquals(s0.compareTo(s3), 0);
        assertEquals(s7.compareTo(s3), -1);
        assertEquals(s5.compareTo(s4), -1);
        assertEquals(s2.compareTo(s6), 1);
        assertEquals(s4.compareTo(s1), 1);

    }

    @Test
    public void testComputeNumMismatchedTiles() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        set(Heuristic.TileMismatch);
        Method method = State.class.getDeclaredMethod("computeNumMismatchedTiles");
        method.setAccessible(true);

        // multiple calls to the same board should give the same value
        assertEquals(method.invoke(s0), 7);
        assertEquals(method.invoke(s0), 7);
        assertEquals(method.invoke(s1), 3);
        assertEquals(method.invoke(s2), 7);
        assertEquals(method.invoke(s2), 7);
        assertEquals(method.invoke(s3), 7);
        assertEquals(method.invoke(s4), 6);
        assertEquals(method.invoke(s5), 5);
        assertEquals(method.invoke(s6), 2);
        assertEquals(method.invoke(s6), 2);
        assertEquals(method.invoke(s7), 3);
        assertEquals(method.invoke(s7), 3);
        assertEquals(method.invoke(s7), 3);
        assertEquals(method.invoke(s11), 0);
    }

    @Test
    public void testCost() {
        set(Heuristic.ManhattanDist);
        assertEquals(s11.cost(), 0);
        assertEquals(s0.cost(), 13);
        assertEquals(s1.cost(), 3);
        assertEquals(s2.cost(), 17);
        assertEquals(s3.cost(), 13);
        assertEquals(s4.cost(), 8);
        assertEquals(s5.cost(), 8);
        assertEquals(s6.cost(), 2);
    }

    @Test
    public void testCost2() {
        set(Heuristic.TileMismatch);
        assertEquals(s11.cost(), 0);
        assertEquals(s0.cost(), 7);
        assertEquals(s1.cost(), 3);
        assertEquals(s2.cost(), 7);
        assertEquals(s2.cost(), 7);
        assertEquals(s2.cost(), 7);
        assertEquals(s3.cost(), 7);
        assertEquals(s4.cost(), 6);
        assertEquals(s5.cost(), 5);
        assertEquals(s5.cost(), 5);
        assertEquals(s5.cost(), 5); // the same state should have the same cost
        assertEquals(s6.cost(), 2);
        assertEquals(s7.cost(), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCost3(){
        // cost() should throw IllegalArgumentException if heuristic is neither 0 nor 1.
        // set(null);
        s0.compareTo(s2);
    }

    @Test
    public void testManhattanDistance() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        set(Heuristic.ManhattanDist);
        Method method = State.class.getDeclaredMethod("computeManhattanDistance");
        method.setAccessible(true);
        assertEquals(method.invoke(s0), 13);
        assertEquals(method.invoke(s0), 13);
        assertEquals(method.invoke(s1), 3);
        assertEquals(method.invoke(s2), 17);
        assertEquals(method.invoke(s2), 17);
        assertEquals(method.invoke(s3), 13);
        assertEquals(method.invoke(s4), 8);
        assertEquals(method.invoke(s5), 8);
        assertEquals(method.invoke(s6), 2);
        assertEquals(method.invoke(s6), 2);
        assertEquals(method.invoke(s7), 4);
        assertEquals(method.invoke(s7), 4); // multiple calls to the same board should give the same value
        assertEquals(method.invoke(s7), 4);
        assertEquals(method.invoke(s11), 0);
    }

    @Test
    public void testSuccessorState() {
        int[][] b1 = new int[][] {{4,1,0},{5,3,2},{8,6,7}}; // Down
        int[][] b2 = new int[][] {{4,1,2},{5,0,3},{8,6,7}}; // Right
        int[][] b3 = new int[][] {{4,1,2},{5,3,7},{8,6,0}}; // Up
        s8 = s0.successorState(Move.DOWN);
        s9 = new State(b1);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s0.predecessor.equals(s0), true);
        assertEquals(s8.predecessor.equals(s0), true);

        s8 = s0.successorState(Move.RIGHT);
        s9 = new State(b2);
        System.out.println(s8.toString());
        System.out.println(s9.toString());

        assertEquals(s9.equals(s8), true);
        //assertEquals(s0.predecessor.equals(s0), true);
        assertEquals(s8.predecessor.equals(s0), true);

        s8 = s0.successorState(Move.UP);
        s9 = new State(b3);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s0.predecessor.equals(s0), true);
        assertEquals(s8.predecessor.equals(s0), true);
    }

    @Test
    public void testSuccessorState2() {
        int[][] b1 = new int[][] {{2,8,3},{1,0,4},{7,6,5}}; // Up
        int[][] b2 = new int[][] {{0,2,3},{1,8,4},{7,6,5}}; // Right
        int[][] b3 = new int[][] {{2,3,0},{1,8,4},{7,6,5}}; // Left

        s8 = s1.successorState(Move.RIGHT);
        s9 = new State(b2);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s1.predecessor.equals(s1), true);
        assertEquals(s8.predecessor.equals(s1), true);

        s8 = s1.successorState(Move.UP);
        s9 = new State(b1);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s1.predecessor.equals(s1), true);
        assertEquals(s8.predecessor.equals(s1), true);

        s8 = s1.successorState(Move.LEFT);
        s9 = new State(b3);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s1.predecessor.equals(s1), true);
        assertEquals(s8.predecessor.equals(s1), true);
    }

    @Test
    public void testSuccessorState3() {
        int[][] b1 = new int[][] {{2,3,5},{0,8,4},{1,6,7}}; // Down
        int[][] b2 = new int[][] {{2,3,5},{1,8,4},{6,0,7}}; // Left
        s8 = s4.successorState(Move.DOWN);
        s9 = new State(b1);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s4.predecessor.equals(s4), true);
        assertEquals(s8.predecessor.equals(s4), true);

        s8 = s4.successorState(Move.LEFT);
        s9 = new State(b2);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s4.predecessor.equals(s4), true);
        assertEquals(s8.predecessor.equals(s4), true);
    }

    @Test
    public void testSuccesorState4() {
        int[][] b1 = new int[][] {{2,0,3},{1,8,4},{7,6,5}}; // Down
        int[][] b2 = new int[][] {{2,8,3},{1,6,4},{7,0,5}}; // Up
        int[][] b3 = new int[][] {{2,8,3},{1,4,0},{7,6,5}}; // Left
        int[][] b4 = new int[][] {{2,8,3},{0,1,4},{7,6,5}}; // Right

        s8 = s7.successorState(Move.DOWN);
        s9 = new State(b1);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s7.predecessor.equals(s7), true);
        assertEquals(s8.predecessor.equals(s7), true);

        s8 = s7.successorState(Move.RIGHT);
        s9 = new State(b4);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s7.predecessor.equals(s7), true);
        assertEquals(s8.predecessor.equals(s7), true);

        s8 = s7.successorState(Move.UP);
        s9 = new State(b2);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s7.predecessor.equals(s7), true);
        assertEquals(s8.predecessor.equals(s7), true);

        s8 = s7.successorState(Move.LEFT);
        s9 = new State(b3);
        assertEquals(s9.equals(s8), true);
        //assertEquals(s7.predecessor.equals(s7), true);
        assertEquals(s8.predecessor.equals(s7), true);
    }

    @After
    public void reset() {
        set(null);
    }

    public void set(Heuristic h) {
        for (State i : s) {
            i.heu = h;
        }
    }
}