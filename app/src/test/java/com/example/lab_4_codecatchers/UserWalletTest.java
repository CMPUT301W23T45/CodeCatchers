package com.example.lab_4_codecatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserWalletTest {
    private UserWallet userWallet;

    /**
     * Before each test created a mock UserWallet
     */
    @BeforeEach
    public void MockUserWallet() {
        userWallet = new UserWallet("637937", new ArrayList<Code>());
    }

    /**
     * get the size of the list
     * increase the list by adding a new Code
     * check if our current size matches the initial size plus
     one
     */
    @Test
    public void addCodeTest(){
        Code code = new Code(3568, "763974","0","");
        int size = userWallet.getSize();
        userWallet.addCode(code);
        assertEquals(userWallet.getSize(), size+1);
    }

    /**
     * adds code to empty wallet
     * gets code from index 0
     * check if getCode == code
     */
    @Test
    public void getCode() {
        Code code = new Code(3568, "763974","0","");
        userWallet.addCode(code);
        assertEquals(userWallet.getCode(0), code);
    }

    /**
     * adds two new codes to list
     * checks if getTotal is equal to actual total
     */
    @Test
    public void getTotal() {
        Code code = new Code(3568, "763974","0","");
        Code code1 = new Code(95674, "763984","0","");
        userWallet.addCode(code);
        userWallet.addCode(code1);
        assertEquals(userWallet.getTotal(), 99242);
    }

    /**
     * adds two new codes to list
     * checks if getHighest == to code1
     */
    @Test
    public void getHighest() {
        Code code = new Code(3568, "763974","0","");
        Code code1 = new Code(95674, "763984","0","");
        userWallet.addCode(code);
        userWallet.addCode(code1);
        assertEquals(userWallet.getHighest(),code1);
    }

    /**
     * adds two new codes to list
     * checks if getLowest == to code
     */
    @Test
    public void getLowest() {
        Code code = new Code(3568, "763974","0","");
        Code code1 = new Code(95674, "763984","0","");
        userWallet.addCode(code);
        userWallet.addCode(code1);
        assertEquals(userWallet.getLowest(),code);
    }

    /**
     * increase the list by adding a new Code
     * get the size of the list
     * decrease size by removing Code
     * check if our current size matches the initial size minus
     one
     */
    @Test
    public void removeCodeTest() {
        Code code = new Code(3568, "763974","0","");
        Code code1 = new Code(95674, "763984","0","");
        userWallet.addCode(code);
        userWallet.addCode(code1);
        int size = userWallet.getSize();
        userWallet.removeCode(code);
        assertEquals(userWallet.getSize(), size - 1);
    }
}
