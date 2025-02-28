package com.waveaccess.tacocloud;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegExpTests {

    @ParameterizedTest
    @ValueSource(strings = {"02/2413", "11/2011", "01/2015", "02/0000", "01/9999"})
    public void CreditCard_RegExp_Happy(String pattern) {
        String matcher = "(0[1-9]|1[0-2])\\/\\d{4}$";
        assertTrue(Pattern.matches(matcher,pattern));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2/2013", "12/14", "13/2012", "/3012", "00/9999", "11/10111","31\r/2021","    ","\n2/2020"})
    public void CreditCard_RegExp_Bad(String pattern) {
        String matcher = "(0[1-9]|1[0-2])\\/\\d{4}$";
        assertFalse(Pattern.matches(matcher,pattern));
    }

    @ParameterizedTest
    @CsvSource(value = {"tom@waveaccess.com, tom",
            "tom.riddle@waveaccess.com, tom.riddle",
            "tom.riddle+wa@waveaccess.com, tom.riddle",
            "tom@waveaccess.eu.com, tom",
            "potter@waveaccess.com, potter",
            "harry@waveaccess.com, harry",
            "hermione+wa@waveaccess.com, hermione",
            "wa@waveaccess.com, wa"})
    public void getEmailName_RegExp_Happy(String pattern, String expected) {
        String matcher = "^[^+@]+";
        Pattern patt = Pattern.compile(matcher);
        Matcher matt = patt.matcher(pattern);
        String actual=null;
        while (matt.find())
            actual = pattern.substring(matt.start(), matt.end());
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"@waveaccess.com"})
    public void getEmailName_RegExp_Bad(String pattern) {
        String matcher = "^[^+@]+";
        Pattern patt = Pattern.compile(matcher);
        Matcher matt = patt.matcher(pattern);
        String actual=null;
        while (matt.find())
            actual = pattern.substring(matt.start(), matt.end());
        assertNull(actual);
    }
}
