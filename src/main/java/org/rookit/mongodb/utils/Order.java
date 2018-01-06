package org.rookit.mongodb.utils;

@SuppressWarnings("javadoc")
public interface Order {
	
	static Order create() {
		return new OrderImpl();
	}
	
	enum TypeOrder {
		ASC,
		DSC
	}

	void addField(FieldOrder criteria);
	void addField(String fieldName, TypeOrder order);
	
	Iterable<FieldOrder> getFields();
	
}
