package com.ecmdeveloper.ceunit.jupiter.test;

import java.util.Optional;

import com.ecmdeveloper.ceunit.jupiter.TestFolderConfiguration;

public class MyTestFolderConfiguration implements TestFolderConfiguration {

	@Override
	public Optional<String> getName() {
		return Optional.of("Configured Folder Name");
	}

	@Override
	public Optional<String> getParentPath() {
		return Optional.of("/CEUnit Jupiter/FolderConfiguration");
	}

	@Override
	public Optional<String> getClassName() {
		return Optional.of("TestFolderClass1");
	}
}
