package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Service extends Service {
    private ProcessingThread processingThread = null;
    public PracticalTest01Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get numbers
        int nrOne = intent.getIntExtra("firstNumber",-1);
        int nrTwo = intent.getIntExtra("secondNumber", -1);

        processingThread = new ProcessingThread(this, nrOne, nrTwo);
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
