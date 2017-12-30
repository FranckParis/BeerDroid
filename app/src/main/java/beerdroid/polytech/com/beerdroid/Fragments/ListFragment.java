package beerdroid.polytech.com.beerdroid.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import beerdroid.polytech.com.beerdroid.Adapters.BeerAdapter;
import beerdroid.polytech.com.beerdroid.Listeners.EndlessScrollListener;
import beerdroid.polytech.com.beerdroid.Objects.Beer;
import beerdroid.polytech.com.beerdroid.R;

/**
 * Created by franck on 26/12/17.
 */

public class ListFragment extends Fragment {
    private int page;
    private ListView beersList;
    private List<Beer> beers;
    BeerAdapter adapter;

    private ListInterface myInterface;

    public interface ListInterface{
        void detailsClick(int pos, List<Beer> beers);
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
            Log.d("test", "Context must implement ListInterface");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.beers = new ArrayList<Beer>();
        this.page = 1;
        this.beersList = (ListView) getActivity().findViewById(R.id.beer_list);

        // Create and print first list of beers
        printBeers(false);

        // Attach the listener to the AdapterView
        this.beersList.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    private void loadNextDataFromApi(int page){
        this.page = page;
        printBeers(true);
    }

    private void printBeers(final boolean addMore){
        final Context c = getActivity().getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(c);
        final Beer defaultBeer = new Beer(0, "Beer", "It's a beer", "", "It's really a beer", "", "", "", "", "", "");
        String url = "https://api.punkapi.com/v2/beers?page=" + this.page;

        final RelativeLayout progressBar = (RelativeLayout) getActivity().findViewById(R.id.loadMoreItemsProgressBar);
        final RelativeLayout progressBarStart = (RelativeLayout) getActivity().findViewById(R.id.loadItemsProgressBar);
        progressBar.setVisibility(View.VISIBLE);

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
                                    ingredientsMaltBuilder.append(jsMalt.getJSONObject(j).getString("name"));
                                    if(j < jsMalt.length()) ingredientsMaltBuilder.append("\n");
                                }
                                String ingredientsMalt = ingredientsMaltBuilder.toString();

                                JSONArray jsHops = jsBeer.getJSONObject("ingredients").getJSONArray("hops");
                                StringBuilder ingredientsHopsBuilder = new StringBuilder();
                                for(int j = 0 ; j < jsHops.length() ; j++){
                                    ingredientsHopsBuilder.append(jsHops.getJSONObject(j).getString("name"));
                                    if(j < jsHops.length()) ingredientsHopsBuilder.append("\n");
                                }
                                String ingredientHops = ingredientsHopsBuilder.toString();

                                String ingredientYeast = jsBeer.getJSONObject("ingredients").getString("yeast");

                                beers.add(new Beer(beerId, beerName, beerTagline, beerFirst_brewed, beerDescription, beerImage_url, beerBrewers_tips, beerContributed_by, ingredientsMalt, ingredientHops, ingredientYeast));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            beers.add(defaultBeer);
                        }

                        if(addMore){
                            beersList.deferNotifyDataSetChanged();
                        }else{
                            adapter = new BeerAdapter(c, beers);
                            beersList.setAdapter(adapter);
                        }

                        beersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(myInterface != null){
                                    myInterface.detailsClick(position, beers);
                                }
                            }
                        });
                        progressBar.setVisibility(View.GONE);
                        progressBarStart.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        beers.add(defaultBeer);

                        if(addMore){
                            beersList.deferNotifyDataSetChanged();
                        }else{
                            adapter = new BeerAdapter(c, beers);
                            beersList.setAdapter(adapter);
                        }
                        progressBar.setVisibility(View.GONE);
                        progressBarStart.setVisibility(View.GONE);
                    }
                });
        queue.add(jsArrRequest);
    }
}

