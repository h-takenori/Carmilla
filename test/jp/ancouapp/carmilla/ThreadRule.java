package jp.ancouapp.carmilla;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ThreadRule implements MethodRule {
	private final int count;

	public ThreadRule(int count) {
		this.count = count;
	}

	public Statement apply(final Statement base, FrameworkMethod method,
			Object target) {

		final Runnable run = new Runnable() {
			public void run() {
				try {
					base.evaluate();
				} catch (Throwable t) {
					throw new RuntimeException(t);
				}
			}
		};

		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				ExecutorService es = Executors.newCachedThreadPool();
				Future<?>[] fa = new Future[count];
				for (int i = 0; i < count; i++) {
					fa[i] = es.submit(run);
				}
				for (Future<?> f : fa) {
					f.get();
				}
			}
		};
	}
}