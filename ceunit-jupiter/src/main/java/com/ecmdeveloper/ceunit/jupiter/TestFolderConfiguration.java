package com.ecmdeveloper.ceunit.jupiter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ricardo Belfor
 *
 */
public interface TestFolderConfiguration {

	default Optional<String> getName() {
		return Optional.empty();
	}
	
	default Optional<String> getParentPath() {
		return Optional.empty();
	}
	
	default Optional<String> getClassName() {
		return Optional.empty();
	}
	
	default Map<String,Object> getPropertiesMap() {
		return Collections.emptyMap();
	}
}
