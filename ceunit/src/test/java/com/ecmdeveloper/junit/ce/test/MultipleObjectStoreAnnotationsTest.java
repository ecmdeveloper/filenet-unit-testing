/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.TestObjectStore;
import com.filenet.api.core.ObjectStore;

/**
 * @author ricardo.belfor
 *
 */
public class MultipleObjectStoreAnnotationsTest {

	private static String ERROR_MESSAGE_1 = "There are more than one non-static fields annotated as an object stores";

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestRule chain = RuleChain.outerRule( new TestErrorRule(ERROR_MESSAGE_1) ).around( new TestConnection(this, username, password, url )  );

	@TestObjectStore(name="P8ConfigObjectStore")
	private static ObjectStore staticObjectStore1;

	@TestObjectStore(name="P8ConfigObjectStore")
	private static ObjectStore staticObjectStore2;
	
	@TestObjectStore(name="P8ConfigObjectStore")
	private ObjectStore objectStore;

	@TestObjectStore(name="P8ConfigObjectStore")
	private ObjectStore secondObjectStore;
	
	@Test
	public void testObjectStoreNotInjected() throws Exception {
		assertNull("Is object store set?", objectStore);
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
