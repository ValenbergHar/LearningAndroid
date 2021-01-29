package drawer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.MainActivityKings;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static android.drm.DrmStore.Playback.START;

public class MainActivityDrawer extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        initItems();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.dreawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.kings:
                        Toast.makeText(MainActivityDrawer.this, "Kings selected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivityDrawer.this, MainActivityKings.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new MainFragment());
        fragmentTransaction.commit();
        drawer.openDrawer(GravityCompat.START);
    }

    @SuppressLint("WrongConstant")
    public void openDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.openDrawer(Gravity.START);
    }

    private void initItems() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navgationView);
        toolbar = findViewById(R.id.toolBarDrawer);
    }
}