package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by lilly on 3/31/2016.
 */
public class ProcessingThread extends Thread {
    private double geometricSum;
    private double arithmeticSum;
    private boolean isRunning = true;
    private Context context;
    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        geometricSum = Math.sqrt(firstNumber*secondNumber);
        arithmeticSum = (firstNumber + secondNumber)/2;
        this.context = context;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while(isRunning){
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage(){
        Intent intent = new Intent("broadcast_message_action");
        String message = new Date(System.currentTimeMillis()).toString() + " and result is: " + geometricSum+ " and "+arithmeticSum;
        intent.putExtra("broadcast_message", message);
        intent.setAction("LIVIA_ACTION");
        context.sendBroadcast(intent);
    }
    private void sleep(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
    public void stopThread(){
        isRunning = false;
    }
}
