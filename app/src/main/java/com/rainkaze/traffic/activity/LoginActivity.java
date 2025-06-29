package com.rainkaze.traffic.activity;

import android.content.Context; // 确保导入 Context
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull; // 建议为回调参数添加 @NonNull 注解
import androidx.appcompat.app.AppCompatActivity;

import com.rainkaze.traffic.R;
import com.rainkaze.traffic.api.ApiClient;
import com.rainkaze.traffic.api.ApiService;
import com.rainkaze.traffic.model.JwtAuthResponseDto;
import com.rainkaze.traffic.model.LoginDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 检查是否已登录
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        if (prefs.contains("token")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // =======================
        //  *** 这里是修改点 ***
        //  将 this (Activity的Context) 传递给 getClient 方法
        // =======================
        apiService = ApiClient.getClient(this).create(ApiService.class);

        buttonLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonLogin.setEnabled(false);
        LoginDto loginDto = new LoginDto(username, password);

        apiService.login(loginDto).enqueue(new Callback<JwtAuthResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<JwtAuthResponseDto> call, @NonNull Response<JwtAuthResponseDto> response) {
                buttonLogin.setEnabled(true); // 无论成功失败，都恢复按钮状态
                if (response.isSuccessful() && response.body() != null) {
                    // 保存Token
                    SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", response.body().getToken());
                    editor.apply();

                    // 跳转到主界面
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请检查凭据", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JwtAuthResponseDto> call, @NonNull Throwable t) {
                buttonLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "网络错误: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}