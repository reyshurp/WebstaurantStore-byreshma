package searchItem;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.*;


public class ProductSearch {
    @Test
    public void PageSetUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeDriver chrome = new ChromeDriver();
        chrome.manage().window().maximize();

//Go to "https://www.webstaurantstore.com/
        chrome.get("https://www.webstaurantstore.com/");
//Waiting for website to load
        Thread.sleep(2000);

        //Search for stainless steel table
        chrome.findElement(name("searchval")).sendKeys("stainless work table" + Keys.ENTER);

        // Check the search result ensuring every product has the word 'Table' in its title
        List<WebElement> productTitles = chrome.findElements(new ByCssSelector("[data-testid=\"itemDescription\"]"));
        String searchText = "Table";
        for (WebElement titleElement : productTitles) {
            String titleText = titleElement.getText();
            // Compare titleText with your search text
            if (titleText.contains(searchText)) {
                System.out.println("Text found in title: " + titleText);
            } else {
                System.out.println("Text not found in title: " + titleText);
            }

        }
        Iterator<WebElement> var4 = productTitles.iterator();
        WebElement lastItemLink;
        while (var4.hasNext()) {
            lastItemLink = (WebElement) var4.next();
            assertTrue(lastItemLink.getText().contains("Table"));
        }
        // add the last of found items to cart
        lastItemLink = (WebElement) productTitles.get(productTitles.size() - 1);
        String linktext = lastItemLink.getText();
        System.out.println(linktext);
        lastItemLink.click();
        WebElement addToCartButton = chrome.findElement(By.cssSelector("[gtm-id=\"AddToCartATC\"]"));
        addToCartButton.click();
// Waiting to update the cart after adding item
        Thread.sleep(2000);
//view the cart with added item
        WebElement viewCart = chrome.findElement(new ByXPath("//*[contains(text(),'View Cart')]"));
        boolean isTextVisible = viewCart.isDisplayed();
        if (isTextVisible) {
            System.out.println("view cart element is visible");
        } else {
            System.out.println("view cart element not visible");
        }
        viewCart.click();

        //Empty Cart
        WebElement emptyCartButton = chrome.findElement(new ByXPath("//*[contains(text(),'Empty Cart')]"));
        emptyCartButton.click();

//Handling the modal to empty the cart
        WebElement reactModal = chrome.findElement(By.xpath("//header[contains(.,'Empty Cart')]"));
        WebElement ModalEmptyCartBtn = reactModal.findElement(By.xpath("//*[@id=\"td\"]/div[11]/div/div/div/footer/button[1]"));
        ModalEmptyCartBtn.click();

        chrome.quit();

    }
}





