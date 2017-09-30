package org.rookit.mongodb.queries;

import java.util.stream.Stream;

import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface RookitQuery<T extends Element> {

	Stream<T> stream();

	long count();

	T first();

	T byElement(T element);

}
