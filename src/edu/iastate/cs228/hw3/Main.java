/**
 * @author  Irfan Farhan Mohamad Rafie
 */

package edu.iastate.cs228.hw3;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        int[][] temp = new int[3][3];
        temp[0][0] = 1;
        temp[0][1] = 2;
        temp[0][2] = 3;
        temp[1][0] = 8;
        temp[1][1] = 0;
        temp[1][2] = 4;
        temp[2][0] = 7;
        temp[2][1] = 6;
        temp[2][2] = 5;



        State test = new State(temp);
        System.out.println(test.toString());
        System.out.println(test.isGoalState());
    }
}
