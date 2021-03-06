package com.teamgy.wakeonlan;

import org.junit.Test;

import com.teamgy.wakeonlan.utils.Tools;

import junit.framework.Assert;

/**
 * Created by Jedi-Windows on 16.01.2016..
 */
public class MacFormatTest {

    @Test
    public void test_macFormatting_spaces(){

        Assert.assertEquals("000a959d6816", Tools.reformatMACInput("00 0a 95 9d 68 16",true));
        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00 0a 95 9d  68 16",true));


    }
    @Test
    public void test_macFormatting_dashes(){

        Assert.assertEquals("001422012345",Tools.reformatMACInput("00-14-22-01-23-45",true));
        Assert.assertEquals("001422012345",Tools.reformatMACInput("00-14-22-- 01-23-45",true));




    }
    @Test
    public void test_macFormatting_colon(){

        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00:0a:95:9d:68:16",true));
        Assert.assertEquals("fcaa142804ea",Tools.reformatMACInput("FC:AA:14:28:04:EA",true));
        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00:0a:95:9d::::68:16",true));

    }
    @Test
    public void test_mac_OverLimit(){

        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00:0a:95:9d:68:16:85:12",true));


    }
    @Test
    public void test_mac_dots(){

        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00.0a:95:9d:68:16",true));


    }
    @Test
    public void test_mac_software_tester_user(){

        Assert.assertEquals("000a959d6816",Tools.reformatMACInput("00???????????0a$*$%*&95#@&&$9d:68!@@#$&16213213123213123123",true));

    }

}
