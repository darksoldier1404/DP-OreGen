package dp.dog.main;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tuple<A, B> implements Serializable{

	private final A a;
	private final B b;

	public Tuple(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public static <A, B> Tuple<A, B> of(final A a, final B b) {
		return new Tuple<>(a, b);
	}

	public A a() {
		return a;
	}

	public B b() {
		return b;
	}
}