package com.rainkaze.traffic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;

import androidx.fragment.app.Fragment;

import com.rainkaze.traffic.R;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rainkaze.traffic.R;
import com.rainkaze.traffic.fragment.DashboardFragment;

import com.rainkaze.traffic.fragment.UserManagementFragment;
import com.rainkaze.traffic.fragment.ViolationFragment;
import com.rainkaze.traffic.fragment.DeviceManagementFragment;
import com.rainkaze.traffic.fragment.HelpFragment;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar; // 将 toolbar 提升为成员变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar); // 初始化 toolbar
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            // 设置初始界面和标题
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
            setTitle("仪表盘"); // 设置初始标题
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            selectedFragment = new DashboardFragment();
            setTitle(item.getTitle()); // 更新标题
        } else if (id == R.id.nav_user_management) {
            selectedFragment = new UserManagementFragment();
            setTitle(item.getTitle()); // 更新标题
        } else if (id == R.id.nav_violations) {
            selectedFragment = new ViolationFragment();
            setTitle(item.getTitle()); // 更新标题
        } else if (id == R.id.nav_devices) {
            selectedFragment = new DeviceManagementFragment();
            setTitle(item.getTitle()); // 更新标题
        } else if (id == R.id.nav_help) {
            selectedFragment = new HelpFragment();
            setTitle(item.getTitle()); // 更新标题
        } else if (id == R.id.nav_logout) { // 添加登出逻辑
            logout();
        }
        // ... 添加其他Fragment的切换逻辑 ...

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void logout() {
        // 清除Token
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        prefs.edit().remove("token").apply();

        // 跳转到登录页
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}