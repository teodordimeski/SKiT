package finki.ukim.mk.lab4.part1.pages;

import finki.ukim.mk.lab4.part1.model.UserDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserAddPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private String employeeName = "Admin";

    public UserAddPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    private final By usernameField = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By passwordField = By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By saveButton = By.xpath("//button[@type='submit']");
    private final By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private final By dropdownArrow = By.xpath("//i[contains(@class,'oxd-select-text--arrow')]");
    private final By successToast = By.xpath("//div[contains(@class,'oxd-toast--success')]");

    private void selectDropdownOption(int arrowIndex, String optionText) throws InterruptedException {
        List<WebElement> arrows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownArrow));
        arrows.get(arrowIndex).click();
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + optionText + "']");
        if (driver.findElements(optionLocator).isEmpty()) {
            optionLocator = By.xpath("//div[@role='listbox']//div[text()='" + optionText + "']");
        }
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    private void selectEmployeeName() throws InterruptedException {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(employeeName);
        WebDriverWait pause = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            pause.until(driver -> false);
        } catch (TimeoutException e) {
            // explicitly wait 2s
        }
        List<WebElement> autocompleteDivs = driver.findElements(By.cssSelector("div.oxd-autocomplete-dropdown"));
        if (autocompleteDivs.isEmpty()) {
            throw new RuntimeException("No autocomplete dropdown for: " + employeeName);
        }
        List<WebElement> suggestions = autocompleteDivs.get(0).findElements(By.xpath(".//div"));
        if (suggestions.isEmpty()) {
            throw new RuntimeException("No suggestions for: " + employeeName);
        }
        suggestions.get(0).click();
    }

    public void addUser(UserDetails user) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

        selectDropdownOption(0, user.getUserRole());

        this.employeeName = user.getEmployeeNamePartial();
        selectEmployeeName();

        selectDropdownOption(1, user.getStatus());

        driver.findElement(usernameField).sendKeys(user.getUsername());
        driver.findElement(passwordField).sendKeys(user.getPassword());
        driver.findElement(confirmPasswordField).sendKeys(user.getConfirmPassword());

        driver.findElement(saveButton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(successToast));
        wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'oxd-button--secondary')]//i[contains(@class,'bi-plus')]")));
    }
}

