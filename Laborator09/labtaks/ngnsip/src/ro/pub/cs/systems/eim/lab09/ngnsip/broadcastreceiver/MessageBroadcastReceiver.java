package ro.pub.cs.systems.eim.lab09.ngnsip.broadcastreceiver;

import org.doubango.ngn.events.NgnEventArgs;
import org.doubango.ngn.events.NgnMessagingEventArgs;
import org.doubango.ngn.utils.NgnContentType;
import org.doubango.ngn.utils.NgnStringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import ro.pub.cs.systems.eim.lab09.ngnsip.general.Constants;

public class MessageBroadcastReceiver extends BroadcastReceiver {

	private TextView conversationTextView;

	public MessageBroadcastReceiver(TextView conversationTextView) { 
		this.conversationTextView = conversationTextView; 
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (NgnMessagingEventArgs.ACTION_MESSAGING_EVENT.equals(action)) {
			NgnMessagingEventArgs arguments = intent.getParcelableExtra(NgnEventArgs.EXTRA_EMBEDDED);
			if(arguments == null){
				Log.d(Constants.TAG, "Invalid messaging event arguments");
				return;
			}

			switch(arguments.getEventType()) {
				case INCOMING:
					if(!NgnStringUtils.equals(arguments.getContentType(), NgnContentType.T140COMMAND, true)) {
						
						// TODO: exercise 11b
						// - get the byte array from the arguments using the getPayload() method
						// - if the payload contains actual information, convert it to a string, using the UTF-8 encoding
						// - display the conversation in the graphic unser interface
						// !!! don't forget to handle the UnsupportedEncodingException
						
					}
					break;
				default:
					break;
			}
		}
	}

}
