package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a mathematical set of integers backed by an ArrayList.
 * 
 * <p>Invariants:</p>
 * <ul>
 *   <li>No duplicate integers are stored.</li>
 *   <li>All mutator operations modify this instance (no new objects returned).</li>
 * </ul>
 * 
 * <p>This class supports standard set operations such as union, intersection,
 * difference, and complement. It disallows duplicates and throws an exception
 * when operations requiring elements (like {@code largest()} or {@code smallest()})
 * are invoked on an empty set.</p>
 */
public class IntegerSet  {
  /** Internal storage for set elements. */
  private List<Integer> set = new ArrayList<Integer>();

  /**
   * Clears all elements from the set.
   */
  public void clear() {
    set.clear();
  }

  /**
   * Returns the number of elements in the set.
   * 
   * @return the number of elements currently in the set
   */
  public int length() {
    return set.size();
  }

  /**
   * Checks whether this set and another object are equal.
   * Two sets are equal if they contain all of the same values, regardless of order.
   * 
   * @param o the object to compare
   * @return {@code true} if the sets contain the same elements, {@code false} otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntegerSet)) return false;
    IntegerSet other = (IntegerSet) o;
    return this.set.size() == other.set.size()
        && this.set.containsAll(other.set)
        && other.set.containsAll(this.set);
  }

  /**
   * Checks if a specific value exists in the set.
   * 
   * @param value the integer to check
   * @return {@code true} if the value exists, {@code false} otherwise
   */
  public boolean contains(int value) {
    return set.contains(value);
  }

  /**
   * Returns the largest value in the set.
   * 
   * @return the largest integer in the set
   * @throws IllegalStateException if the set is empty
   */
  public int largest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("IntegerSet is empty");
    }
    int max = set.get(0);
    for (int v : set) {
      if (v > max) max = v;
    }
    return max;
  }

  /**
   * Returns the smallest value in the set.
   * 
   * @return the smallest integer in the set
   * @throws IllegalStateException if the set is empty
   */
  public int smallest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("IntegerSet is empty");
    }
    int min = set.get(0);
    for (int v : set) {
      if (v < min) min = v;
    }
    return min;
  }

  /**
   * Adds an integer to the set if it is not already present.
   * 
   * @param item the integer to add
   */
  public void add(int item) {
    if (!set.contains(item)) {
      set.add(item);
    }
  }

  /**
   * Removes an integer from the set if it exists.
   * 
   * @param item the integer to remove
   */
  public void remove(int item) {
    set.remove(Integer.valueOf(item));
  }

  /**
   * Performs the union operation with another set.
   * After execution, this set contains all unique elements that are
   * in either this set or the other set.
   * 
   * @param other the other set to union with
   */
  public void union(IntegerSet other) {
    for (int v : other.set) {
      if (!this.set.contains(v)) {
        this.set.add(v);
      }
    }
  }

  /**
   * Performs the intersection operation with another set.
   * After execution, this set contains only the elements common to both sets.
   * 
   * @param other the other set to intersect with
   */
  public void intersect(IntegerSet other) {
    this.set.retainAll(other.set);
  }

  /**
   * Performs the difference operation (this \ other).
   * After execution, this set contains elements that were only in this set.
   * 
   * @param other the other set whose elements will be removed
   */
  public void diff(IntegerSet other) {
    this.set.removeAll(other.set);
  }

  /**
   * Performs the complement operation, replacing this set with
   * the elements that are in the other set but not in this one.
   * 
   * @param other the other set
   */
  public void complement(IntegerSet other) {
    List<Integer> result = new ArrayList<>(other.set);
    result.removeAll(this.set);
    this.set.clear();
    this.set.addAll(result);
  }

  /**
   * Checks whether the set contains no elements.
   * 
   * @return {@code true} if the set is empty, {@code false} otherwise
   */
  public boolean isEmpty() {
    return set.isEmpty();
  }

  /**
   * Returns a string representation of the set in the form [a, b, c].
   * 
   * @return a string containing all elements separated by commas
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < set.size(); i++) {
      sb.append(set.get(i));
      if (i < set.size() - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}

