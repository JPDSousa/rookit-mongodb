package org.rookit.mongodb.queries;

import java.util.stream.Stream;

import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface RookitQuery<E extends Element> {

	Stream<E> stream();

	long count();

	E first();

	E byElement(E element);

}
