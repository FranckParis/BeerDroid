package beerdroid.polytech.com.beerdroid.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import beerdroid.polytech.com.beerdroid.Adapters.BeerAdapter;
import beerdroid.polytech.com.beerdroid.Objects.Beer;
import beerdroid.polytech.com.beerdroid.R;
import beerdroid.polytech.com.beerdroid.Services.IntentServiceDetails;

/**
 * Created by franck on 26/12/17.
 */

public class ListFragment extends Fragment {
    private String url = "https://api.punkapi.com/v2/beers";
    private ListView beersList;

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

        this.beersList = (ListView) getActivity().findViewById(R.id.beer_list);
        printBeers();

//        Button button = (Button) getActivity().findViewById(R.id.queryButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(myInterface != null){
//                    myInterface.detailsClick();
//                    getDetails();
//                }
//            }
//        });
    }

    private void getDetails(){
        Intent msgIntent = new Intent(this.getContext(), IntentServiceDetails.class);
        msgIntent.putExtra(IntentServiceDetails.PARAM_IN_MSG, IntentServiceDetails.ACTION_VALUE_GET_DETAILS);
        this.getContext().startService(msgIntent);
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "com.mamlambo.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
//            TextView result = (TextView) getActivity().findViewById(R.id.responseView);
//            String text = intent.getStringExtra(IntentServiceDetails.PARAM_OUT_MSG);
//            result.setText(text);
        }
    }

    private void printBeers(){
        final Context c = getActivity().getApplicationContext();
        final List<Beer> beers = new ArrayList<Beer>();
        RequestQueue queue = Volley.newRequestQueue(c);
        final Beer defaultBeer = new Beer(0, "Beer", "It's a beer", "", "It's really a beer", "", "", "", "", "", "");

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0 ; i < response.length()  ; i++){
                                JSONObject jsBeer = response.getJSONObject(i);
                                int beerId = jsBeer.getInt("id");
                                String beerName = jsBeer.getString("name");
                                String beerTagline = jsBeer.getString("tagline");
                                String beerFirst_brewed = jsBeer.getString("first_brewed");
                                String beerDescription = jsBeer.getString("description");
                                String beerImage_url = jsBeer.getString("image_url");
                                String beerBrewers_tips = jsBeer.getString("brewers_tips");
                                String beerContributed_by = jsBeer.getString("contributed_by");

                                JSONArray jsMalt = jsBeer.getJSONObject("ingredients").getJSONArray("malt");
                                StringBuilder ingredientsMaltBuilder = new StringBuilder();
                                for(int j = 0 ; j < jsMalt.length() ; j++){
                                    ingredientsMaltBuilder.append(jsMalt.getJSONObject(j).getString("name")).append("\n");
                                }
                                String ingredientsMalt = ingredientsMaltBuilder.toString();

                                JSONArray jsHops = jsBeer.getJSONObject("ingredients").getJSONArray("hops");
                                StringBuilder ingredientsHopsBuilder = new StringBuilder();
                                for(int j = 0 ; j < jsHops.length() ; j++){
                                    ingredientsHopsBuilder.append(jsHops.getJSONObject(j).getString("name")).append("\n");
                                }
                                String ingredientHops = ingredientsHopsBuilder.toString();

                                String ingredientYeast = jsBeer.getJSONObject("ingredients").getString("yeast");

                                beers.add(new Beer(beerId, beerName, beerTagline, beerFirst_brewed, beerDescription, beerImage_url, beerBrewers_tips, beerContributed_by, ingredientsMalt, ingredientHops, ingredientYeast));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            beers.add(defaultBeer);
                        }

                        BeerAdapter adapter = new BeerAdapter(c, beers);
                        beersList.setAdapter(adapter);

                        beersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(myInterface != null){
                                    myInterface.detailsClick();
                                    getDetails();
                                }
                            }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        beers.add(defaultBeer);

                        BeerAdapter adapter = new BeerAdapter(c, beers);
                        beersList.setAdapter(adapter);
                    }
                });
        queue.add(jsArrRequest);
    }
}

