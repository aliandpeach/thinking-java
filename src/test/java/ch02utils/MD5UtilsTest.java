package ch02utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilsTest {

    @Test
    public void testParseStrToMd5L32() throws Exception {
        System.out.println(MD5Utils.parseStrToMd5L32("admin"));
        System.out.println(MD5Utils.parseStrToMd5L32_("wusong"));
    }

    @Test
    public void testParseStrToMd5U32() throws Exception {

    }

    @Test
    public void testParseStrToMd5U16() throws Exception {

    }

    @Test
    public void testParseStrToMd5L16() throws Exception {

    }
}