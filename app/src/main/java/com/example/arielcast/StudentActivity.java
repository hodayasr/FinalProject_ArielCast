package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    ListView studentListView;
    ArrayList<String> coursesList = new ArrayList<>();
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_list_item_1, coursesList);

        studentListView = findViewById(R.id.student_listview);
        studentListView.setAdapter(myArrayAdapter);

        // get student's email from MainActivity
        Intent intent = getIntent();
        String email= intent.getExtras().getString("Email");
        Toast.makeText(StudentActivity.this,
                "Welcome "+email+" !",
                Toast.LENGTH_LONG).show();

        mRef = FirebaseDatabase.getInstance().getReference().child("Lecturers");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.child("courseName").getValue(String.class);
                String value2 = snapshot.child("fullname").getValue(String.class);
                coursesList.add(value+" - "+value2);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}