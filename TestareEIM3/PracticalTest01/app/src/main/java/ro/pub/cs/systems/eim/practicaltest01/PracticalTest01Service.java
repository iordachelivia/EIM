package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Service extends Service {
    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String resultedString = intent.getStringExtra("resultedString");
        processingThread = new ProcessingThread(this, resultedString);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }

}
