package org.rookit.mongodb.utils;

import java.util.List;

import com.google.common.collect.Lists;

class OrderImpl implements Order {

	private final List<FieldOrder> order;
	
	OrderImpl() {
		order = Lists.newLinkedList();
	}

	@Override
	public void addField(FieldOrder criteria) {
		order.add(criteria);
	}

	@Override
	public void addField(String fieldName, TypeOrder order) {
		addField(new FieldOrder(fieldName, order));
	}

	@Override
	public Iterable<FieldOrder> getFields() {
		return order;
	}
}
