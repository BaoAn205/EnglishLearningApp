package com.example.apphoctiengtrung;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LessonAdapter.OnItemClickListener {

    private DrawerLayout drawerLayout;

    private FirebaseAuth mAuth;
    private NavigationView navigationView;
    private TextView headerUserName, headerUserEmail;

    private RecyclerView recyclerViewLessons;
    private LessonAdapter lessonAdapter;
    private List<Lesson> lessonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            headerUserName = headerView.findViewById(R.id.text_view_user_name);
            headerUserEmail = headerView.findViewById(R.id.text_view_user_email);
        }

        // --- Setup RecyclerView ---
        recyclerViewLessons = findViewById(R.id.recycler_view_lessons);
        recyclerViewLessons.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu giả
        lessonList = new ArrayList<>();
        lessonList.add(new Lesson("Bài 1: Xin chào", "HSK 1", "Học các câu chào hỏi cơ bản.", false));
        lessonList.add(new Lesson("Bài 2: Bạn tên là gì?", "HSK 1", "Học cách hỏi và trả lời tên.", false));
        lessonList.add(new Lesson("Bài 3: Mua sắm", "HSK 2", "Học từ vựng về mua sắm và mặc cả.", false));
        lessonList.add(new Lesson("Bài 4: Giao tiếp nâng cao", "HSK 3", "Khóa học dành cho tài khoản Premium.", true));

        lessonAdapter = new LessonAdapter(lessonList, this);
        recyclerViewLessons.setAdapter(lessonAdapter);
        // --- End Setup RecyclerView ---

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(toggle);
        }
        toggle.syncState();

        if (savedInstanceState == null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.nav_home_title);
            }
            if(navigationView != null) {
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    if (!isFinishing()) {
                        MainActivity.super.onBackPressed();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (headerUserName != null) {
                headerUserName.setText(user.getDisplayName());
            }
            if (headerUserEmail != null) {
                headerUserEmail.setText(user.getEmail());
            }

            if (navigationView != null) {
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_tai_khoan).setVisible(false);
                navMenu.findItem(R.id.nav_dang_xuat).setVisible(true);
            }

        } else {
            if (headerUserName != null) {
                headerUserName.setText(R.string.app_name);
            }
            if (headerUserEmail != null) {
                headerUserEmail.setText("Chưa đăng nhập");
            }

            if (navigationView != null) {
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_tai_khoan).setVisible(true);
                navMenu.findItem(R.id.nav_dang_xuat).setVisible(false);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            // Không cần làm gì vì đã ở trang chủ
        } else if (itemId == R.id.nav_dictionary) {
            startActivity(new Intent(this, DictionaryActivity.class));
        } else if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (itemId == R.id.nav_tai_khoan) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (itemId == R.id.nav_dang_xuat) {
            mAuth.signOut();
            updateUI(null);
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        } else {
            // Các mục khác như Từ vựng, Ngữ pháp sẽ được xử lý sau
            Toast.makeText(this, "Chức năng này sẽ được phát triển sau", Toast.LENGTH_SHORT).show();
        }

        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onItemClick(Lesson lesson) {
        if (lesson.isPremium()) {
            Toast.makeText(this, "Đây là khóa học Premium. Vui lòng nâng cấp tài khoản.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bạn đã chọn: " + lesson.getTitle(), Toast.LENGTH_SHORT).show();
            // Trong tương lai, chúng ta sẽ mở một màn hình chi tiết bài học ở đây
        }
    }
}