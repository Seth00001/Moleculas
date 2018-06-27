package testcases;

import physicsCore.ThreadPool;

public class MainForThreadPool {

	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool();
		pool.setThreadCount(1);
		pool.finalize();
		
	}

}
