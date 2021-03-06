package com.mschneider.wgutermtracker.ui.activities.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mschneider.wgutermtracker.R;
import com.mschneider.wgutermtracker.database.AppDatabase;
import com.mschneider.wgutermtracker.models.Term;
import com.mschneider.wgutermtracker.ui.activities.MainActivity;
import com.mschneider.wgutermtracker.ui.adapters.TermAdapter;

import java.util.ArrayList;
import java.util.List;

public class TermsActivity extends AppCompatActivity implements TermAdapter.ViewHolder.OnTermClickListener {
    private RecyclerView termsRecyclerView;
    private List<Term> termsList = new ArrayList<>();
    private Button termAddButton;
    private Button termEditButton;
    private Button termDetailButton;
    private Button termDeleteButton;
    private int selectedPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_terms);
            AppDatabase appDatabase = MainActivity.getAppDatabase();
            List<Term> terms = appDatabase.termDao().getAllTerms();
            for (Term term : terms){ termsList.add(term); }

            termsRecyclerView = findViewById(R.id.termsRecyclerView);
            termsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            termsRecyclerView.setLayoutManager(layoutManager);
            termsRecyclerView.setAdapter(new TermAdapter(termsList, this));

            // Button Connections
            termAddButton = findViewById(R.id.termAddButton);
            termEditButton = findViewById(R.id.termEditButton);
            termDetailButton = findViewById(R.id.termDetailButton);
            termDeleteButton = findViewById(R.id.termDeleteButton);

            termAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TermAddActivity.class);
                    startActivity(intent);
                }
            });
            termDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  MainActivity.getAppDatabase().termDao().deleteById(selectedPosition);
                  termsList.remove(selectedPosition);
                  termsRecyclerView.getAdapter().notifyDataSetChanged();

                }
            });

        termEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermEditActivity.class);
                intent.putExtra("termId", terms.get(selectedPosition).getTermId());
                intent.putExtra("title", terms.get(selectedPosition).getTitle());
                intent.putExtra("start_date", terms.get(selectedPosition).getStartDate());
                intent.putExtra("end_date", terms.get(selectedPosition).getEndDate());
                startActivity(intent);
            }
        });

        termDetailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermDetailActivity.class);
                intent.putExtra("termId", terms.get(selectedPosition).getTermId());
                intent.putExtra("title", terms.get(selectedPosition).getTitle());
                intent.putExtra("start_date", terms.get(selectedPosition).getStartDate());
                intent.putExtra("end_date", terms.get(selectedPosition).getEndDate());
                startActivity(intent);
            }
        });

        }


    @Override
    public void onTermClick(int position) {
        selectedPosition = position;
    }
}
