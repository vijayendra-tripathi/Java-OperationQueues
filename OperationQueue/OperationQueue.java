
/*
 MIT License
 
 Copyright (c) 2016 Vijayendra Tripathi
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OperationQueue {
 
	private static OperationQueue queue = null;
	private ExecutorService simpleExecutor = null;
	private ExecutorService multiExecutor = null;
	
	private static final int EXECUTOR_THREAD_1 = 1; // Performs one operation at a time
	private static final int EXECUTOR_THREAD_4 = 4; // Performs multiple (4) operations at a time
	
	private OperationQueue() {
		simpleExecutor = Executors.newFixedThreadPool(EXECUTOR_THREAD_1);
		multiExecutor = Executors.newFixedThreadPool(EXECUTOR_THREAD_4);
	}
	
	public static OperationQueue sharedQueue() {
		if(queue == null) {
			queue = new OperationQueue();
		}
		return queue;
	}
	
    /* Add runnable object to the queue */
	public void addOperationToSequentialExecutor(Runnable runnable) {
		simpleExecutor.execute(runnable);
	}
	
    /* Add runnable object to the queue */
	public void addOperationToMultiSimultaneousExecutor(Runnable runnable) {
		multiExecutor.execute(runnable);
	}
	
	public void quit() {
		try {
			simpleExecutor.shutdown();
			simpleExecutor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			multiExecutor.shutdown();
			multiExecutor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
