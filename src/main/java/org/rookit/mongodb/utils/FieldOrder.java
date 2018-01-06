package org.rookit.mongodb.utils;

import org.rookit.mongodb.utils.Order.TypeOrder;

@SuppressWarnings("javadoc")
public class FieldOrder {

	private final String field;
	private final TypeOrder order;
	
	public FieldOrder(String field, TypeOrder order) {
		super();
		this.field = field;
		this.order = order;
	}

	public String getField() {
		return field;
	}

	public TypeOrder getOrder() {
		return order;
	}
	
}
