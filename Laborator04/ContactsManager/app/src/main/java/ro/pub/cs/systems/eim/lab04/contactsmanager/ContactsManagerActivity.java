package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    /* buttons */
    Button showHideAdditionalButton = null;
    Button saveButton = null;
    Button cancelButton = null;

    /* edittexts */
    EditText name = null;
    EditText phoneNumber = null;
    EditText email = null;
    EditText address = null;
    EditText job = null;
    EditText company = null;
    EditText website = null;
    EditText im = null;

    /* invisible layout */
    LinearLayout additionalFields = null;

    /*listeners*/
    private ShowHideButtonListener showHideButtonListener = new ShowHideButtonListener();
    private SaveButtonListener saveButtonListener = new SaveButtonListener();
    private CancelButtonListener cancelButtonListener = new CancelButtonListener();

    /*
    butonul Show Additional Details / Hide Additional Details -
    afișează / ascunde al doilea container în funcție de starea curentă ,
    modificând corespunzător textul afișat pe buton. (Hint: atributul visibility
    al containerului, resepctiv metoda setVisibility() a clasei Java împreună cu
    constantele View.VISIBLE, View.GONE).
     */
    private class ShowHideButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            // CONTAINER VISIBLE
            if(additionalFields.getVisibility() == View.VISIBLE){
                //modify visibility and text
                additionalFields.setVisibility(View.INVISIBLE);
                showHideAdditionalButton.setText(getResources().getString(R.string.show_additional_fields));
            }
            else if (additionalFields.getVisibility() == View.INVISIBLE){
                additionalFields.setVisibility(View.VISIBLE);
                showHideAdditionalButton.setText(getResources().getString(R.string.hide_additional_fields));
            }

        }
    }

    private class SaveButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            // Get strings
            String string_name = name.getText().toString();
            String string_phone = phoneNumber.getText().toString();
            String string_email = email.getText().toString();
            String string_address = address.getText().toString();
            String string_job = job.getText().toString();
            String string_company = company.getText().toString();
            String string_website = website.getText().toString();
            String string_im = im.getText().toString();

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            if (string_name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, string_name);
            }
            if (string_phone != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, string_phone);
            }
            if (string_email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, string_email);
            }
            if (string_address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, string_address);
            }
            if (string_job != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, string_job);
            }
            if (string_company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, string_company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (string_website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, string_website);
                contactData.add(websiteRow);
            }
            if (string_im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, string_im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            startActivityForResult(intent, 1);
        }
    }

    private class CancelButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /* TODO 1
       Get all references
       */
        /*buttons */
        showHideAdditionalButton = (Button)findViewById(R.id.show_hide_additional);
        saveButton = (Button)findViewById(R.id.save_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        /*edittexts */
        name = (EditText)findViewById(R.id.name);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        email = (EditText)findViewById(R.id.phone_number);
        address = (EditText)findViewById(R.id.address);
        job = (EditText)findViewById(R.id.job);
        company = (EditText)findViewById(R.id.company);
        website = (EditText)findViewById(R.id.website);
        im = (EditText)findViewById(R.id.im);

        /*linearLayout*/
        additionalFields = (LinearLayout)findViewById(R.id.additional_fields);

        // Add listeners
        showHideAdditionalButton.setOnClickListener(showHideButtonListener);
        saveButton.setOnClickListener(saveButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneNumber.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 1:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_manager, menu);
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
