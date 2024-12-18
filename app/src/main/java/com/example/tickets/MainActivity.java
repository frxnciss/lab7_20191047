package com.example.tickets;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickets.adapter.BusAdapter;
import com.example.tickets.dto.Bus;
import com.example.tickets.dto.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private AppCompatButton btnEscaneo;
    private FirebaseFirestore db;
    private List<User> usuariosList;
    private BusAdapter busAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Bus> busList = List.of(
                new Bus("El chino", "S/. 2.50", "S/. 50.00", R.drawable.chino),
                new Bus("Corredor Rojo", "S/. 3.00", "S/. 60.00", R.drawable.corredorR),
                new Bus("Corredor Morado", "S/. 3.00", "S/. 60.00", R.drawable.corredorM)
        );

        busAdapter = new BusAdapter(this, busList);
        recyclerView.setAdapter(busAdapter);
    }

}