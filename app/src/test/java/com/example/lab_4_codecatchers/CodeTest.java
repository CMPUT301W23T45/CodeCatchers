package com.example.lab_4_codecatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.firebase.firestore.GeoPoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CodeTest {
    Code code;

    /**
     * Before each test created a mock Code
     */
    @BeforeEach
    public void MockCode() {
        code = new Code();
    }

    @Test
    public void imageStringTest(){
        Code code = new Code(3568, "763974","0","","");
        assertEquals("0",code.getImageString());
        code.setImageString("imageString");
        assertEquals("imageString",code.getImageString());
    }

    @Test
    public void scoreTest(){
        Code code = new Code(3568, "763974","0","","");
        assertEquals(3568,code.getScore());
        code.setScore(10);
        assertEquals(10,code.getScore());
    }


    @Test
    public void hashTest(){
        Code code = new Code(3568, "","0","","");
        assertEquals("",code.getHash());
        code.setHash("hash12345");
        assertEquals("hash12345",code.getHash());
    }

    @Test
    public void humanNameTest(){
        Code code = new Code(3568, "","0","","","");
        assertEquals("0",code.getHumanName());
        code.setHumanName("humanName");
        assertEquals("humanName",code.getHumanName());
    }


    @Test
    public void commentTest(){
        Code code = new Code(3568, "","0","","","");
        assertEquals("",code.getComment());
        code.setComment("hello");
        assertEquals("hello",code.getComment());
    }

    @Test
    public void geoPointTest(){
        Code code = new Code();
        GeoPoint g = new GeoPoint(-1.0,-1.0);
        code.setLocation(g);
        assertEquals(g,code.getLocation());
    }



}
