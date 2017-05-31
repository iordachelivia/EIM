package ro.pub.cs.systems.eim.practicaltest021;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private int multiply;
    private double divide;
    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        multiply = (firstNumber * secondNumber);
        divide = firstNumber/secondNumber;
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

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("livia");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " Multiplication " + multiply + " Division " + divide);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
