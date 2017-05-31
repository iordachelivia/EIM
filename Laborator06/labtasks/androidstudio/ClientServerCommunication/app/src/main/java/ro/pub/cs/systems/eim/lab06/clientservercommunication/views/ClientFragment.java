package ro.pub.cs.systems.eim.lab06.clientservercommunication.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import ro.pub.cs.systems.eim.lab06.clientservercommunication.R;
import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Constants;
import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Utilities;

public class ClientFragment extends Fragment {

    private EditText serverAddressEditText, serverPortEditText;
    private TextView serverMessageTextView;
    private Button displayMessageButton;

    private class ClientAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Socket socket = null;
            try {

                // TODO: exercise 6b
                // - get the connection parameters (serverAddress and serverPort from parameters - on positions 0 and 1)
                String serverAddress = params[0];
                int serverPort = Integer.parseInt(params[1]);
                // - open a socket to the server
                socket = new Socket(serverAddress,serverPort);
                // - get the BufferedReader object in order to read from the socket (use Utilities.getReader())
                BufferedReader bufferedReader = Utilities.getReader(socket);
                String line;
                // - while the line that was read is not null (EOF was not sent), append the content to serverMessageTextView
                while((line = bufferedReader.readLine())!=null) {
                    // by publishing the progress - with the publishProgress(...) method - to the UI thread
                    publishProgress(line);
                }
                socket.close();
                // - close the socket to the server

            } catch (Exception exception) {
                Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
                if (Constants.DEBUG) {
                    exception.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // TODO: exercise 6b
            // - reset the content of serverMessageTextView
            serverMessageTextView.setText("");
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            // TODO: exercise 6b
            // - append the content to serverMessageTextView
            String prev = serverMessageTextView.getText().toString();
            String my = prev + progress[0] + '\n';
            Log.v(Constants.TAG, "PREVIOUS MESSAGE + CURR " + my);
            serverMessageTextView.setText(my);
        }

        @Override
        protected void onPostExecute(Void result) { }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        return inflater.inflate(R.layout.fragment_client, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        serverAddressEditText = (EditText)getActivity().findViewById(R.id.server_address_edit_text);
        serverPortEditText = (EditText)getActivity().findViewById(R.id.server_port_edit_text);
        serverMessageTextView = (TextView)getActivity().findViewById(R.id.server_message_text_view);

        displayMessageButton = (Button)getActivity().findViewById(R.id.display_message_button);
        displayMessageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientAsyncTask clientAsyncTask = new ClientAsyncTask();
                clientAsyncTask.execute(serverAddressEditText.getText().toString(), serverPortEditText.getText().toString());
            }
        });

    }

}