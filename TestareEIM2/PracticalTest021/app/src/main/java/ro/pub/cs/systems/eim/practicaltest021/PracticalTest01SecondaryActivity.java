package ro.pub.cs.systems.eim.practicaltest021;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PracticalTest01SecondaryActivity extends Activity {
    private int firstNumber, secondNumber;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("firstNumber") && intent.getExtras().containsKey("secondNumber")) {
            firstNumber = intent.getIntExtra("firstNumber", -1);
            secondNumber = intent.getIntExtra("secondNumber", -1);

            result = firstNumber * secondNumber;
            intent.putExtra("result_multiply", result);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
