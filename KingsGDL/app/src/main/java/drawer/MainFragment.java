package drawer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;

public class MainFragment extends Fragment {

    private ImageView imageViewMap;
    private String pahonia = "https://upload.wikimedia.org/wikipedia/commons/2/2c/Pahonia_-_Пагоня%2C_Grand_Duchy_of_Lithuania_COA_%281575%29_cut.png";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imageViewMap = view.findViewById(R.id.imageViewMap);
        //  Glide.with(context).load(kingList.get(position).getImageUrl()).into(holder.iv_king_picture);
        Glide.with(getActivity()).load(pahonia).into(imageViewMap);
        return view;
    }


}
