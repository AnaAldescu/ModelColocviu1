package ro.pub.cs.systems.eim.practicaltest01;

import java.sql.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {
	
	private Context context = null;
	private boolean isRunning = true;
	 
	private Random random = new Random();
	 
	private double arithmeticMean;
	private double geometricMean;
	
	public ProcessingThread(Context context, int firstNumber, int secondNumber) {
	    this.context = context;
	 
	    arithmeticMean = (firstNumber + secondNumber) / 2;
	    geometricMean = Math.sqrt(firstNumber * secondNumber);
	}
	
	@Override
	public void run() {
		Log.d("[ProcessingThread]", "Thread has started!");
	    
		while (isRunning) {
			sendMessage();
			sleep();
	    }
		
	    Log.d("[ProcessingThread]", "Thread has stopped!");
	  }

	private void sleep() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage() {
		Intent intent = new Intent();
		
	    intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
	    intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " "
	    																	  + geometricMean);
	    context.sendBroadcast(intent);
	}
	
	public void stopThread() {
	    isRunning = false;
	}
}
