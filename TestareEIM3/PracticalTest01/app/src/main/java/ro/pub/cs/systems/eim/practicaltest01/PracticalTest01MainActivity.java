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
    private EditText editOne,editTwo,resultOne,resultTwo;
    private Button addOne,addTwo,navigate;
    private ButtonListener buttonListener = new ButtonListener();
    private boolean serviceStarted = false;
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
                case R.id.add_one:
                    String old = resultOne.getText().toString();
                    old = old + " " + editOne.getText().toString();
                    resultOne.setText(old);
                    editOne.setText("");
                    if (resultOne.getText().toString().length()>10) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                        intent.putExtra("resultedString", resultOne.getText().toString()+" "+resultTwo.getText().toString());
                        getApplicationContext().startService(intent);
                        serviceStarted = true;
                    }
                    break;
                case R.id.add_two:
                    String old1 = resultTwo.getText().toString();
                    old = old1 + " " + editTwo.getText().toString();
                    resultTwo.setText(old);
                    editTwo.setText("");
                    break;
                case R.id.navigate:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra("firstString", resultOne.getText().toString());
                    intent.putExtra("secondString", resultTwo.getText().toString());
                    startActivityForResult(intent, 2016);
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
        addOne = (Button)findViewById(R.id.add_one);
        addTwo = (Button)findViewById(R.id.add_two);

        resultOne = (EditText)findViewById(R.id.result_one);
        resultTwo = (EditText)findViewById(R.id.result_two);

        resultOne.setText("");
        resultTwo.setText("");

        navigate = (Button)findViewById(R.id.navigate);

        addOne.setOnClickListener(buttonListener);
        addTwo.setOnClickListener(buttonListener);
        navigate.setOnClickListener(buttonListener);

        intentFilter.addAction("livia");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 2016) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String first = savedInstanceState.getString("firstString");
        String second = savedInstanceState.getString("secondString");
        resultOne.setText(first);
        resultTwo.setText(second);
        Toast.makeText(getApplicationContext(), "results are restored: " + first + " "+second, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("firstString", resultOne.getText().toString());
        outState.putString("secondString", resultTwo.getText().toString());

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
