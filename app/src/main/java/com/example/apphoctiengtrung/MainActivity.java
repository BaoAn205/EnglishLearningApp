package com.example.apphoctiengtrung;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

// BẮT ĐẦU SỬA: Thêm import mới cho OnBackPressedCallback
import androidx.activity.OnBackPressedCallback;
// KẾT THÚC SỬA

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView mainTextContent; // Biến để thay đổi chữ trên màn hình chính

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Kết nối Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Gắn Toolbar này làm Action Bar

        // 2. Kết nối DrawerLayout và NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // BẮT ĐẦU SỬA: Thêm kiểm tra null
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this); // Gắn "lắng nghe" sự kiện click
        }
        // KẾT THÚC SỬA

        // 3. Kết nối TextView nội dung
        mainTextContent = findViewById(R.id.main_text_content);

        // 4. Tạo nút 3 gạch (hamburger)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // BẮT ĐẦU SỬA: Thêm kiểm tra null
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(toggle);
        }
        // KẾT THÚC SỬA
        toggle.syncState();

        // 5. Xử lý khi app mới mở (Hiển thị trang chủ)
        if (savedInstanceState == null) {
            // BẮT ĐẦU SỬA: Thêm kiểm tra null
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Trang Chủ");
            }
            if (mainTextContent != null) {
                mainTextContent.setText("Nội dung Trang Chủ");
            }
            if (navigationView != null) {
                navigationView.setCheckedItem(R.id.nav_home);
            }
            // KẾT THÚC SỬA
        }

        // BẮT ĐẦU SỬA: 6. Xử lý khi nhấn nút "Back" của điện thoại (CÁCH MỚI)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START); // Nếu sidebar đang mở, đóng nó lại
                } else {
                    // Nếu sidebar đã đóng, cho phép hành vi "Back" mặc định (thoát app)
                    setEnabled(false); // Tắt callback này
                    if (!isFinishing()) { // Kiểm tra để tránh lỗi khi thoát
                        MainActivity.super.onBackPressed(); // Gọi lại hàm "Back"
                    }
                }
            }
        });
        // KẾT THÚC SỬA
    }

    // BẮT ĐẦU SỬA: Xóa hàm onBackPressed() cũ
    /*
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    */
    // KẾT THÚC SỬA

    // 7. Xử lý khi một mục trong sidebar được nhấn
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        // BẮT ĐẦU SỬA: Thêm kiểm tra null
        if (itemId == R.id.nav_home) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Trang Chủ");
            if (mainTextContent != null) mainTextContent.setText("Nội dung Trang Chủ");
            Toast.makeText(this, "Mở Trang Chủ", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.nav_tu_vung) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Học Từ Vựng");
            if (mainTextContent != null) mainTextContent.setText("Đây là trang Học Từ Vựng");
            Toast.makeText(this, "Mở Học Từ Vựng", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.nav_ngu_phap) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Ngữ Pháp");
            if (mainTextContent != null) mainTextContent.setText("Đây là trang Ngữ Pháp");
            Toast.makeText(this, "Mở Ngữ Pháp", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.nav_settings) {
            Toast.makeText(this, "Mở Cài đặt", Toast.LENGTH_SHORT).show();
        }
        // KẾT THÚC SỬA

        // Đóng sidebar lại sau khi nhấn
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}