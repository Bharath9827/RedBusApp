package testCases;

import org.testng.annotations.Test;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class SearchBuses extends BaseClass {


    @Test
    public void searchBusesFromOriginToDestination() throws InterruptedException {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.addFromLocation("Mumbai");
        searchPage.addToLocation("Goa");
        searchPage.searchBusesAndScrollTillDataIsLoaded();
        System.out.println(searchPage.extractBusNames());

    }
}
