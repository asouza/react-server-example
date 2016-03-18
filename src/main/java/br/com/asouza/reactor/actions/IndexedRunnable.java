package br.com.asouza.reactor.actions;

public class IndexedRunnable implements Runnable {

	private int index;
	private Runnable delegate;

	public IndexedRunnable(int index, Runnable delegate) {
		super();
		this.index = index;
		this.delegate = delegate;
	}

	@Override
	public void run() {
		delegate.run();
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "IndexedRunnable [index=" + index + "]";
	};
	
	
	

}
