package Part4;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;
import testHelper.TestHelper;

public class Part4Test {
	private final String expectedGreetingText = "Hello World";
	private final String[] expectedAddMinutesText = {"Add Minutes Exercised", "Minutes Exercise For Today:"};
	
	@Test
	public void testGreetingText()
	{
		WebDriver driver = new FirefoxDriver();

		try
		{
			driver.get(automationConfig.AutomationConfig.url + "/greeting.html");

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();

			Assert.assertEquals(expectedGreetingText, bodyText);
		}
		finally
		{
			driver.close();
		}
	}	
	
	@Test
	public void testAddMinutesText()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(automationConfig.AutomationConfig.url + "/addMinutes.html");

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();
			
			for(String expected : expectedAddMinutesText)
			{
				if(bodyText.contains(expected) == false)
				{
					errors += "Expected page to contain " + expected + ".\r\n";
				}
			}
			
			errors += TestHelper.verifyElementExists(driver, By.id("minutes"), "Could not find minutes text field with ID of minutes\r\n");
			errors += TestHelper.verifyElementExists(driver, By.tagName("input"), "Could not find submit button\r\n");

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
}
