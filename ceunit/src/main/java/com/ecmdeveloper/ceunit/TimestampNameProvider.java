/**
 * 
 */
package com.ecmdeveloper.ceunit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ricardo.belfor
 *
 */
public class TimestampNameProvider {

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd HHmmssSSS");

	@Override
	public String toString() {
		return dateFormatter.format( new Date() );
	}
}
