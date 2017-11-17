package org.rookit.mongodb.queries.filter;

import java.time.LocalDate;

@SuppressWarnings("javadoc")
public interface PlayableFilter<Q> {
	
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
