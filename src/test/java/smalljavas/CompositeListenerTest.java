package smalljavas.compositelistener;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.*;

public class CompositeListenerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{
		setThreadingPolicy(new Synchroniser());
	}};
	private TestListener testListener1 = context.mock(TestListener.class, "testListener1");
	private TestListener testListener2 = context.mock(TestListener.class, "testListener2");
	
	@Test
	public void is_able_to_act_as_another_object() {
		CompositeListener<TestListener> composite = CompositeListener.of(TestListener.class);
		assertNotNull(composite.proxy());
	}

	@Test
	public void delegate_call_to_inner_listeners() {
		CompositeListener<TestListener> composite = CompositeListener.of(TestListener.class);
		composite.add(testListener1);
		composite.add(testListener2);

		context.checking(new Expectations() {{
			oneOf(testListener1).called("Hello World");
			oneOf(testListener2).called("Hello World");
		}});
		
		composite.proxy().called("Hello World");
	}

	
	public interface TestListener {
		
		void called(String text);
		
	}
	
}
