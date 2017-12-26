package beerdroid.polytech.com.beerdroid.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import beerdroid.polytech.com.beerdroid.Fragments.DetailsFragment;
import beerdroid.polytech.com.beerdroid.Fragments.ListFragment;
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

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void detailsClick() {

        DetailsFragment newFragment = new DetailsFragment();
        newFragment.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}