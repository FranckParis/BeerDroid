package beerdroid.polytech.com.beerdroid.Fragments;

/**
 * Created by franck on 26/12/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import beerdroid.polytech.com.beerdroid.Objects.Beer;
import beerdroid.polytech.com.beerdroid.R;

public class DetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Beer beer = getSelectedBeer();
        printBeer(beer);
    }

    public Beer getSelectedBeer(){
        Bundle bundle = getArguments();
        return (Beer) bundle.getSerializable("beer");
    }

    private void printBeer(Beer beer){
        ImageView beerImg = (ImageView) getActivity().findViewById(R.id.beerImg);
        TextView beerName = (TextView) getActivity().findViewById(R.id.beerName);
        TextView beerTagline = (TextView) getActivity().findViewById(R.id.beerTagline);
        TextView beerContributed_by = (TextView) getActivity().findViewById(R.id.beerContributed_by);
        TextView beerFirst_brewed = (TextView) getActivity().findViewById(R.id.beerFirst_brewed);
        TextView beerDescription = (TextView) getActivity().findViewById(R.id.beerDescription);
        TextView beerBrewers_tips = (TextView) getActivity().findViewById(R.id.beerBrewers_tips);
        TextView beerIngredientsMalt = (TextView) getActivity().findViewById(R.id.beerIngredientsMalt);
        TextView beerIngredientsHops = (TextView) getActivity().findViewById(R.id.beerIngredientsHops);
        TextView beerIngredientYeast = (TextView) getActivity().findViewById(R.id.beerIngredientYeast);


        Picasso.with(getContext()).load(beer.getImage_url()).into(beerImg);
        beerName.setText(beer.getName());
        beerTagline.setText(beer.getTagline());
        beerContributed_by.setText(beer.getContributed_by());
        beerFirst_brewed.setText("Since " + beer.getFirst_brewed());
        beerDescription.setText(beer.getDescription());
        beerBrewers_tips.setText(beer.getBrewers_tips());
        beerIngredientsMalt.setText(beer.getIngredientsMalt());
        beerIngredientsHops.setText(beer.getIngredientsHops());
        beerIngredientYeast.setText("Yeast : " + beer.getIngredientYeast());
    }
}