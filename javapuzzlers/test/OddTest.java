/**
 * 
 */
package javapuzzlers.test;

import static org.junit.Assert.*;
import javapuzzlers.src.Odd;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jay
 * 
 */
public class OddTest {
	
	int odd1 = 1;
	int odd2 = -1;
	int even1 = 0;
	int even2 = 2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link javapuzzlers.src.Odd#isOddA(int)}.
	 */
	@Test
	public void testIsOdd_a() {
		assertTrue(Odd.isOddA(odd1));
		assertTrue(Odd.isOddA(odd2));
		assertFalse(Odd.isOddA(even1));
		assertFalse(Odd.isOddA(even2));
	}

	/**
	 * Test method for {@link javapuzzlers.src.Odd#isOdd_b(int)}.
	 */
	@Test
	public void testIsOdd_b() {
		assertTrue(Odd.isOddB(odd1));
		assertTrue(Odd.isOddB(odd2));
		assertFalse(Odd.isOddB(even1));
		assertFalse(Odd.isOddB(even2));
	}

	/**
	 * Test method for {@link javapuzzlers.src.Odd#isOddW(int)}.
	 */
	@Test
	public void testIsOdd_wrong() {
		assertTrue(Odd.isOddW(odd1));
		assertTrue(Odd.isOddW(odd2));  // this test will fail.
		assertFalse(Odd.isOddW(even1));
		assertFalse(Odd.isOddW(even2));
	}

}
