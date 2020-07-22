package com.ecmdeveloper.ceunit.jupiter.internal;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.filenet.api.collection.StringList;

/**
 * 
 * @author Ricardo Belfor
 *
 */
@SuppressWarnings("unchecked")
public class StreamUtils {

	public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
		Iterable<T> iterable = () -> sourceIterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	 
	public static Stream<String> asStream(StringList stringList) {
		if ( stringList == null) {
			return Stream.empty();
		}
    	return asStream(stringList.iterator());
    }	
}
