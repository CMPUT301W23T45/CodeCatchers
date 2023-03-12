package com.example.lab_4_codecatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.*;
import java.util.ArrayList;
import java.util.Objects;
public class UserTest {
    private User user;

    @BeforeEach
    public void userSetUp(){
        this.user = new User();
    }

    @Test
    public void usernameTest() {
        String name = user.getUsername();
        assertEquals(" ",name);
        user.setUsername("JohnDoe");
        String actualName = user.getUsername();
        assertEquals("JohnDoe",actualName);
    }

    @Test
    public void emailTest(){
        String email = user.getEmail();
        assertEquals(" ",email);
        user.setEmail("abcd@gmail.com");
        assertEquals("abcd@gmail.com",user.getEmail());
    }

    @Test
    public void phoneTest(){
        String phone = user.getPhone();
        assertEquals(" ",phone);
        user.setEmail("283-334-3322");
        assertEquals("283-334-3322",user.getPhone());
    }

    @Test
    public void totalScoreTest(){
        int score = user.getTotalScore();
        assertEquals(0,score);
        user.setTotalScore(100);
        int actual_score = user.getTotalScore();
        assertEquals(100,actual_score);
    }

    @Test
    public void rankTest(){
        int rank = user.getRank();
        assertEquals(0,rank);
        user.setRank(1);
        int actual_rank = user.getRank();
        assertEquals(1,actual_rank);
    }

    //Not sure how to test collectedQRcodes

}
