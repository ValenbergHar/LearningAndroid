package drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private ImageButton imageViewPah;
    private ImageView imageMainMap;
    private DrawerLayout drawer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imageViewPah = view.findViewById(R.id.imageViewPah);
        imageMainMap = view.findViewById(R.id.imageMainMap);


        Glide.with(getActivity()).load(R.drawable.main_map).centerCrop().into(imageMainMap);
        Glide.with(getActivity()).load(R.drawable.main_pahonia).into(imageViewPah);

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
