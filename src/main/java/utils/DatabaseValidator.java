package utils;

import org.rookit.utils.log.Logs;
import org.rookit.utils.log.Validator;

@SuppressWarnings("javadoc")
public class DatabaseValidator extends Validator {
	
	private static final DatabaseValidator SINGLETON = new DatabaseValidator();
	
	public static final DatabaseValidator getDefault() {
		return SINGLETON;
	}

	private DatabaseValidator() {
		super(Logs.DATABASE);
	}

}
