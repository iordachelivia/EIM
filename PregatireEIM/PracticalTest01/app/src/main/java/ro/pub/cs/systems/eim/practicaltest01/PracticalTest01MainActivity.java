package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private ButtonListener buttonListener = new ButtonListener();
    private SpecialButtonListener specialButtonListener = new SpecialButtonListener();
    private EditText editOne;
    private EditText editTwo;
    private Button pressme;
    private Button pressMeTwo;
    private Button navigate;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("broadcast_message"));
        }
    }

    private class SpecialButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(getApplicationContext(),PracticalTest01SecondaryActivity.class);

            //get values
            int clicks = Integer.parseInt(editOne.getText().toString()) + Integer.parseInt(editTwo.getText().toString());

            intent.putExtra("nr_clicks",clicks);
            startActivityForResult(intent,2016);
        }
    }
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int leftNumberOfClicks = Integer.parseInt(editOne.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(editTwo.getText().toString());

            if (leftNumberOfClicks + rightNumberOfClicks > 10 ){
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", leftNumberOfClicks);
                intent.putExtra("secondNumber", rightNumberOfClicks);
                getApplicationContext().startService(intent);
            }
            switch (v.getId()){
                case R.id.press_me:
                    int old = Integer.parseInt(editOne.getText().toString());
                    editOne.setText(String.valueOf(old+1));
                    break;

                case R.id.press_me_too:
                    int old2 = Integer.parseInt(editTwo.getText().toString());
                    editTwo.setText(String.valueOf(old2+1));
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editOne = (EditText)findViewById(R.id.edit_one);
        editTwo = (EditText)findViewById(R.id.edit_two);
        pressme = (Button)findViewById(R.id.press_me);
        pressMeTwo = (Button)findViewById(R.id.press_me_too);
        navigate = (Button)findViewById(R.id.navigate);

        editOne.setText("0");
        editTwo.setText("0");

        pressme.setOnClickListener(buttonListener);
        pressMeTwo.setOnClickListener(buttonListener);
        navigate.setOnClickListener(specialButtonListener);

        intentFilter.addAction("LIVIA_ACTION");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("editOneval",editOne.getText().toString());
        outState.putString("editTwoval",editTwo.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey("editOneval")){
            editOne.setText(savedInstanceState.getString("editOneval"));
        }
        if(savedInstanceState.containsKey("editTwoval")){
            editTwo.setText(savedInstanceState.getString("editTwoval"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2016){
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}

