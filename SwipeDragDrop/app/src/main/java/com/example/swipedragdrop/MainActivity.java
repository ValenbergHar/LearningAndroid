package com.example.swipedragdrop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.content.ReceiverCallNotAllowedException;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    String deletedMovie = null;
    List<String> moviesList;
    List<String> archivedMovies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = new ArrayList<>();
        moviesList.add("Iron Man");
        moviesList.add("The Incredible Hulk");
        moviesList.add("Iron Man 2");
        moviesList.add("Thor");
        moviesList.add("Captain America: The First Avenger");
        moviesList.add("The Avengers");
        moviesList.add("Iron Man 3");
        moviesList.add("Thor: The Dark World");
        moviesList.add("Captain America: The Winter Soldier");
        moviesList.add("Guardians of the Galaxy");
        moviesList.add("Avengers: Age of Ultron");
        moviesList.add("Ant-Man");
        moviesList.add("Captain America: Civil War");
        moviesList.add("Doctor Strange");
        moviesList.add("Guardians of the Galaxy Vol. 2");
        moviesList.add("Spider-Man: Homecoming");
        moviesList.add("Thor: Ragnarok");
        moviesList.add("Black Panther");
        moviesList.add("Avengers: Infinity War");
        moviesList.add("Ant-Man and the Wasp");
        moviesList.add("Captain Marvel");
        moviesList.add("Avengers: Endgame");
        moviesList.add("Spider-Man: Far From Home");


        recyclerView = findViewById(R.id.recycleView);
        recyclerAdapter = new RecyclerAdapter(moviesList);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener((new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesList.add("Black Widow (2020)");
                moviesList.add("The Eternals (2020)");
                moviesList.add("Shang-Chi and the Legend of the Ten Rings (2021)");
                moviesList.add("Doctor Strange in the Multiverse of Madness (2021)");
                moviesList.add("Thor: Love and Thunder (2021)");

                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }));


    }

    ItemTouchHelper.SimpleCallback simpleCallBack = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(moviesList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedMovie = moviesList.get(position);
                    moviesList.remove(position);
                    recyclerAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, deletedMovie, Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moviesList.add(position, deletedMovie);
                            recyclerAdapter.notifyItemInserted(position);
                        }
                    }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    final String movieName = moviesList.get(position);
                    archivedMovies.add(movieName);
                    moviesList.remove(position);
                    recyclerAdapter.notifyItemRemoved(position);

                    Snackbar.make(recyclerView, movieName + ", Archived", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            archivedMovies.remove(archivedMovies.lastIndexOf(movieName));
                            moviesList.add(position, movieName);
                            recyclerAdapter.notifyItemInserted(position);
                        }
                    }).show();

                    break;

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.design_default_color_primary))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.design_default_color_secondary))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}