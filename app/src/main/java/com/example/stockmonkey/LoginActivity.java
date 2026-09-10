package com.example.stockmonkey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import com.example.stockmonkey.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton btnMenu = findViewById(R.id.btnMenu);
        TextView tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        // 우측 상단 햄버거 메뉴 클릭 -> 드롭다운 표시
        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_settings) {
                    // 세팅 처리
                    return true;
                } else if (id == R.id.action_logout) {
                    // 로그아웃 처리
                    return true;
                }
                return false;
            });
            popup.show();
        });

        // 회원가입 화면으로 이동 링크
        tvGoToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}