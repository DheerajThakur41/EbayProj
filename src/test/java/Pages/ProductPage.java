package Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import base.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ProductPage extends BasePage {

	By aboutBrand = By.xpath("//android.view.View[@content-desc='Visit the Apple Store']/android.widget.TextView");
	By descriptionLocator = By.xpath("//*[@resource-id='title']");
	By priceLocator = By
			.xpath("//*[@resource-id='atfRedesign_priceblock_priceToPay']//*[@class='android.widget.EditText']");

	By cartValue = By.xpath("//*[@resource-id='com.amazon.mShop.android.shopping:id/action_bar_cart_count']");
	By addToCartLocator = By.xpath("//*[@text='Added to cart']");

	Logger logger = LoggerFactory.getLogger(ProductPage.class);

	public ProductPage(AndroidDriver<AndroidElement> aDriver) {
		super(aDriver);
	}

	public List<String> get_Name_Price_Description() throws InterruptedException {

		logger.debug("Inside get_Name_Price_Description function ");

		TimeUnit.SECONDS.sleep(5);

		String brandName = getText(aboutBrand,0);
		String description = driver.findElement(descriptionLocator).getText();
		String rawPriceVal = getText(priceLocator, 0);
		String price="";
		if(rawPriceVal.equals("0.0"))
			price="0.0, Not Able to Get PRICE ";
		else
			price = rawPriceVal.split(" ")[1];

		System.out.println("brandName----" + brandName + "description-----" + description + "price-----" + price);

		logger.debug("brandName---" + brandName);
		logger.debug("description---" + description);
		logger.debug("price---" + price);

		List<String> itemValues = new ArrayList<String>();
		itemValues.add(brandName);
		itemValues.add(description);
		itemValues.add(price);

		scrollTo();

		return itemValues;

	}

	public boolean addToCart() {
		scrolltoElement("Add to Cart");
		clickUsingTextContains("Add to Cart");
		sleep(4);
		driver.findElementByAccessibilityId("Cart").isDisplayed();
		boolean addToCart=(getText(addToCartLocator, 0).equals("Added to cart"));
		boolean cartValueShouldBe1=driver.findElement(cartValue).getText().equals("1");
		logger.debug("Proceed To Checkout Popup iSDisplayed---" + cartValueShouldBe1);
		return (addToCart || cartValueShouldBe1);
	}

}
