package com.example.applicationjava;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.applicationjava.adapters.crewAdapter;
import com.example.applicationjava.daos.crewDAO;
import com.example.applicationjava.db.crewdatabase;
import com.example.applicationjava.models.crewModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Button refresh, delete;
    ArrayList<crewModel> list = new ArrayList<>();
    ArrayList<crewModel> roomlist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.crewlist);
        progressBar= findViewById(R.id.progress);
         refresh= findViewById(R.id.refresh);
         delete=findViewById(R.id.clear);
        if (isOnline())
            loadcrew();
        else
            loadfromroom();
        crewdatabase db= Room.databaseBuilder(this,crewdatabase.class,"crewdatabase").build();
        crewDAO crewDAO= db.getDAO();
        refresh.setOnClickListener(view -> {
            if (isOnline())
            loadcrew();
            else
                loadfromroom();
        });
        delete.setOnClickListener(view -> {
            if (roomlist!=null)
          crewDAO.deleteUsers().subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.CompletableObserver() {
              @Override
              public void onSubscribe(@io.reactivex.annotations.NonNull io.reactivex.disposables.Disposable d) {

              }

              @Override
              public void onComplete() {
                  recyclerView.getAdapter().notifyDataSetChanged();
                  Log.d(Constants.TAG,"all data deleted");
                  Toast.makeText(getApplicationContext(),"Cache cleared",Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                  Log.d(Constants.TAG,e.getMessage());
              }
          });

        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void loadcrew() {
       progressBar.setVisibility(View.VISIBLE);
        crewdatabase db= Room.databaseBuilder(this,crewdatabase.class,"crewdatabase").build();

        crewDAO crewDAO= db.getDAO();
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, Constants.BASE_URL, null, response -> {
            for (int i=0; i<response.length(); i++){
                try {
//                       name, agency, image(display image in app),
//                               wikipedia(hyperlink), status
                    JSONObject object= response.getJSONObject(i);
                    String name= object.getString("name");
                    String agency= object.getString("agency");
                    String image= object.getString("image");
                    String wiki= object.getString("wikipedia");
                    String status= object.getString("status");

                    crewModel model= new crewModel(name,agency,image,wiki,status);
                    list.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

                    crewDAO.insertAllcrew(list).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                           Log.d(Constants.TAG,"insertion complete");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });


            recyclerView.setAdapter(new crewAdapter(getApplicationContext(),list));
        }, error -> {

             loadfromroom();
            Toast.makeText(this,"Failed to load crew data",Toast.LENGTH_SHORT).show();
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void loadfromroom(){
        crewdatabase db= Room.databaseBuilder(this,crewdatabase.class,"crewdatabase").build();

        crewDAO crewDAO= db.getDAO();
        crewDAO.getAllCrew().observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(list1 -> {
                    Log.d(Constants.TAG,"getting complete");
                       if (list1.size()==0) Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                    roomlist= (ArrayList<crewModel>) list1;
                    recyclerView.setAdapter(new crewAdapter(getApplicationContext(),(ArrayList<crewModel>) roomlist));
                });
    }
}