package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var08SecondaryActivity extends AppCompatActivity {
    private TextView textView;
    private Button verify,cancel;
    private ButtonListener buttonListener = new ButtonListener();
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.verify:
                    setResult(RESULT_OK,null);
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
        setContentView(R.layout.activity_practical_test01_var08_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView)findViewById(R.id.textview_second);
        verify = (Button)findViewById(R.id.verify);
        cancel=(Button)findViewById(R.id.cancel);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("buttons_pressed")) {
            String text = intent.getStringExtra("buttons_pressed");
            textView.setText(text);
        }

        verify.setOnClickListener(buttonListener);
        cancel.setOnClickListener(buttonListener);
    }

}
