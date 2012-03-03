package server;

import java.util.LinkedList;

class ThreadPool extends ThreadGroup {

	private boolean isAlive;

	private LinkedList<Runnable> taskQueue;

	private int threadID;

	private static int threadPoolID;
	//constructor
	public ThreadPool(int numThreads) {
		super("ThreadPool-" + (threadPoolID++));
//		Changes the daemon status of this thread group. 
		setDaemon(true);
		
		isAlive = true;

		taskQueue = new LinkedList<Runnable>();
		for (int i = 0; i < numThreads; i++) {
			new PooledThread().start();
		}
	}

	public synchronized void runTask(Runnable task) {
		if (!isAlive) {
			throw new IllegalStateException();
		}
		if (task != null) {
			taskQueue.add(task);
			notify();
		}

	}

	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskQueue.size() == 0) {
			if (!isAlive) {
				return null;
			}
			wait();
		}
		return (Runnable) taskQueue.removeFirst();
	}

	public synchronized void close() {
		if (isAlive) {
			isAlive = false;
			taskQueue.clear();
			interrupt();
		}
	}

	public void join() {
		// notify all waiting threads that this ThreadPool is no
		// longer alive
		synchronized (this) {
			isAlive = false;
			notifyAll();
		}

		// wait for all threads to finish
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);
		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException ex) {
			}
		}
	}

	private class PooledThread extends Thread {

		public PooledThread() {
			super(ThreadPool.this, "PooledThread-" + (threadID++));
		}

		public void run() {
			while (!isInterrupted()) {

				// get a task to run
				Runnable task = null;
				try {
					task = getTask();
				} catch (InterruptedException ex) {
				}

				// if getTask() returned null or was interrupted,
				// close this thread by returning.
				if (task == null) {
					return;
				}

				// run the task, and eat any exceptions it throws
				try {
					task.run();
				} catch (Throwable t) {
					uncaughtException(this, t);
				}
			}
		}
	}
}