package com.example.autosalon.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autosalon.activities.MainActivity;

import com.example.autosalon.data.UserDao;
import com.example.autosalon.models.User;
import com.example.autosalon.utils.DatabaseHelper;

import com.example.autosalon.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);

        userDao = DatabaseHelper.getDatabase(this).userDao();

        btnLogin.setOnClickListener(v-> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = userDao.login(username, password);
            if (user == null) {
                Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show();
                return;
            }

            //Сохранение текущей сессии локально
            SharedPreferences prefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            prefs.edit().putString("loggedInUser", username).apply();

            Toast.makeText(this, "Добро пожаловать, " + username, Toast.LENGTH_SHORT).show();

            //Переход в основную активность
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        TextView tvRegister = findViewById(R.id.tv_register_link);
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

    }
}