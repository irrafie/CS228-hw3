package edu.iastate.cs228.hw3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *  
 * @author	Irfan Farhan Mohamad Rafie
 *
 */

/**
 * This class describes a circular doubly-lisnked list of states to represent both the OPEN and CLOSED lists
 * used by the A* algorithm.  The states on the list are sorted in the
 * 
 *     a) order of non-decreasing cost estimate for the state if the list is OPEN, or 
 *     b) lexicographic order of the state if the list is CLOSED.  
 * 
 */
public class OrderedStateList 
{

	/**
	 * Implementation of a circular doubly-linked list with a dummy head node.
	 */
	  private State head;           // dummy node as the head of the sorted linked list 
	  private int size = 0;

	  private boolean isOPEN;       // true if this OrderedStateList object is the list OPEN and false
	                                // if the list CLOSED.

	  /**
	   *  Default constructor constructs an empty list. Initialize heuristic. Set the fields next and 
	   *  previous of head to the node itself. Initialize instance variables size and heuristic. 
	   * 
	   * @param h 
	   * @param isOpen   
	   */
	  public OrderedStateList(Heuristic h, boolean isOpen)
	  {
		  State.heu = h;   // initialize heuristic used for evaluating all State objects.
		  //head.next = head;
		  //head.previous = head;
		  size = 0;
		  isOPEN = isOpen;
	  }

	  
	  public int size()
	  {
		  return size; 
	  }
	  
	  
	  /**
	   * A new state is added to the sorted list.  Traverse the list starting at head.  Stop 
	   * right before the first state t such that compare(s, t) <= 0, and add s before t. If 
	   * no such state exists, simply add s to the end of the list. 
	   * 
	   * Precondition: s does not appear on the sorted list. 
	   * 
	   * @param s
	   */
	  public void addState(State s)
	  {
            State current = head;
            State behind = null;

            while(current != null && current.compareTo(s) < 0){
                behind = current;
                current = current.next;
            }

            State net = s;
            net.next = current;
            net.previous = behind;

            if(behind == null){
                head = s;
            }

            else{
                behind.next = s;
                s.previous = behind;
            }

            if(current != null){
                current.previous = s;
                s.next = current;

            }

	  }
	  
	  
	  /**
	   * Conduct a sequential search on the list for a state that has the same board configuration 
	   * as the argument state s.  
	   * 
	   * Calls compareStates(). 
	   * 
	   * @param s
	   * @return the state on the list if found
	   *         null if not found 
	   */
	  public State findState(State s)
	  {
	      State temp = head;
	      while(temp.next != null){
	          if(temp.compareTo(s) == 0){
	              return temp;
              }
              else
              {
                  temp = temp.next;
              }
          }
		  return null; 
	  }
	  
	  
	  /**
	   * Remove the argument state s from the list.  It is used by the A* algorithm in maintaining 
	   * both the OPEN and CLOSED lists. 
	   * 
	   * @param s
	   * @throws IllegalStateException if s is not on the list 
	   */
	  public void removeState(State s) throws IllegalStateException
	  {
          State current = findState(s);
          if(current == null){
              throw new IllegalStateException();
          }

          State front = current.next;
          State behind = current.previous;

          if(behind == null){
              this.head = current.next;
              this.head.previous = null;
          }

          if(behind != null || front != null){
              State temp = current.previous;
              temp.next = current.next;
              temp = current.next;
              temp.previous = current.previous;
          }

	  }
	  
	  
	  /**
	   * Remove the first state on the list and return it.  This is used by the A* algorithm in maintaining
	   * the OPEN list.
	   *
       */
	  public State remove()
	  {
	      if(head == null){
	          return null;
          }

	      State tempo = head;

	      head = head.next;
	      head.previous = null;

	      return tempo;
	  }
	  
	  
	  /**
	   * Compare two states depending on whether this OrderedStateList object is the list OPEN 
	   * or the list CLOSE used by the A* algorithm.  More specifically,  
	   * 
	   *     a) call the method compareTo() of the State if isOPEN == true, or 
	   *     b) create a StateComparator object to call its compare() method if isOPEN == false. 
	   * 
	   * @param s1
	   * @param s2
	   * @return
	   */
	  private int compareStates(State s1, State s2)
	  {
          if(isOPEN){
              return s1.compareTo(s2);
          }

          else{
              StateComparator comp = new StateComparator();
              return comp.compare(s1,s2);
          }
	  }
}
