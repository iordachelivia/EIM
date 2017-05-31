package ro.pub.cs.systems.eim.lab02.activitylifecyclemonitor.graphicuserinterface;

import android.app.ActionBar.LayoutParams;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import ro.pub.cs.systems.eim.lab02.activitylifecyclemonitor.R;
import ro.pub.cs.systems.eim.lab02.activitylifecyclemonitor.general.Constants;
import ro.pub.cs.systems.eim.lab02.activitylifecyclemonitor.general.Utilities;

public class LifecycleMonitorActivity extends Activity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        @SuppressLint("InflateParams")
        public void onClick(View view) {
            EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
            EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);

            if(((Button)view).getText().toString().equals(getResources().getString(R.string.ok_button_content))) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                View popupContent = null;
                if (Utilities.allowAccess(getApplicationContext(), username, password)) {
                    popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_success, null);
                } else {
                    popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_fail, null);
                }
                final PopupWindow popupWindow = new PopupWindow(popupContent, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
                dismissButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }

            if(((Button)view).getText().toString().equals(getResources().getString(R.string.cancel_button_content))) {
                usernameEditText.setText(getResources().getString(R.string.empty));
                passwordEditText.setText(getResources().getString(R.string.empty));
            }
        }

    }


    // TODO EX 5
    @Override
    protected  void onRestart(){
        // Call parent method
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    @Override
    protected  void onStart(){
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
    }

    @Override
    protected  void onPause(){
        super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_monitor);

        Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);

        /* TODO EX 7
        Să se modifice mesajul din metoda onCreate(), astfel încât să
         se indice dacă activitatea a mai fost lansată în execuție anterior
         sau nu (dacă există o stare a activității care trebuie restaurată).
         */
        if (savedInstanceState == null)
        {
            Log.d(Constants.TAG, "onCreate() method was invoked without a previous state");
        }
        else
        {
            Log.d(Constants.TAG, "onCreate() method was invoked with a previous state");
        }

        /* Să se transfere comportamentul de restaurare a stării pe metoda onCreate() și să se identifice diferențele de implementare
        TODO 12
         */
        /*if(savedInstanceState.containsKey(Constants.USERNAME_EDIT_TEXT)) {
            // get control
            EditText username = (EditText)findViewById(R.id.username_edit_text);
            //set text
            username.setText(savedInstanceState.getString(Constants.USERNAME_EDIT_TEXT));
        }
        if(savedInstanceState.containsKey(Constants.PASSWORD_EDIT_TEXT)) {
            // get control
            EditText password = (EditText)findViewById(R.id.password_edit_text);
            //set text
            password.setText(savedInstanceState.getString(Constants.PASSWORD_EDIT_TEXT));
        }
        if(savedInstanceState.containsKey(Constants.REMEMBER_ME_CHECKBOX)) {
            // get control

            CheckBox check = (CheckBox)findViewById(R.id.remember_me_checkbox);
            //set text
            check.setChecked(savedInstanceState.getBoolean(Constants.REMEMBER_ME_CHECKBOX));
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lifecycle_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected  void onRestoreInstanceState(Bundle instanceState){
        super.onRestoreInstanceState(instanceState);

        /*
        TODO 11
        Să se implementeze metoda onRestoreInstanceState() astfel încât să se restaureze starea elementelor grafice.
         */
        if(instanceState.containsKey(Constants.USERNAME_EDIT_TEXT)) {
            // get control
            EditText username = (EditText)findViewById(R.id.username_edit_text);
            //set text
            username.setText(instanceState.getString(Constants.USERNAME_EDIT_TEXT));
        }
        if(instanceState.containsKey(Constants.PASSWORD_EDIT_TEXT)) {
            // get control
            EditText password = (EditText)findViewById(R.id.password_edit_text);
            //set text
            password.setText(instanceState.getString(Constants.PASSWORD_EDIT_TEXT));
        }
        if(instanceState.containsKey(Constants.REMEMBER_ME_CHECKBOX)) {
            // get control

            CheckBox check = (CheckBox) findViewById(R.id.remember_me_checkbox);
            //set text
            check.setChecked(instanceState.getBoolean(Constants.REMEMBER_ME_CHECKBOX));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Get checkbox
        CheckBox checkBox = (CheckBox)findViewById(R.id.remember_me_checkbox);
        // get information
        EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);

        if (checkBox.isChecked()){
            // TODO 10
            //în condițiile în care este bifat elementul grafic de tip CheckBox,
            // să se salveze informațiile din interfața cu utilizatorul.
            savedInstanceState.putString(Constants.USERNAME_EDIT_TEXT, usernameEditText.getText().toString());
            savedInstanceState.putString(Constants.PASSWORD_EDIT_TEXT, passwordEditText.getText().toString());
            savedInstanceState.putBoolean(Constants.REMEMBER_ME_CHECKBOX, checkBox.isChecked());
        }
    }
}
