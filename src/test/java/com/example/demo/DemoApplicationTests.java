package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    public void insertUser() {
        String encodedPassword = passwordEncoder.encode("password123");

        User user = new User()
                .setEmail("ethan@w07.tech")
                .setUsername("ethan")
                .setFirstName("ethan")
                .setPasswordHash(encodedPassword)
                .setLastName("w07");

        assertThat(userMapper.insert(user)).isGreaterThan(0);
        // 成功直接拿回写的 ID
        assertThat(user.getId()).isNotNull();
    }

}
