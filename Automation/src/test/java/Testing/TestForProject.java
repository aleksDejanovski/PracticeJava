package Testing;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestForProject {

	

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(14));
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("dejanovskia@yahoo.com");
		driver.findElement(By.id("userPassword")).sendKeys("aA123456789");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		
		// find all elements and store in list
		List <WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//find element that has text ZARA COAT 3 using stream
		WebElement prod = products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		//click add to card button of desired product
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//wait for toast message to appear
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//wait until animation disapears
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		
		//click CartButton
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//geting titles of products in card
		List <WebElement> cardProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cardProducts.stream().anyMatch(cardProduct -> cardProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//click Checkout button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		
		
		//driver.findElement(By.cssSelector(".btn-primary")).click();
		
		//click submit
		a.sendKeys(Keys.PAGE_DOWN);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".actions a"))).click();
		//WebElement element = driver.findElement(By.cssSelector(".actions a"));
		//element.click();
		
		//store and assert confirm message
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertEquals(confirmMessage, " Thankyou for the order.");
		
		
		driver.quit();
		
		
		
	
		
	
		
		
	}

}