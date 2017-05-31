package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {
    private EditText phoneNumberEditText = null;
    private NumberButtonListener numberButtonListener = new NumberButtonListener();
    private BackspaceListener backspaceListener = new BackspaceListener();
    private CallListener callListener = new CallListener();
    private HangupListener hangupListener = new HangupListener();
    private ContactsListener contactsListener = new ContactsListener();

    /*
    TODO 5a
    pentru butoanele ce conțin cifre sau caracterele * / #,
    se va adăuga simbolul corespunzător la numărul de telefon care se dorește format;
     */
    private class NumberButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view){
            // get phone number text. append number to it. set text
            String oldtext = phoneNumberEditText.getText().toString();
            oldtext = oldtext + ((Button)view).getText().toString();

            phoneNumberEditText.setText(oldtext);
        }
    }
    /*
    TODO 5B
    pentru butonul de corecție, se va șterge ultimul caracter (în cazul în care numărul de telefon nu este vid);
     */

    private class BackspaceListener implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            // get phone number text. erase last elem. set text
            String oldtext = phoneNumberEditText.getText().toString();
            if (oldtext.length() > 0){
                oldtext = oldtext.substring(0, oldtext.length() - 1);
                phoneNumberEditText.setText(oldtext);
            }
        }
    }
    /*
    TODO 5C
    pentru butonul de apel, se va invoca intenția care realizează legătura telefonică

    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:"+phoneNumber));
    startActivity(intent);
    Pentru a putea apela, în fișierul AndroidManifest.xml trebuie să se specifice o permisiune explicită în acest sens:
<uses-permission android:name=“android.permission.CALL_PHONE” />
     */
    private class CallListener implements View.OnClickListener{
        @Override
        public  void onClick(View view){
            //GET PHONE NUMBER
            String phoneNumber = phoneNumberEditText.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            //Check if permissions
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }
    /*
    TODO 5D
    pentru butonul de oprire, se va închide activitatea (se va apela metoda finish());
     */
    private class HangupListener implements  View.OnClickListener{
        @Override
        public  void  onClick(View view){
            finish();
        }
    }

    private class ContactsListener implements  View.OnClickListener{
        @Override
        public  void  onClick(View view){
            //get phonenumber
            String phoneNumber = phoneNumberEditText.getText().toString();

            if (phoneNumber.length() > 0) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //GET PHONE DIALER TEXT
        phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);

        // Set listeners for each number button (plus * and #)
        for(int i = 0; i < Constants.buttonIds.length; i++){
            Button button = (Button)findViewById(Constants.buttonIds[i]);
            button.setOnClickListener(numberButtonListener);
        }

        // Set listener for backspace
        ImageButton backspace = (ImageButton)findViewById(R.id.backspace_button);
        backspace.setOnClickListener(backspaceListener);

        // Set listener for call and hangup and contacts
        ImageButton call = (ImageButton)findViewById(R.id.call_button);
        call.setOnClickListener(callListener);
        ImageButton hang = (ImageButton)findViewById(R.id.hangup_button);
        hang.setOnClickListener(hangupListener);
        ImageButton contacts = (ImageButton)findViewById(R.id.contacts_button);
        contacts.setOnClickListener(contactsListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone_dialer, menu);
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
