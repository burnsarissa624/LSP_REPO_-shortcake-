package org.howard.edu.lsp.assignment6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerSetTest {
  // add / contains / length / toString / isEmpty
  @Test
  void testAddContainsNoDuplicates() {
    IntegerSet s = new IntegerSet();
    assertTrue(s.isEmpty());
    s.add(1); s.add(1); s.add(2);
    assertEquals(2, s.length());
    assertTrue(s.contains(1));
    assertTrue(s.contains(2));
    assertFalse(s.contains(3));
    assertEquals("[1, 2]", s.toString());
  }

  @Test
  void testClearAndIsEmpty() {
    IntegerSet s = new IntegerSet();
    s.add(5); s.add(7);
    assertFalse(s.isEmpty());
    s.clear();
    assertTrue(s.isEmpty());
    assertEquals(0, s.length());
    assertEquals("[]", s.toString());
  }

  // largest / smallest
  @Test
  void testLargestAndSmallest() {
    IntegerSet s = new IntegerSet();
    s.add(3); s.add(10); s.add(-2);
    assertEquals(10, s.largest());
    assertEquals(-2, s.smallest());
  }

  @Test
  void testLargestThrowsWhenEmpty() {
    IntegerSet s = new IntegerSet();
    assertThrows(IllegalStateException.class, s::largest);
  }

  @Test
  void testSmallestThrowsWhenEmpty() {
    IntegerSet s = new IntegerSet();
    assertThrows(IllegalStateException.class, s::smallest);
  }

  // remove
  @Test
  void testRemovePresentAndAbsent() {
    IntegerSet s = new IntegerSet();
    s.add(4); s.add(8);
    s.remove(4);
    assertFalse(s.contains(4));
    assertTrue(s.contains(8));
    s.remove(100); // no-op
    assertEquals("[8]", s.toString());
  }

  // equals(Object)
  @Test
  void testEqualsOrderIndependentAndTypeSafe() {
    IntegerSet a = new IntegerSet();
    IntegerSet b = new IntegerSet();
    a.add(1); a.add(2); a.add(3);
    b.add(3); b.add(2); b.add(1);
    assertTrue(a.equals(b));
    b.add(4);
    assertFalse(a.equals(b));
    assertFalse(a.equals(null));
    assertFalse(a.equals("not a set"));
  }

  // union
  @Test
  void testUnionMutatesThisOnly() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1); s1.add(2);
    s2.add(2); s2.add(3);
    s1.union(s2);
    assertEquals("[1, 2, 3]", s1.toString());
    assertEquals("[2, 3]", s2.toString()); // unchanged
  }

  @Test
  void testUnionWithEmptyAndSelf() {
    IntegerSet s = new IntegerSet();
    s.add(5);
    IntegerSet empty = new IntegerSet();
    s.union(empty);
    assertEquals("[5]", s.toString());
    s.union(s);
    assertEquals("[5]", s.toString());
  }

  // intersect
  @Test
  void testIntersectKeepsOnlyCommon() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1); s1.add(2); s1.add(3);
    s2.add(2); s2.add(4);
    s1.intersect(s2);
    assertEquals("[2]", s1.toString());
    assertEquals("[2, 4]", s2.toString()); // unchanged
  }

  @Test
  void testIntersectWithEmptyAndSelf() {
    IntegerSet s = new IntegerSet();
    s.add(7); s.add(8);
    IntegerSet empty = new IntegerSet();
    s.intersect(empty);
    assertTrue(s.isEmpty());
    s.add(7); s.add(8);
    s.intersect(s);
    assertEquals("[7, 8]", s.toString());
  }

  // diff (this \ other)
  @Test
  void testDiffRemovesOthersElements() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1); s1.add(2); s1.add(3);
    s2.add(2); s2.add(5);
    s1.diff(s2);
    assertEquals("[1, 3]", s1.toString());
    assertEquals("[2, 5]", s2.toString()); // unchanged
  }

  @Test
  void testDiffWithEmptyAndSelf() {
    IntegerSet s = new IntegerSet();
    s.add(9); s.add(10);
    IntegerSet empty = new IntegerSet();
    s.diff(empty);
    assertEquals("[9, 10]", s.toString());
    s.diff(s);
    assertTrue(s.isEmpty());
  }

  // complement (other \ this)
  @Test
  void testComplementBecomesOtherMinusThis() {
    IntegerSet s1 = new IntegerSet();
    IntegerSet s2 = new IntegerSet();
    s1.add(1); s1.add(3);
    s2.add(1); s2.add(2); s2.add(4);
    s1.complement(s2);
    assertEquals("[2, 4]", s1.toString());
    assertEquals("[1, 2, 4]", s2.toString()); // unchanged
  }

  @Test
  void testComplementWithEmptyAndSelf() {
    IntegerSet s = new IntegerSet();
    s.add(1); s.add(2);
    IntegerSet empty = new IntegerSet();
    s.complement(empty);
    assertTrue(s.isEmpty());
    s.add(1); s.add(2);
    s.complement(s);
    assertTrue(s.isEmpty());
  }

  @Test
  void testToStringEmpty() {
    IntegerSet s = new IntegerSet();
    assertEquals("[]", s.toString());
  }
}
