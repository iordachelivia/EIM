package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String url;
    private WebView urlTextView;

    private Socket socket;

    public ClientThread(
            String address,
            int port,
            String url,
            WebView urlTextView) {
        this.address = address;
        this.port = port;
        this.url = url;
        this.urlTextView = urlTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                printWriter.println(url);
                printWriter.flush();

                String htmlData;
                String content="";
                while ((htmlData = bufferedReader.readLine()) != null) {
                    final String finalHtml = htmlData;
                    /*urlTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            urlTextView.append(finalHtml + "\n");
                        }
                    });*/
                    content = content + finalHtml;
                }
                urlTextView.loadDataWithBaseURL(
                        this.url,
                        content,
                        Constants.MIME_TYPE,
                        Constants.CHARACTER_ENCODING,
                        null);
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}
