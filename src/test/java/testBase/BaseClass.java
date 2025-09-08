package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {

    public WebDriver driver;
    public Properties properties;

    @BeforeClass
    public void setUp() throws IOException {
        FileReader configFile = new FileReader("src/test/resources/config.properties");
        properties = new Properties();
        properties.load(configFile);

        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(properties.getProperty("appURL"));

    }

    @Test
    public void firstTest(){
        System.out.println("First test passed");
        System.out.println(driver.getTitle());
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
