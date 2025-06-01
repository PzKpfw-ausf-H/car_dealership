package com.example.autosalon.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autosalon.data.UserDao;
import com.example.autosalon.models.User;
import com.example.autosalon.utils.DatabaseHelper;

import com.example.autosalon.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tvLoginLink = findViewById(R.id.tv_login_link);
        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);

        userDao = DatabaseHelper.getDatabase(this).userDao();

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDao.getUserByUsername(username) != null) {
                Toast.makeText(this, "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
                return;
            }

            userDao.insert(new User(username, password));
            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }
}