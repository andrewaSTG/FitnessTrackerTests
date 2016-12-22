package Part2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Part2Test {
	
	private final String expectedText = "Hello World!";
	
	@Test
	public void test()
	{
		WebDriver driver = new FirefoxDriver();

		try
		{
			driver.get(automationConfig.AutomationConfig.url);

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();

			Assert.assertEquals(expectedText, bodyText);
		}
		finally
		{
			driver.close();
		}
	}

}
