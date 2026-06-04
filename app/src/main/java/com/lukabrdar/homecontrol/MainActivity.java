package com.lukabrdar.homecontrol;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        NavController navController = Navigation.findNavController(this, R.id.navgraph);
        if (id == R.id.menu_home) {
            navController.navigate(R.id.mainFragment);
            return true;
        } else if (id == R.id.menu_devices) {
            navController.navigate(R.id.devicesFragment);
            return true;
        } else if (id == R.id.menu_settings) {
            navController.navigate(R.id.settingsFragment);
            return true;
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}