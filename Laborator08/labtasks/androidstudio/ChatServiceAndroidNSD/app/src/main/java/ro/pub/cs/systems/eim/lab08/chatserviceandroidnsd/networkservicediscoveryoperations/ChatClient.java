package ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.networkservicediscoveryoperations;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.general.Constants;
import ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.general.Utilities;
import ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.model.Message;
import ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.view.ChatActivity;
import ro.pub.cs.systems.eim.lab08.chatserviceandroidnsd.view.ChatConversationFragment;

public class ChatClient {

    private Socket socket = null;

    private Context context = null;

    private SendThread sendThread = null;
    private ReceiveThread receiveThread = null;

    private BlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(Constants.MESSAGE_QUEUE_CAPACITY);

    private List<Message> conversationHistory = new ArrayList<Message>();

    public ChatClient(Context context, final String host, final int port) {
        this.context = context;

        try {
            socket = new Socket(host, port);
            Log.d(Constants.TAG, "A socket has been created on " + socket.getInetAddress() + ":" + socket.getLocalPort());
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred while creating the socket: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
        if (socket != null) {
            startThreads();
        }
    }

    public ChatClient(Context context, Socket socket) {
        this.context = context;

        this.socket = socket;
        if (socket != null) {
            startThreads();
        }
    }

    public void sendMessage(String message) {
        try {
            messageQueue.put(message);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.TAG, "An exception has occurred: " + interruptedException.getMessage());
            if (Constants.DEBUG) {
                interruptedException.printStackTrace();
            }
        }
    }

    private class SendThread extends Thread {

        @Override
        public void run() {

            PrintWriter printWriter = Utilities.getWriter(socket);
            if (printWriter != null) {
                try {
                    Log.d(Constants.TAG, "Sending messages to " + socket.getInetAddress() + ":" + socket.getLocalPort());

                    // TODO: exercise 6
                    // iterate while the thread is not yet interrupted
                    while (!Thread.currentThread().isInterrupted()) {
                        // - get the content (a line) from the messageQueue, if available, using the take() method
                        String content = null;
                        try{
                            content = messageQueue.take();
                            Log.d(Constants.TAG, "Sending the message " + content);
                            // - if the content is not null
                            if (content != null){
                                //   - send the content to the PrintWriter, as a line
                                printWriter.println(content);
                                printWriter.flush();
                                //   - create a Message instance, with the content received and Constants.MESSAGE_TYPE_SENT as message type
                                Message message = new Message(content, Constants.MESSAGE_TYPE_SENT);
                                //   - add the message to the conversationHistory
                                conversationHistory.add(message);
                                //   - if the ChatConversationFragment is visible (query the FragmentManager for the Constants.FRAGMENT_TAG tag)
                                if (context != null) {
                                    ChatActivity chatActivity = (ChatActivity) context;
                                    FragmentManager fragmentManager = chatActivity.getFragmentManager();
                                    Fragment fragment = fragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG);
                                    if (fragment instanceof ChatConversationFragment && fragment.isVisible()) {
                                        ChatConversationFragment chatConversationFragment = (ChatConversationFragment) fragment;
                                        //   append the message to the graphic user interface
                                        chatConversationFragment.appendMessage(message);
                                    }
                                }

                            }
                        }catch(InterruptedException exception){
                            Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
                            if (Constants.DEBUG) {
                                exception.printStackTrace();
                            }
                        }

                    }

                } catch (Exception exception) {
                    Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
            }

            Log.i(Constants.TAG, "Send Thread ended");

        }

        public void stopThread() {
            interrupt();
        }

    }

    private class ReceiveThread extends Thread {

        @Override
        public void run() {

            BufferedReader bufferedReader = Utilities.getReader(socket);
            if (bufferedReader != null) {
                try {
                    Log.d(Constants.TAG, "Reading messages from " + socket.getInetAddress() + ":" + socket.getLocalPort());

                    // TODO: exercise 7
                    // iterate while the thread is not yet interrupted
                    while (!Thread.currentThread().isInterrupted()) {
                        // - receive the content (a line) from the bufferedReader, if available
                        String content = null;
                        try{
                            content = bufferedReader.readLine();
                            Log.d(Constants.TAG, "Reading the message " + content);
                            // - if the content is not null
                            if (content !=null) {
                                //   - create a Message instance, with the content received and Constants.MESSAGE_TYPE_RECEIVED as message type
                                Message message = new Message(content, Constants.MESSAGE_TYPE_RECEIVED);
                                //   - add the message to the conversationHistory
                                conversationHistory.add(message);
                                //   - if the ChatConversationFragment is visible (query the FragmentManager for the Constants.FRAGMENT_TAG tag)
                                //   append the message to the graphic user interface
                                if (context != null) {
                                    ChatActivity chatActivity = (ChatActivity) context;
                                    FragmentManager fragmentManager = chatActivity.getFragmentManager();
                                    Fragment fragment = fragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG);
                                    if (fragment instanceof ChatConversationFragment && fragment.isVisible()) {
                                        ChatConversationFragment chatConversationFragment = (ChatConversationFragment) fragment;
                                        //   append the message to the graphic user interface
                                        chatConversationFragment.appendMessage(message);
                                    }
                                }
                            }
                        }catch(IOException exception){
                            Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
                            if (Constants.DEBUG) {
                                exception.printStackTrace();
                            }
                        }
                    }

                } catch (Exception exception) {
                    Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
            }

            Log.i(Constants.TAG, "Receive Thread ended");

        }

        public void stopThread() {
            interrupt();
        }

    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setConversationHistory(List<Message> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    public List<Message> getConversationHistory() {
        return conversationHistory;
    }

    public void startThreads() {
        sendThread = new SendThread();
        sendThread.start();

        receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    public void stopThreads() {

        sendThread.stopThread();
        receiveThread.stopThread();

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred while closing the socket: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}
