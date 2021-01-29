package drawer;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private ImageButton imageViewPah;
    private ImageView imageMainMap;
    private DrawerLayout drawer;

    private String pahonia = "https://upload.wikimedia.org/wikipedia/commons/2/2c/Pahonia_-_Пагоня%2C_Grand_Duchy_of_Lithuania_COA_%281575%29_cut.png";
    private String mainMap = "https://ic.pics.livejournal.com/max_pragensis/21033009/110521/110521_900.jpg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imageViewPah = view.findViewById(R.id.imageViewPah);
        imageMainMap = view.findViewById(R.id.imageMainMap);


        //  Glide.with(context).load(kingList.get(position).getImageUrl()).into(holder.iv_king_picture);
        //Glide.with(getActivity()).load(pahonia).into(imageViewPah);

      Glide.with(getActivity()).load(mainMap).centerCrop().into(imageMainMap);
        Glide.with(getActivity()).load(pahonia).into(imageViewPah);

        imageViewPah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityDrawer) getActivity()).openDrawer();
                Toast.makeText(getActivity(), "sghsgh", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



}
