package com.qst.finance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Classname JwtTest
 * @Description TODO
 * @Date 2025/08/11 19:34
 * @Created by YanShijie
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void test() {
        System.out.println(passwordEncoder.encode("admin"));
    }
}
