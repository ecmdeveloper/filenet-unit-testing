/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.TestFolder;
import com.filenet.api.core.Folder;

/**
 * @author ricardo.belfor
 *
 */
public class InjectTestFolderNoParentTest {

	private static final String TEST_FOLDER_NAME = "MyTest";
	private static final String ERROR_MESSAGE_1 = "The parent path for the test folder 'badlyAnnotatedfolder' cannot be empty";
	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestRule chain = RuleChain.outerRule( new TestErrorRule(ERROR_MESSAGE_1) ).around( new TestConnection(this, username, password, url )  );

	@TestFolder(objectStoreName="P8ConfigObjectStore", name=TEST_FOLDER_NAME )
	Folder badlyAnnotatedfolder;

	@Test
	public void test() throws Exception {
		fail("Should not be reached");
	}

	private class TestErrorRule implements TestRule {
		
		private final String message;
		
		public TestErrorRule(String message) {
			super();
			this.message = message;
		}

		@Override
		public Statement apply(final Statement statement, Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					try {
						statement.evaluate();
						fail("TestConnectionStatement did not throw an error");
					} catch (Exception e) {
						assertEquals(
								"Correct error message?",
								message,
								e.getMessage());
					}
				}

			};
		}
	}
}