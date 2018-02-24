package org.rookit.mongodb;

import static org.rookit.utils.config.ConfigUtils.*;

import java.util.Properties;

@SuppressWarnings("javadoc")
public class DatabaseConfig {
	
	public static final String DEFAULT_DRIVER = "rookit-mongodb";
	public static final Properties DEFAULT_OPTIONS = new Properties();
	
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String DB_NAME = "db_name";
	
	static {
		DEFAULT_OPTIONS.put(HOST, "localhost");
		DEFAULT_OPTIONS.put(PORT, "27020");
		DEFAULT_OPTIONS.put(DB_NAME, "rookit");
	}
	
	private String driverName;
	private Properties options;
	
	public String getDriverName() {
		return getOrDefault(driverName, "");
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public Properties getOptions() {
		return getOrDefault(options, DEFAULT_OPTIONS);
	}
	
	public void setOptions(Properties options) {
		this.options = options;
	}
	
}