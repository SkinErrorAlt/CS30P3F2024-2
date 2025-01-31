package AvoidingObstacles;

//File: AvoidingObstacles.java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.phidget22.PhidgetException;

public class AvoidingObstaclesMain {
	public static void main(String[] args) throws InterruptedException {
		// Create a thread pool with 3 threads
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		// Gets the java stuff to use for multi-threading.
		Vector3_Ai_System aiSystem = new Vector3_Ai_System(); // This is for making the AI run.
		Vector3_Ai_Ui aiUi = new Vector3_Ai_Ui(); // This is for displaying information about the AI in real time.
		Vector3_Ai_Information aiInfo = new Vector3_Ai_Information(); // This is for getting the information.
		
		executorService.submit(() -> 
		{
			try 
			{
				aiSystem.MainRun();
			}
			catch (PhidgetException e) 
			{
				System.err.println("Failed Connection");
				aiSystem.ConnectionFailed = true;
				aiUi.ConnectionFailed = true;
				aiInfo.ConnectionFailed = true;
			}
			catch (Exception e) 
			{
				System.err.println("_| Failed something |_");
				e.printStackTrace();
			}
		});
		
		executorService.submit(() -> 
		{
			try 
			{
				aiInfo.MainRun();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		});
		
		executorService.submit(() -> 
		{
			try 
			{
				aiUi.UpdateLoading();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		});
		
		executorService.submit(() -> 
		{
			try 
			{
				aiUi.MainRun();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		});

		// Shutdown the executor service
		executorService.shutdown();
	}
}
