package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends Activity {
    private Button okButton;
    private Button cancelButton;
    private ButtonListener buttonListener = new ButtonListener();
    private EditText nrClicks ;
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.ok:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel:
                    setResult(RESULT_CANCELED,null);
                    break;
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        nrClicks = (EditText)findViewById(R.id.nr_access);
        okButton = (Button)findViewById(R.id.ok);
        cancelButton = (Button)findViewById(R.id.cancel);

        okButton.setOnClickListener(buttonListener);
        cancelButton.setOnClickListener(buttonListener);

        //GET INTENT
        Intent intent = getIntent();

        if(intent!=null && intent.getExtras().containsKey("nr_clicks")){
            int clicks = intent.getIntExtra("nr_clicks",-1);
            nrClicks.setText(String.valueOf(clicks));
        }


    }

}
