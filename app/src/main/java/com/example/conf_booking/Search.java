package com.example.conf_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.conf_booking.api.entities.SearchEntity;
import com.example.conf_booking.api.retrofitservices.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Search extends AppCompatActivity {

    Button search;

    TextView results;

    EditText objectIdTextEdit,
            objectTypeTextEdit,
            fromDateTextEdit,
            toDateTextEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.searchBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPressed(v);
            }
        });

        objectIdTextEdit = findViewById(R.id.objectId);
        objectTypeTextEdit = findViewById(R.id.objectType);
        fromDateTextEdit = findViewById(R.id.fromDate);
        toDateTextEdit = findViewById(R.id.toDate);

    }


    public void searchPressed(final View view) {
        SearchEntity newSearch = new SearchEntity();

        newSearch.setObjectId(objectIdTextEdit.getText().toString().trim());
        newSearch.setObjectType(objectTypeTextEdit.getText().toString().trim());
        newSearch.setFromDate(fromDateTextEdit.getText().toString().trim());
        newSearch.setToDate(toDateTextEdit.getText().toString().trim());

        Call<SearchEntity> call = RetrofitHelper.getAPIService().createSearch(newSearch);
        call.enqueue(new Callback<SearchEntity>() {
            @Override
            public void onResponse(Call<SearchEntity> call, Response<SearchEntity> response) {
                SearchEntity body = response.body();

                System.out.println(response.toString());

                if(body != null){
                    Intent intent = new Intent(view.getContext(), Search.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("search", body);
                    startActivity(intent);
                } else {
                    System.out.println("ERROR! WRONG! U SUCK!");
                }
            }

            @Override
            public void onFailure(Call<SearchEntity> call, Throwable t) {
                System.out.println("ERROR! WRONG! U SUCK!");
                t.printStackTrace();

            }
        });
        /*
        results.setText(objectIdTextEdit.getText());
        results.setText(objectTypeTextEdit.getText());
        results.setText(fromDateTextEdit.getText());
        results.setText(toDateTextEdit.getText());
        */


    }


}
