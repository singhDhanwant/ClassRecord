package com.example.classrecord;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.classrecord.databinding.ActivitySubClassBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubClassActivity extends AppCompatActivity {

    private ActivitySubClassBinding binding;

    private RecViewModel viewModel;

    private List<LinkTable> lst = new ArrayList<>();

    String link;
    String sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubClassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sub = getIntent().getStringExtra("Subject");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewLink.setLayoutManager(layoutManager);
        final LinkClassAdapter adapter = new LinkClassAdapter(this, sub, this);
        binding.recyclerViewLink.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecViewModel.class);
        viewModel.getAllLink().observe(this, new Observer<List<LinkTable>>() {
            @Override
            public void onChanged(List<LinkTable> tables) {
                lst.clear();
                lst.addAll(tables);
                adapter.updateLinks(tables);
                //Toast.makeText(MainActivity.this, "tables", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.inputLink.getText().toString();
                if(TextUtils.isEmpty(text))
                    Toast.makeText(SubClassActivity.this, "Please Paste link first", Toast.LENGTH_SHORT).show();
                else {
                    boolean flag = false;
                    for(LinkTable table : lst){
                        if(table.link.equals(text) || table.link.equals(text+"https://")) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag)
                        Toast.makeText(SubClassActivity.this, "Link Already exists", Toast.LENGTH_SHORT).show();
                    else {
                        viewModel.insertLink(new LinkTable(text, sub));
                        Toast.makeText(SubClassActivity.this, sub, Toast.LENGTH_SHORT).show();
                        link = text;
                    }
                }
            }
        });
    }

    void delete(LinkTable table){
        viewModel.deleteLink(table);
    }
}