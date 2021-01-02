package com.example.awersomechat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AwesomeMessageAdapter extends ArrayAdapter<AwesomeMessage> {

    public AwesomeMessageAdapter(Context context, int resource, List<AwesomeMessage> messages) {
        super(context, resource, messages);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_item, parent, false);
        }
        ImageView photoImageView = convertView.findViewById(R.id.photoImageView);
        TextView textTextView = convertView.findViewById(R.id.textTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);

        AwesomeMessage message = getItem(position);
        nameTextView.setText(message.getText());
        Glide.with(photoImageView.getContext()).load(message.getImageUrl()).into(photoImageView);
        textTextView.setText(message.getText());


//        boolean isText = message.getImageUrl() == null;
//        if (isText) {
//            textTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
//            textTextView.setText(message.getText());
//        } else {
//            textTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.INVISIBLE);
//            Glide.with(photoImageView.getContext()).load(message.getImageUrl()).into(photoImageView);
//        }
 //       nameTextView.setText(message.getText());

        return convertView;
    }
}