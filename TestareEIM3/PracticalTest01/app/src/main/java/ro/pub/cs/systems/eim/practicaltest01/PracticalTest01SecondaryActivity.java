package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.w3c.dom.EntityReference;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    private EditText stringOne, stringTwo, resultString;
    private CheckBox capitalize;
    private Button concatenate;
    private Button okButton,cancelButton;
    private ButtonListener buttonListener = new ButtonListener();



    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.ok:
                    setResult(RESULT_OK, null);
                    finish();
                    break;
                case R.id.cancel:
                    setResult(RESULT_CANCELED,null);
                    finish();
                    break;
                case R.id.concatenate:
                    String concated = stringOne.getText().toString() + " " + stringTwo.getText().toString();
                    resultString.setText(concated);
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_tes01_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stringOne = (EditText)findViewById(R.id.first);
        stringTwo = (EditText)findViewById(R.id.second);
        resultString = (EditText)findViewById(R.id.result);

        capitalize = (CheckBox)findViewById(R.id.capitalize);
        concatenate = (Button)findViewById(R.id.concatenate);
        okButton = (Button)findViewById(R.id.ok);
        cancelButton = (Button)findViewById(R.id.cancel);

        concatenate.setOnClickListener(buttonListener);
        okButton.setOnClickListener(buttonListener);
        cancelButton.setOnClickListener(buttonListener);

        capitalize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String first = stringOne.getText().toString();
                    String[] tokenized = first.split(" ");
                    String constructed = "";
                    for(String one : tokenized){
                        constructed = constructed + " " + one.toUpperCase();
                    }
                    stringOne.setText(constructed);

                    first = stringTwo.getText().toString();
                    tokenized = first.split(" ");
                    constructed = "";
                    for(String one : tokenized){
                        constructed = constructed + " "+one.toUpperCase();
                    }
                    stringTwo.setText(constructed);
                }
                else{
                    String first = stringOne.getText().toString();
                    String[] tokenized = first.split(" ");
                    String constructed = "";
                    for(String one : tokenized){
                        constructed = constructed + " " +one.toLowerCase();
                    }
                    stringOne.setText(constructed);

                    first = stringTwo.getText().toString();
                    tokenized = first.split(" ");
                    constructed = "";
                    for(String one : tokenized){
                        constructed = constructed + " " +one.toLowerCase();
                    }
                    stringTwo.setText(constructed);
                }
            }
        }
        );
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("firstString") && intent.getExtras().containsKey("secondString")) {
            stringOne.setText(intent.getStringExtra("firstString"));
            stringTwo.setText(intent.getStringExtra("secondString"));
        }

    }

}
