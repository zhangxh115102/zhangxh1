package com.shsunedu.tool;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for {@link SimpleDateFormat}s. The instances are stored in a
 * threadlocal way because SimpleDateFormat is not threadsafe as noted in
 * {@link SimpleDateFormat its javadoc}.
 *
 */
public class DateFormatHolder {

	private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new SoftReferenceThreadLocal();

	/**
	 * creates a {@link SimpleDateFormat} for the requested format string.
	 *
	 * @param pattern
	 *            a non-<code>null</code> format String according to
	 *            {@link SimpleDateFormat}. The format is not checked against
	 *            <code>null</code> since all paths go through {@link DateUtils}
	 *            .
	 * @return the requested format. This simple dateformat should not be used
	 *         to {@link SimpleDateFormat#applyPattern(String) apply} to a
	 *         different pattern.
	 */
	public static SimpleDateFormat formatFor(String pattern) {
		SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
		Map<String, SimpleDateFormat> formats = ref.get();
		if (formats == null) {
			formats = new HashMap<String, SimpleDateFormat>();
			THREADLOCAL_FORMATS.set(new SoftReference<Map<String, SimpleDateFormat>>(formats));
		}

		SimpleDateFormat format = formats.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			formats.put(pattern, format);
		}

		return format;
	}

}