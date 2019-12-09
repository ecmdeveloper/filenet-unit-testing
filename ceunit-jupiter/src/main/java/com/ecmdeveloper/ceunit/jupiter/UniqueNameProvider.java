/**
 * 
 */
package com.ecmdeveloper.ceunit.jupiter;

import java.util.UUID;

/**
 * @author ricardo.belfor
 *
 */
public class UniqueNameProvider {

	@Override
	public String toString() {
		return UUID.randomUUID().toString();
	}

}
