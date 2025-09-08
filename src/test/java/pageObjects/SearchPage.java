package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testCases.SearchBuses;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage{

    private static final Logger logger = LoggerFactory.getLogger(SearchBuses.class);


    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(text(),'From')]")
    WebElement fromLocation;

    @FindBy(xpath = "//i[contains(@class,'icon-dropping_point')]")
    WebElement toLocation;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper')]")
    WebElement searchSuggestionBox;

    @FindBy(xpath = "//button[contains(@class,'searchButton')]")
    WebElement searchBusesButton;

    @FindBy(xpath = "//button[text()='Proceed']")
    WebElement proceedButtonForSearch;

    @FindBy(xpath = "//div[contains(@class,\"busesFoundText\")]")
    WebElement busesCountText;

    By busNameLocator = By.xpath("//div[contains(@class,'travelsName')]");

    By busTile = By.xpath("//li[contains(@class,'tupleWrapper')]");


    public void addFromLocation(String cityName) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(fromLocation)).click();
        wait.until(ExpectedConditions.visibilityOf(searchSuggestionBox));
        WebElement fromSearchTextBox = driver.switchTo().activeElement();
        fromSearchTextBox.sendKeys(cityName);
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//div[text()='"+ cityName +"']"))).click();
    }

    public void addToLocation(String cityName){
       // wait.until(ExpectedConditions.elementToBeClickable(toLocation)).click();
        wait.until(ExpectedConditions.visibilityOf(searchSuggestionBox));
        WebElement searchTextBox = driver.switchTo().activeElement();
        searchTextBox.sendKeys(cityName);
        wait.until(ExpectedConditions.
                elementToBeClickable(By.xpath("//div[text()='"+ cityName +"']"))).click();
    }

    public void searchBusesAndScrollTillDataIsLoaded() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(searchBusesButton)).click();
        try {
            wait.until(ExpectedConditions.visibilityOf(proceedButtonForSearch));
            wait.until(ExpectedConditions.elementToBeClickable(proceedButtonForSearch)).click();
        } catch (Exception e) {
            System.out.println("Proceed button not visible or clickable.");
        }
        String numOfBuses = wait.until(ExpectedConditions.visibilityOf(busesCountText)).getText();
        System.out.println(numOfBuses);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        while(true){
            List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(busTile));
            List<WebElement> endOfList = driver.findElements(By.xpath("//span[contains(text(),'End of list')]"));
            if(!endOfList.isEmpty()){
                System.out.println("Total buses count: "+rowList.size());
                break;
            }
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})",rowList.get(rowList.size()-3));
        }
    }

    public List<String> extractBusNames(){
        List<WebElement> buses = driver.findElements(busNameLocator);
        List<String> busNames = new ArrayList<>();
        for(WebElement element: buses){
            busNames.add(element.getText());
        }
        System.out.println();
        System.out.println(busNames.size());
        return busNames;
    }

}
