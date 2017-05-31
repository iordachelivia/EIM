package ro.pub.cs.systems.eim.practicaltest01;

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

    private String resultedString;
public ProcessingThread(Context context, String resulted) {
        this.context = context;

        this.resultedString = resulted;
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
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + resultedString);
        context.sendBroadcast(intent);
        }

private void sleep() {
        try {
        Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
        }
        }

public void stopThread() {
        isRunning = false;
        }
        }
