package com.labs;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class GooglePage {
    private static final Logger logger = LoggerFactory.getLogger(GooglePage.class);
    private static final String INPUT_FOR_QUERY_LOC = "q";

    public SearchResultsPage searchFor(String text) {
        $(By.name(INPUT_FOR_QUERY_LOC)).should(visible).val(text).pressEnter();
        logger.info("Searched text is: " + text);
        return new SearchResultsPage();
    }

}
