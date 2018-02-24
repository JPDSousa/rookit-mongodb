package org.rookit.mongodb.utils;

import java.util.List;

import org.rookit.api.storage.utils.FieldOrder;
import org.rookit.api.storage.utils.Order;

import com.google.common.collect.Lists;

@SuppressWarnings("javadoc")
public class OrderImpl implements Order {

	private final List<FieldOrder> order;
	
	public OrderImpl() {
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
