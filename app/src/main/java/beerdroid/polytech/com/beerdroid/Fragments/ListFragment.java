package beerdroid.polytech.com.beerdroid.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import beerdroid.polytech.com.beerdroid.R;
import beerdroid.polytech.com.beerdroid.Services.IntentServiceDetails;

/**
 * Created by franck on 26/12/17.
 */

public class ListFragment extends Fragment {

    private ListInterface myInterface;

    public interface ListInterface{
        void detailsClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            myInterface = (ListInterface) context;
        } catch(ClassCastException e){
            Log.d("test", "Context must implement HomeInterface");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = (Button) getActivity().findViewById(R.id.queryButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(myInterface != null){
                    myInterface.detailsClick();
                    getDetails();
                }
            }
        });
    }

    private void getDetails(){
        Intent msgIntent = new Intent(this.getContext(), IntentServiceDetails.class);
        msgIntent.putExtra(IntentServiceDetails.PARAM_IN_MSG, IntentServiceDetails.ACTION_VALUE_GET_QUESTION);
        this.getContext().startService(msgIntent);
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "com.mamlambo.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView result = (TextView) getActivity().findViewById(R.id.responseView);
            String text = intent.getStringExtra(IntentServiceDetails.PARAM_OUT_MSG);
            result.setText(text);
        }
    }
}

