package org.rookit.mongodb.log;

import org.mongodb.morphia.logging.Logger;
import org.mongodb.morphia.logging.LoggerFactory;
import org.rookit.mongodb.utils.DatabaseValidator;

@SuppressWarnings("javadoc")
public class RookitMorphiaLoggerFactory implements LoggerFactory {
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();

	@Override
	public Logger get(Class<?> c) {
		return new LoggerAdapter(c);
	}
	
	private class LoggerAdapter implements Logger {
		
		private org.apache.logging.log4j.Logger logger;

		private LoggerAdapter(final Class<?> clazz) {
			logger = VALIDATOR.getLogger(clazz);
		}
		

		@Override
		public void debug(final String msg) {
			logger.debug(msg);
		}

		@Override
		public void debug(final String msg, final Object... arg) {
			logger.debug(msg, arg);
		}

		@Override
		public void debug(final String msg, final Throwable t) {
			logger.debug(msg, t);
		}

		@Override
		public void error(final String msg) {
			logger.error(msg);
		}

		@Override
		public void error(final String msg, final Object... arg) {
			logger.error(msg, arg);
		}

		@Override
		public void error(final String msg, final Throwable t) {
			logger.error(msg, t);
		}

		@Override
		public void info(final String msg) {
			logger.info(msg);
		}

		@Override
		public void info(final String msg, final Object... arg) {
			logger.info(msg, arg);
		}

		@Override
		public void info(final String msg, final Throwable t) {
			logger.info(msg, t);
		}

		@Override
		public boolean isDebugEnabled() {
			return logger.isDebugEnabled();
		}

		@Override
		public boolean isErrorEnabled() {
			return logger.isErrorEnabled();
		}

		@Override
		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		@Override
		public boolean isTraceEnabled() {
			return logger.isTraceEnabled();
		}

		@Override
		public boolean isWarningEnabled() {
			return logger.isWarnEnabled();
		}

		@Override
		public void trace(final String msg) {
			logger.trace(msg);
		}

		@Override
		public void trace(final String msg, final Object... arg) {
			logger.trace(msg, arg);
		}

		@Override
		public void trace(final String msg, final Throwable t) {
			logger.trace(msg, t);
		}

		@Override
		public void warning(final String msg) {
			logger.warn(msg);
		}

		@Override
		public void warning(final String msg, final Object... arg) {
			logger.warn(msg, arg);
		}

		@Override
		public void warning(final String msg, final Throwable t) {
			logger.warn(msg, t);
		}
		
	}

}
