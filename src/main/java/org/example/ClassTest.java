package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class ClassTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Test Accept Cookies Functionality")
    public void testAcceptCookies() {
        try {
            driver.get("https://www.sas.am/en/");
            WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("cookies__btn")));
            cookiesButton.click();
            Assertions.assertFalse(cookiesButton.isDisplayed(), "Cookies button should not be visible after accepting");
        } catch (Exception e) {
            Assertions.fail("Test case failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Search for Milk")
    public void testSearchForMilk() {
        try {
            driver.get("https://www.sas.am/en/");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='I want to buy...']")));
            searchInput.sendKeys("milk");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("s-result__title")));
            List<WebElement> searchResults = driver.findElements(By.className("s-result__title"));
            Assertions.assertTrue(searchResults.size() > 0, "At least one search result should be present");
            String expectedText = "Milk 'Dili' 750ml, richness: 3.6%";
            Assertions.assertEquals(expectedText, searchResults.get(0).getText(), "First search result does not match the expected text");

        } catch (Exception e) {
            Assertions.fail("Test case failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Login Modal Appears")
    public void testLoginModalAppears() {
        try {
            driver.get("https://www.sas.am/en/");
            WebElement loginSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-header__link-text")));
            Assertions.assertEquals("Log in", loginSpan.getText(), "Login text is not 'Log in'");
            loginSpan.click();
            WebElement modalBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-window__body")));
            Assertions.assertTrue(modalBody.isDisplayed(), "Modal window did not appear after clicking 'Log in'");
        } catch (Exception e) {
            Assertions.fail("Test case failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test URL after Clicking on Toggler")
    public void testURLAfterTogglerClick() {
        try {
            driver.get("https://www.sas.am/en/");
            WebElement togglerDiv = wait.until(ExpectedConditions.elementToBeClickable(By.className("toggler")));
            togglerDiv.click();
            wait.until(ExpectedConditions.urlToBe("https://www.sas.am/food/en/"));
            Assertions.assertEquals("https://www.sas.am/food/en/", driver.getCurrentUrl(), "Page URL is not as expected");
        } catch (Exception e) {
            Assertions.fail("Test case failed: " + e.getMessage());
        }
    }
}
