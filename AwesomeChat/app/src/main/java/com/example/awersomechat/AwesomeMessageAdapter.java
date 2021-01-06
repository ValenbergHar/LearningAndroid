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

    List<AwesomeMessage> messages;
    private Activity activity;

    public AwesomeMessageAdapter(Activity context, int resource, List<AwesomeMessage> messages) {
        super(context, resource, messages);
        this.messages = messages;
        this.activity = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderAM viewHolderAM;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        AwesomeMessage awesomeMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            layoutResource = R.layout.my_message_item;
        } else {
            layoutResource = R.layout.your_message_item;
        }

        if (convertView != null) {
            viewHolderAM = (ViewHolderAM) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolderAM=new ViewHolderAM(convertView);
            convertView.setTag(viewHolderAM);
        }
        boolean isText = awesomeMessage.getImageUrl()==null;
        if (isText){
            viewHolderAM.messageTextView.setVisibility(View.VISIBLE);
            viewHolderAM.photoImageView.setVisibility(View.GONE);
            viewHolderAM.messageTextView.setText(awesomeMessage.getText());
        }else{
            viewHolderAM.messageTextView.setVisibility(View.GONE);
            viewHolderAM.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolderAM.photoImageView.getContext()).load(awesomeMessage.getImageUrl()).into(viewHolderAM.photoImageView);
        }


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

    @Override
    public int getItemViewType(int position) {
        int flag;
        AwesomeMessage awesomeMessage = messages.get(position);
        if (awesomeMessage.isMine()) {
            flag = 0;
        } else {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolderAM {
        private ImageView photoImageView;
        private TextView messageTextView;

        public ViewHolderAM(View view) {
            photoImageView = view.findViewById(R.id.photoImageView);
            messageTextView = view.findViewById(R.id.messageTextView);
        }
    }
}