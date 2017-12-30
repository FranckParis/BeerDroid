package beerdroid.polytech.com.beerdroid.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import beerdroid.polytech.com.beerdroid.Fragments.DetailsFragment;
import beerdroid.polytech.com.beerdroid.Fragments.ListFragment;
import beerdroid.polytech.com.beerdroid.Objects.Beer;
import beerdroid.polytech.com.beerdroid.R;

public class MainActivity extends AppCompatActivity implements ListFragment.ListInterface {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ListFragment firstFragment = new ListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void detailsClick(int pos, List<Beer> beers) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        DetailsFragment newFragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        Beer beer = beers.get(pos);
        bundle.putSerializable("beer", beer);
        newFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
    }
}