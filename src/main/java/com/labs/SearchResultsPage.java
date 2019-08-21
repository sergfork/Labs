package com.labs;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);

    private static final String CURRENT_NUMBER_OF_PAGE_LOC = "#foot #nav td.cur";
    private static final String NEXT_PAGE_LOC = "#foot #nav #pnnext";
    private static final String RESULT_LOC = "#search .g"; // "#ires .g";

    private List<String> getTextResults() {
        return $$(RESULT_LOC).texts();
    }

    private int nextPage() {
        SelenideElement currentPageNumber = $(CURRENT_NUMBER_OF_PAGE_LOC);
        int orderNumber = Integer.parseInt(currentPageNumber.text());
        logger.info("Current page before is: " + orderNumber);
        orderNumber++;
        $(NEXT_PAGE_LOC).hover();
        $(NEXT_PAGE_LOC).should(visible).click();
        currentPageNumber.shouldHave(text(Integer.toString(orderNumber)));
        logger.info("New page is: " + orderNumber);
        return orderNumber;
    }

    private boolean isNextPageAvailable() {
        return $$(NEXT_PAGE_LOC).size() > 0;
    }

    public List<String> getTopFiveTopics() {
        return getTextResults().stream().limit(5).peek(event -> logger.info("Post: " + event))
                .collect(Collectors.toList());
    }

    public int searchPages(int findPages) {
        int currentPage = 1;
        while (currentPage < findPages) {
            SearchResultsPage results = new SearchResultsPage();
            List<String> foundTexts = results.getTextResults();
            logger.info("Found " + foundTexts.size() + " result(s) on the page");
            if (isNextPageAvailable()) {
                currentPage = results.nextPage();
            } else {
                return currentPage;
            }
        }
        return currentPage;
    }

}
