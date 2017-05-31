package ro.pub.cs.systems.eim.practicaltest01var08;

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
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button navigate,topLeft,topRight,center,bottomLeft,bottomRight;
    private ButtonListener buttonListener = new ButtonListener();
    private int buttons_pressed = 0;
    private boolean serviceStarted = false;
    private int pressed_threshold = 4;

    private int nrTries=0;
    private int correctTries=0;
    private int incorrectTries=0;
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.navigate:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08SecondaryActivity.class);
                    String text = textView.getText().toString();
                    intent.putExtra("buttons_pressed", text);
                    startActivityForResult(intent, 2016);
                    break;

                case R.id.top_left_button:
                    if(textView.getText().toString().compareTo("") != 0){
                        String newstr = textView.getText().toString() + ", " + getResources().getString(R.string.top_left);
                        textView.setText(newstr);
                    }
                    else
                        textView.setText(getResources().getString(R.string.top_left));
                    buttons_pressed+=1;
                    break;
                case R.id.top_right_button:
                    if(textView.getText().toString().compareTo("") != 0){
                        String newstr = textView.getText().toString() + ", " + getResources().getString(R.string.top_right);
                        textView.setText(newstr);
                    }
                    else
                        textView.setText(getResources().getString(R.string.top_right));
                    buttons_pressed+=1;
                    break;
                case R.id.center_button:
                    if(textView.getText().toString().compareTo("") != 0){
                        String newstr = textView.getText().toString() + ", " + getResources().getString(R.string.center_string);
                        textView.setText(newstr);
                    }
                    else
                        textView.setText(getResources().getString(R.string.center_string));
                    buttons_pressed+=1;
                    break;
                case R.id.bottom_left_button:
                    if(textView.getText().toString().compareTo("") != 0){
                        String newstr = textView.getText().toString() + ", " + getResources().getString(R.string.bottom_left);
                        textView.setText(newstr);
                    }
                    else
                        textView.setText(getResources().getString(R.string.bottom_left));
                    buttons_pressed+=1;
                    break;
                case R.id.bottom_right_button:
                    if(textView.getText().toString().compareTo("") != 0){
                        String newstr = textView.getText().toString() + ", " + getResources().getString(R.string.bottom_right);
                        textView.setText(newstr);
                    }
                    else
                        textView.setText(getResources().getString(R.string.bottom_right));
                    buttons_pressed+=1;
                    break;
            }
            if (buttons_pressed > pressed_threshold && !serviceStarted){
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
                intent.putExtra("text_for_service", textView.getText().toString());
                getApplicationContext().startService(intent);
                serviceStarted = true;
            }

        }
    }
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var08Service.class);
        stopService(intent);
        serviceStarted = false;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView)findViewById(R.id.text_view);
        navigate = (Button)findViewById(R.id.navigate);
        topLeft = (Button)findViewById(R.id.top_left_button);
        topRight = (Button)findViewById(R.id.top_right_button);
        center = (Button)findViewById(R.id.center_button);
        bottomLeft = (Button)findViewById(R.id.bottom_left_button);
        bottomRight = (Button)findViewById(R.id.bottom_right_button);

        textView.setText("");
        navigate.setOnClickListener(buttonListener);
        topLeft.setOnClickListener(buttonListener);
        topRight.setOnClickListener(buttonListener);
        center.setOnClickListener(buttonListener);
        bottomRight.setOnClickListener(buttonListener);
        bottomLeft.setOnClickListener(buttonListener);

        intentFilter.addAction("practical");



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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var08_main, menu);
        return true;
    }

        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            nrTries = savedInstanceState.getInt("nrTries_cached");
            correctTries = savedInstanceState.getInt("correctTries_cached");
            incorrectTries = savedInstanceState.getInt("incorrectTries_cached");
            Toast.makeText(getApplicationContext(), "nrTries,correctTries,incorrectTries is restored: " + nrTries + " " + correctTries + " " + incorrectTries, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putInt("nrTries_cached", nrTries);
            outState.putInt("correctTries_cached", correctTries);
            outState.putInt("incorrectTries_cached", incorrectTries);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 2016) {
            nrTries  = nrTries + 1;
            if (resultCode == -1)
                correctTries = correctTries + 1;
            else
                incorrectTries = incorrectTries + 1;
            Toast.makeText(this, "The activity returned with tries " + nrTries + " success "+ correctTries+" insuccess "+incorrectTries, Toast.LENGTH_LONG).show();
            textView.setText("");
        }
    }
}
