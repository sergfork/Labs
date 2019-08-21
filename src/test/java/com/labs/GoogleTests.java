package com.labs;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class GoogleTests {

    @Test
    @Parameters({"searchedWord", "findOnPages", "baseURL"})
    public void verifyCountOfSearchedPages(String searchedWord, int findOnPages, String baseURL) {
        open(baseURL);
        int result = new GooglePage()
                .searchFor(searchedWord)
                .searchPages(findOnPages);
        assertThat("Google found only " + result + " page(s) from " + findOnPages + " expected",
                result, is(findOnPages));
    }

    @Test
    @Parameters({"searchedWord", "baseURL"})
    public void verifyTopFiveLinks(String searchedWord, String baseURL) {
        open(baseURL);
        List<String> topics = new GooglePage()
                .searchFor(searchedWord)
                .getTopFiveTopics();
        for (String result : topics) {
            assertThat("Result should contains searched word",
                    result, containsString(searchedWord));
        }
    }
}
