/**
 * 
 */
package com.ecmdeveloper.junit.ce.internal;

import java.text.MessageFormat;

import org.junit.runner.Description;

import com.ecmdeveloper.ceunit.annotations.TestFolder;

/**
 * @author ricardo.belfor
 *
 */
public class TestFolderPathCreator {

	private static final char PATH_SEPARATOR = '/';
	private TestFolder testFolder;
	private Description description;
	private String fieldName;

	public TestFolderPathCreator(TestFolder testFolder, Description description, String fieldName) {
		this.testFolder = testFolder;
		this.description = description;
		this.fieldName = fieldName;
	}

	public String getFolderPath() throws Exception {
		
		StringBuffer path = new StringBuffer();
		
		path.append( getParentPath(testFolder, fieldName) );
		path.append(PATH_SEPARATOR);
		path.append( getFolderName(testFolder) );

		replacePlaceHolder(path, "c", "0");
		replacePlaceHolder(path, "m", "1");

		return MessageFormat.format( path.toString(), getSimpleClassName(), description.getMethodName() );
	}
	
	private void replacePlaceHolder(StringBuffer template, String placeHolder, String replacement) {

		String pattern = "{" + placeHolder + "}";
		int i;
		do {
			i = template.indexOf( pattern );
			if ( i >= 0 ) {
				template.replace(i+1, i+2, replacement);
			}
		} while ( i >= 0 );
	}


	private String getSimpleClassName() {
		String className = description.getClassName();
		int dotIndex = className.lastIndexOf('.');
		if ( dotIndex > 0) {
			className = className.substring(dotIndex+1);
		}
		return className;
	}

	private String getFolderName(TestFolder testFolder)
			throws InstantiationException, IllegalAccessException {
		String name = testFolder.name();
		if ( name.isEmpty() ) {
			Object newInstance = testFolder.nameProviderClass().newInstance();
			name = newInstance.toString();
		}
		return name;
	}


	private String getParentPath(TestFolder testFolder, String fieldName) {
		String parentPath = testFolder.parentPath();
		
		if ( parentPath.isEmpty() ) {
			throw new IllegalArgumentException("The parent path for the test folder '" + fieldName + "' cannot be empty");
		}
		return parentPath;
	}
	
}
