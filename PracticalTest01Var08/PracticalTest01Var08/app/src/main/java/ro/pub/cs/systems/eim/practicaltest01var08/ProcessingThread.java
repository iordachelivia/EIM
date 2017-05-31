package ro.pub.cs.systems.eim.practicaltest01var08;

/**
 * Created by lilly on 3/31/2016.
 */
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private String text;

    public ProcessingThread(Context context, String text) {
        this.context = context;
        this.text = text;

    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            String[] tokenized = text.split("\\, ");
            for(String token: tokenized){
                sendMessage(token);
            }

            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage(String token) {
        Intent intent = new Intent();

        intent.setAction("practical");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + token);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}