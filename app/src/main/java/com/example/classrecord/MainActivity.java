package com.example.classrecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.classrecord.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private RecViewModel viewModel;

    private List<Table> lst = new ArrayList<>();

    List<String> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //if(mAuth.getCurrentUser() == null)
            //sendUserToSignInActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        final ClassAdapter adapter = new ClassAdapter(this, colors, this);
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecViewModel.class);
        viewModel.getAllSub().observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                lst.clear();
                lst.addAll(tables);
                adapter.updateList(tables);
                //Toast.makeText(MainActivity.this, "tables", Toast.LENGTH_SHORT).show();
            }
        });

        addColors();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.input.getText().toString().toUpperCase();
                if(TextUtils.isEmpty(text))
                    Toast.makeText(MainActivity.this, "Please Enter Subject", Toast.LENGTH_SHORT).show();
                else {
                    boolean flag = false;
                    for(Table table : lst){
                        if(table.subject.equalsIgnoreCase(text)) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag)
                        Toast.makeText(MainActivity.this, "Subject Already exists", Toast.LENGTH_SHORT).show();
                    else {
                        viewModel.insertClass(new Table(text));
                        Toast.makeText(MainActivity.this, text+" Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void addColors() {
        colors.add("#5E97F6");
        colors.add("#9CCC65");
        colors.add("#FF8A65");
        colors.add("#9E9E9E");
        colors.add("#9FA8DA");
        colors.add("#90A4AE");
        colors.add("#AED581");
        colors.add("#F6BF26");
        colors.add("#FFA726");
        colors.add("#4DD0E1");
        colors.add("#BA68C8");
        colors.add("#A1887F");
    }

    private void sendUserToSignInActivity() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void delete(Table table) {
        for(LinkTable linkTable : LinkClassAdapter.linkItem){
            if(linkTable.className.equalsIgnoreCase(table.subject))
                viewModel.deleteLink(linkTable);
        }
        viewModel.deleteClass(table);
    }
}