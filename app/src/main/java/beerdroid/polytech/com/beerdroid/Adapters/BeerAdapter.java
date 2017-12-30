package beerdroid.polytech.com.beerdroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import beerdroid.polytech.com.beerdroid.Objects.Beer;
import beerdroid.polytech.com.beerdroid.R;

/**
 * Created by Menros on 29/12/2017.
 */

public class BeerAdapter extends ArrayAdapter<Beer> {

    public BeerAdapter(Context context, List<Beer> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elmt_list_layout,parent, false);
        }

        EventViewHolder viewHolder = (EventViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EventViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.beerImg);
            viewHolder.name = (TextView) convertView.findViewById(R.id.beerName);
            viewHolder.tagline = (TextView) convertView.findViewById(R.id.beerTagline);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List
        Beer beer = getItem(position);
        Picasso.with(getContext()).load(beer.getImage_url()).into(viewHolder.img);
        viewHolder.name.setText(beer.getName());
        viewHolder.tagline.setText(beer.getTagline());

        return convertView;
    }

    private class EventViewHolder {
        public ImageView img;
        public TextView name;
        public TextView tagline;
    }
}
