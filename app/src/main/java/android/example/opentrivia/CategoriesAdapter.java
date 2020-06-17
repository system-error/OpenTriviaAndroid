package android.example.opentrivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter {

        public CategoriesAdapter(Context context, ArrayList<Categories> categoryList){
                super(context,0,categoryList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return initView(position,convertView,parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return initView(position,convertView,parent);
        }

        private View initView(int position, View convertView, ViewGroup parent){
                if(convertView == null){
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_category_row,parent,false);
                }

                TextView spinnerCategoryName = convertView.findViewById(R.id.category_name);

                Categories country = (Categories) getItem(position);

                if(country != null){
                        spinnerCategoryName.setText(country.getName());
                }

                return convertView;
        }
}
