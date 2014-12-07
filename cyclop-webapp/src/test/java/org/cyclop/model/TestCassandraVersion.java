package org.cyclop.model;

import static org.cyclop.model.CassandraVersion.VER_1_2;
import static org.cyclop.model.CassandraVersion.VER_2_0;
import static org.cyclop.model.CassandraVersion.VER_2_1;
import static org.cyclop.model.CassandraVersion.VER_MAX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** @author Maciej Miklas */
public class TestCassandraVersion {

	@Test
	public void testAfter() {
		assertFalse(VER_1_2.after(VER_1_2));
		assertFalse(VER_1_2.after(VER_2_0));
		assertFalse(VER_1_2.after(VER_2_1));

		assertTrue(VER_2_0.after(VER_1_2));
		assertTrue(VER_2_1.after(VER_1_2));
	}

	@Test
	public void testBefore() {
		assertFalse(VER_1_2.before(VER_1_2));

		assertTrue(VER_1_2.before(VER_2_0));
		assertTrue(VER_1_2.before(VER_2_1));

		assertFalse(VER_2_0.before(VER_1_2));
		assertFalse(VER_2_1.before(VER_1_2));
	}

	@Test
	public void testIncluded_1_2() {
		assertTrue(VER_1_2.included(VER_1_2, VER_1_2));
		assertTrue(VER_1_2.included(VER_1_2, VER_2_0));
		assertTrue(VER_1_2.included(VER_1_2, VER_2_1));
		assertTrue(VER_1_2.included(VER_1_2, VER_MAX));

		assertFalse(VER_1_2.included(VER_2_0, VER_2_0));
		assertFalse(VER_1_2.included(VER_2_1, VER_2_1));
		assertFalse(VER_1_2.included(VER_2_0, VER_2_1));
	}

	@Test
	public void testIncluded_2_0() {
		assertTrue(VER_2_0.included(VER_1_2, VER_2_0));
		assertTrue(VER_2_0.included(VER_1_2, VER_2_1));
		assertTrue(VER_2_0.included(VER_1_2, VER_MAX));
		assertTrue(VER_2_0.included(VER_2_0, VER_2_0));
		assertTrue(VER_2_0.included(VER_2_0, VER_2_1));

		assertFalse(VER_2_0.included(VER_2_1, VER_2_1));
		assertFalse(VER_2_0.included(VER_1_2, VER_1_2));
	}

	@Test
	public void testIncluded_2_1() {
		assertTrue(VER_2_1.included(VER_1_2, VER_2_1));
		assertTrue(VER_2_1.included(VER_2_1, VER_2_1));
		assertTrue(VER_2_1.included(VER_2_1, VER_MAX));

		assertFalse(VER_2_1.included(VER_1_2, VER_1_2));
		assertFalse(VER_2_1.included(VER_1_2, VER_2_0));
	}

	@Test
	public void testBetween_1_2() {
		assertFalse(VER_1_2.between(VER_1_2, VER_2_0));
		assertFalse(VER_1_2.between(VER_1_2, VER_2_1));
		assertFalse(VER_1_2.between(VER_1_2, VER_1_2));
		assertFalse(VER_1_2.between(VER_2_0, VER_2_0));
		assertFalse(VER_1_2.between(VER_2_1, VER_2_1));
	}

	@Test
	public void testBetween_2_0() {
		assertTrue(VER_2_0.between(VER_1_2, VER_2_1));
		assertTrue(VER_2_0.between(VER_1_2, VER_MAX));

		assertFalse(VER_2_0.between(VER_1_2, VER_2_0));
		assertFalse(VER_2_0.between(VER_2_0, VER_2_0));
		assertFalse(VER_2_0.between(VER_2_0, VER_2_1));
	}

	@Test
	public void testBetween_2_1() {
		assertFalse(VER_2_1.between(VER_1_2, VER_2_0));
		assertFalse(VER_2_1.between(VER_1_2, VER_2_1));
		assertFalse(VER_2_1.between(VER_1_2, VER_1_2));
		assertFalse(VER_2_1.between(VER_2_0, VER_2_0));
		assertFalse(VER_2_1.between(VER_2_1, VER_2_1));
		assertFalse(VER_2_1.between(VER_2_1, VER_MAX));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncluded_FromAfterTo() {
		VER_2_1.included(VER_2_1, VER_2_0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBetween_FromAfterTo() {
		VER_2_0.between(VER_2_1, VER_1_2);
	}

}
