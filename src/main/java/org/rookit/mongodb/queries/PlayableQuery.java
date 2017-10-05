package org.rookit.mongodb.queries;

import java.time.LocalDate;

import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface PlayableQuery<E extends Element, Q extends RookitQuery<E>> extends RookitQuery<E> {
	
	Q playedMoreThan(long plays);
	Q playedLessThan(long plays);
	Q playedBetween(long min, long max);
	
	Q lastPlayedBefore(LocalDate date);
	Q lastPlayedAfter(LocalDate date);
	
	Q skippedMoreThan(long skipped);
	Q skippedLessThan(long skipped);
	Q skippedBetween(long min, long max);
	
	Q lastSkippedBefore(LocalDate date);
	Q lastSkippedAfter(LocalDate date);
	
	Q withDurationGreaterThan(long duration);
	Q withDurationSmallerThan(long duration);
	Q withDurationBetween(long min, long max);

}
