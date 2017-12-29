package beerdroid.polytech.com.beerdroid.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import beerdroid.polytech.com.beerdroid.Fragments.ListFragment;

/**
 * Created by franck on 26/12/17.
 */

public class IntentServiceDetails extends IntentService {

    public static final String PARAM_IN_MSG = "action";
    public static final String PARAM_OUT_MSG = "action";
    public static final String ACTION_VALUE_GET_DETAILS = "getDetails";

    public IntentServiceDetails(){
        super("IntentServiceDetails");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent.getStringExtra(PARAM_IN_MSG).equals(ACTION_VALUE_GET_DETAILS)){
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ListFragment.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(PARAM_OUT_MSG, getDetails());
            sendBroadcast(broadcastIntent);
        }
    }

    public String getDetails(){
        return "This is a test question";
    }
}
