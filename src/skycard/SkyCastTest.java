package skycard;


import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SkyCastTest {

	SkyCast obj;

	@Before
	public void setUp() {
		obj = new SkyCast();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testclickCountTestCase1() throws Exception {

		String limit = "1 20";
		String blockList = "2 18 19";
		String favorite = "5 15 14 17 1 17";

		int expectedResult = 7;
		int result = obj.clickCount(limit, blockList, favorite);

		assertEquals(expectedResult, result);
	}

	@Test
	public void testclickCountTestCase2() throws Exception {

		String limit = "103 108";
		String blockList = "1 104";
		String favorite = "5 105 106 107 103 105";
		int expResult = 8;
		int result = obj.clickCount(limit, blockList, favorite);
		assertEquals(expResult, result);

	}

	@Test
	public void testclickCountTestCase3() throws Exception {
		String limit = "1 100";
		String blockList = "4 78 79 80 3";
		String favorite = "8 10 13 13 100 99 98 77 81";
		int expResult = 12;
		int result = obj.clickCount(limit, blockList, favorite);
		assertEquals(expResult, result);
	}

	@Test
	public void testclickCountTestCase4() throws Exception {
		String limit = "1 200";
		String blockList = "0";
		String favorite = "4 1 100 1 101";
		int expResult = 7;
		int result = obj.clickCount(limit, blockList, favorite);
		assertEquals(expResult, result);
	}

	@Test
	public void testcheckGreaterOrSmallerChannelTestCase1() {
		int current = 33;
		int size = 3;
		int previous = 31;
		int secondPrevious = 44;
		obj = new SkyCast();
		boolean expResult = false;
		boolean result = obj.checkGreaterOrSmallerChannel(current, size, previous, secondPrevious);
		assertEquals(expResult, result);

	}

	@Test
	public void testcheckGreaterOrSmallerChannelTestCase2() {
		int current = 145;
		int size = 2;
		int previous = 143;
		int secondPrevious = 147;
		obj = new SkyCast();
		boolean expResult = true;
		boolean result = obj.checkGreaterOrSmallerChannel(current, size, previous, secondPrevious);
		assertEquals(expResult, result);

	}

	@Test
	public void testCheckBoundaryCaseTestCase1() {
		int current = 9999;
		int startNumber = 1;
		int endNumber = 10000;
		int size = 4;
		int previous = 1;
		boolean expResult = false;
		boolean result = obj.checkBoundaryCase(current, startNumber, endNumber, size, previous);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckBoundaryCaseTestCase2() {
		int current = 6;
		int startNumber = 1;
		int endNumber = 1000;
		int size = 2;
		int previous = 1;
		boolean expResult = true;
		boolean result = obj.checkBoundaryCase(current, startNumber, endNumber, size, previous);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckBlockedChannelsTestCase1() {
		Set<Integer> blocked = new HashSet<>();
		blocked.add(2);
		blocked.add(3);
		blocked.add(4);
		int previous = 1;
		int current = 5;
		boolean expResult = false;
		boolean result = obj.checkBlockedChannels(blocked, previous, current);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckBlockedChannelsTestCase2() {
		Set<Integer> blocked = new HashSet<>();
		blocked.add(5);
		blocked.add(6);
		int previous = 4;
		int current = 66;
		boolean expResult = false;
		boolean result = obj.checkBlockedChannels(blocked, previous, current);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckrangeTestCase1() {
		int current = 24;
		boolean expResult = false;
		boolean result = obj.checkrange(current);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckrangeTestCase2() {
		int current = -1;
		boolean expResult = true;
		boolean result = obj.checkrange(current);
		assertEquals(expResult, result);
	}

	@Test
	public void testCheckBlockedlimitTestCase1() {
		int blockedCount = 23;
		obj.checkBlockedlimit(blockedCount);
	}

	@Test
	public void testCheckBlockedlimitTestCase2() {
		int blockedCount = 26;
		obj.checkBlockedlimit(blockedCount);
	}

}