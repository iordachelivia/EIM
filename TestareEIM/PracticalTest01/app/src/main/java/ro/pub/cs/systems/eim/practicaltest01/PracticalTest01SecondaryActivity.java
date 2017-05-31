package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PracticalTest01SecondaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        Intent intent = getIntent();

        if(intent!=null && intent.getExtras().containsKey("to_compute")){
            String toParse = intent.getExtras().getString("to_compute");
            String[] tokenized = toParse.split("\\+");
            int result = 0;
            for (String value : tokenized){
                result = result + Integer.parseInt(value);
            }

            intent.putExtra("computed_result", result);
            setResult(RESULT_OK,intent);

        }
        finish();
    }
}
