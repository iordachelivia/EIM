package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
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
    private Button addButton;
    private Button computeButton;
    private EditText nextTerm;
    private EditText allTerms;
    private boolean firstClick = true;
    private String cachedString;
    private int cachedResult;
    private boolean started = false;

    private ButtonListener buttonListener = new ButtonListener();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }
    private class ButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.add_button:
                    String newval= nextTerm.getText().toString();
                    nextTerm.setText("");
                    String old = allTerms.getText().toString();

                    try{
                        int value = Integer.parseInt(newval);
                    }
                    catch(NumberFormatException e){
                        Log.d("Button Listener","Number format exception");
                        return;//not valid number
                    }
                    if(old.compareTo("") == 0)
                        allTerms.setText(newval);
                    else
                        allTerms.setText(old + "+"+newval);
                    break;
                case R.id.compute_button:
                    if(firstClick || cachedString.compareTo(allTerms.getText().toString()) != 0){
                        //lanseaza a 2a activitate
                        cachedString=allTerms.getText().toString();
                        firstClick=false;
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                        intent.putExtra("to_compute", allTerms.getText().toString());
                        startActivityForResult(intent, 2016);

                    }
                    else
                    {
                        //show cached result
                        Toast.makeText(getApplicationContext(),"Result is cached "+ Integer.toString(cachedResult),Toast.LENGTH_LONG).show();
                    }

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

        computeButton = (Button)findViewById(R.id.compute_button);
        addButton = (Button)findViewById(R.id.add_button);
        nextTerm = (EditText)findViewById(R.id.next_term_edit);
        allTerms = (EditText)findViewById(R.id.all_terms_edit);

        addButton.setOnClickListener(buttonListener);
        computeButton.setOnClickListener(buttonListener);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2016) {
            int res =data.getIntExtra("computed_result",-1);
            cachedResult = res;
            Toast.makeText(this, "The activity returned with the result " + res, Toast.LENGTH_LONG).show();
        }

        if(cachedResult > 10 && !started){
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
            intent.putExtra("sum",cachedResult);
            getApplicationContext().startService(intent);
            started = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("cached_result", cachedResult);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        cachedResult = savedInstanceState.getInt("cached_result");
        Toast.makeText(getApplicationContext(), "cachedSum is restored: " + cachedResult, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
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
}
