package com.ecmdeveloper.ceunit.jupiter.internal;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;
import com.filenet.api.property.PropertyFilter;

/**
 * @author ricardo.belfor
 *
 */
public class TestFolderCreator {

	private final ObjectStore objectStore;
	private static PropertyFilter idFilter = new PropertyFilter();
	
	static {
		idFilter.addIncludeProperty(0, null, null, PropertyNames.ID, null);
	}

	public TestFolderCreator(ObjectStore objectStore) {
		this.objectStore = objectStore;
	}

	public Folder create(String className, String path) {
		List<String> pathParts = getParts(path);
		return create(className, pathParts, false );
	}
	
	public Folder create(String className, List<String> pathParts, boolean usePropertyFilter) {
		
		try {
			String path = pathParts.stream().collect(Collectors.joining("/", "/", "") );
			Folder folder = Factory.Folder.fetchInstance(objectStore, path, usePropertyFilter ? idFilter : null);
			return folder;
		} catch (EngineRuntimeException exception) {
			if ( exception.getExceptionCode().equals(ExceptionCode.E_OBJECT_NOT_FOUND ) ) {
				return createFolder(className, pathParts, usePropertyFilter );
			} else {
				throw new RuntimeException(exception);
			}
		}
	}

	private Folder createFolder(String className, List<String> pathParts, boolean usePropertyFilter ) {
		
		List<String> parentPath = pathParts.stream()
				.limit(pathParts.size() -1 )
				.collect(Collectors.toList() );
		
		Folder parentFolder = create(ClassNames.FOLDER, parentPath, true );
			
		Folder subFolder = Factory.Folder.createInstance(objectStore, className);
		subFolder.set_FolderName( pathParts.get(pathParts.size()-1) );
		subFolder.set_Parent(parentFolder);
		subFolder.save(RefreshMode.REFRESH, usePropertyFilter ? idFilter : null );
		return subFolder;
	}
	
	/**
	 * Splits the path into different parts. We use this method because it will
	 * handle this situation correctly:
	 * 
	 * <pre>
	 * //My Strange Path/
	 * </pre>
	 * 
	 * Returning only one element.
	 *
	 * TODO check if this also works on a Windows machine.
	 * 
	 * @return the path parts
	 */
	public List<String> getParts(String path) {
		Path p = Paths.get(path);
		return StreamSupport.stream(p.spliterator(), false)
			.map(Path::toString)
			.collect(Collectors.toList());
	}

}