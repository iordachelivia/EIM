package ro.pub.cs.systems.eim.practicaltest021;

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
    private EditText editOne, editTwo;
    private Button addOne, addTwo;
    private EditText resultEdit;
    private Button multiplyButton;
    private boolean isServiceRunning = false;
    private IntentFilter intentFilter = new IntentFilter();

    private ButtonListener buttonListener = new ButtonListener();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.add_button_one:
                    int before = Integer.parseInt(editOne.getText().toString());
                    before = before +1;
                    editOne.setText(String.valueOf(before));
                    break;
                case R.id.add_button_two:
                    int before1 = Integer.parseInt(editTwo.getText().toString());
                    before = before1 +1;
                    editTwo.setText(String.valueOf(before));
                    break;
                case R.id.multiply_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra("firstNumber", Integer.parseInt(editOne.getText().toString()));
                    intent.putExtra("secondNumber", Integer.parseInt(editTwo.getText().toString()));
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
        addOne = (Button)findViewById(R.id.add_button_one);
        addTwo =(Button)findViewById(R.id.add_button_two);
        resultEdit = (EditText)findViewById(R.id.result_edit);
        multiplyButton = (Button)findViewById(R.id.multiply_button);

        editOne.setText("0");
        editTwo.setText("0");

        addOne.setOnClickListener(buttonListener);
        addTwo.setOnClickListener(buttonListener);
        multiplyButton.setOnClickListener(buttonListener);

        intentFilter.addAction("livia");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 2016) {
            int result = intent.getIntExtra("result_multiply",-1);
            resultEdit.setText(String.valueOf(result));
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();

            if(!isServiceRunning && result > 20){
                Intent aux = new Intent(getApplicationContext(), PracticalTest01Service.class);
                aux.putExtra("firstNumber", Integer.parseInt(editOne.getText().toString()));
                aux.putExtra("secondNumber", Integer.parseInt(editTwo.getText().toString()));
                getApplicationContext().startService(aux);
                isServiceRunning = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
    }
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        editOne.setText(savedInstanceState.getString("first_cached"));
        editTwo.setText(savedInstanceState.getString("second_cached"));
        resultEdit.setText(savedInstanceState.getString("result_cached"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("result_cached", resultEdit.getText().toString());
        outState.putString("first_cached", editOne.getText().toString());
        outState.putString("second_cached", editTwo.getText().toString());
    }

}
