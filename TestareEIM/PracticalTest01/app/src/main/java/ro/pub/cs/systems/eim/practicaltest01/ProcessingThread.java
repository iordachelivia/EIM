package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by lilly on 3/31/2016.
 */
    public class ProcessingThread extends Thread {

        private Context context = null;
        private boolean isRunning = true;

        private Random random = new Random();

        private int sum;

        public ProcessingThread(Context context, int sum) {
            this.context = context;
            this.sum = sum;
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
            intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + sum);
            context.sendBroadcast(intent);
        }

        private void sleep() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        public void stopThread() {
            isRunning = false;
        }
    }
