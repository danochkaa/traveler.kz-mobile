package com.example.trav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;.
import java.util.ArrayList;

public class EventsListAdapter extends BaseAdapter{

    Context context;
    ArrayList<Integer> Id;
    ArrayList<String> City;
    ArrayList<String> Name;
    ArrayList<String> Discription;
    ArrayList<Integer> Picture;


    public EventsListAdapter( Context context2,ArrayList<Integer> id, ArrayList<String> City, ArrayList<String> name, ArrayList<String> Discription ,ArrayList<Integer> Picture) {

        this.context = context2;
        this.Id = id;
        this.City = City;
        this.Name = name;
        this.Discription = Discription;
        this.Picture = Picture;
    }

    public int getCount() { return Id.size(); }
    public Object getItem(int position) { return null; }
    public long getItemId(int position) { return 0; }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.single_item_eventlist, null);

            holder = new Holder();
            holder.Name_TextView = child.findViewById(R.id.title);
            holder.Discription_TextView = child.findViewById(R.id.description);
            holder.pic_ImageView = child.findViewById(R.id.thumbnail);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.Name_TextView.setText(Name.get(position));
        holder.Discription_TextView.setText(Discription.get(position));
        holder.pic_ImageView.setImageResource(Picture.get(position));

        return child;
    }


    public class Holder {
        TextView Name_TextView;
        TextView Discription_TextView;
        ImageView pic_ImageView;
    }




}
