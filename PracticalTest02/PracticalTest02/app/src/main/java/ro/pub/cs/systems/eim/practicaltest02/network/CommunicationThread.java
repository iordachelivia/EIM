package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;
import ro.pub.cs.systems.eim.practicaltest02.model.WeatherForecastInformation;

public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);

                if (bufferedReader != null && printWriter != null) {
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");

                    String url = bufferedReader.readLine();


                    if (url != null && !url.isEmpty()) {
                        if (url.contains("bad")) {
                            printWriter.println("RESTRICTED!");
                            printWriter.flush();
                        } else {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice..." + url);
                            HttpClient httpClient = new DefaultHttpClient();

                            //HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
                            HttpGet httpGet = new HttpGet(url+ "?");

                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String pageSourceCode = httpClient.execute(httpGet, responseHandler);
                            if (pageSourceCode != null) {
                                printWriter.println(pageSourceCode);
                                printWriter.flush();
                            } else {
                                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error getting the information from the webservice!");
                            }


                        }
                    } else {
                        Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client url)!");
                    }

                    socket.close();

                }
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}
