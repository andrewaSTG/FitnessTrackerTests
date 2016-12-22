package Part7;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationConfig.AutomationConfig;
import junit.framework.Assert;
import testHelper.TestHelper;

public class Part7Test {
	
	private final String addMinutesURL = automationConfig.AutomationConfig.url + "/addMinutes.html";
	private final String addMinutesEnglishURL = automationConfig.AutomationConfig.url + "/addMinutes.html?language=en";
	private final String addMinutesSpanishURL = automationConfig.AutomationConfig.url + "/addMinutes.html?language=es";
	private final String addGoalURL = automationConfig.AutomationConfig.url + "/addGoal.html";
	
	private final String[] expectedAddMinutesTextEnglish = {"Add Minutes Exercised", "Minutes Exercised For The Day Today:", "Our goal for the day is:",
			                                                "Language : English | Spanish"};
	private final String[] expectedAddMinutesTextSpanish = {"Add Minutes Exercised", "Minutos ejercicio para el d�a de hoy:", "Our goal for the day is:",
															"Language : English | Spanish"};
	
	By englishLink = By.linkText("English");
	By spanishLink = By.linkText("Spanish");
	By activityDropDown = By.id("activities");

	@Test
	public void testGreetingText()
	{
		WebDriver driver = new FirefoxDriver();
		String expectedGreetingText = "Hello World";

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
	public void testAddMinutesElementsPresent()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addMinutesEnglishURL);

			errors += TestHelper.verifyElementExists(driver, By.id("minutes"), "Could not find minutes text field with ID of minutes\r\n");
			errors += TestHelper.verifyElementExists(driver, By.xpath("//input[@value='Enter Exercise']"), "Could not find submit button\r\n");
			errors += TestHelper.verifyElementExists(driver, englishLink, "Could not find English link.\r\n");
			errors += TestHelper.verifyElementExists(driver, spanishLink, "Could not find Spanish link.\r\n");
			errors += TestHelper.verifyElementExists(driver, activityDropDown, "Could not find activity dropdown.\r\n");

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@Test
	public void testAddMinutesTextEnglish()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addMinutesEnglishURL);

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();

			for(String expected : expectedAddMinutesTextEnglish)
			{
				if(bodyText.contains(expected) == false)
				{
					errors += "Expected page to contain " + expected + ".\r\n";
				}
			}

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@Test
	public void testAddMinutesTextSpanish()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addMinutesSpanishURL);

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();

			for(String expected : expectedAddMinutesTextSpanish)
			{
				if(bodyText.contains(expected) == false)
				{
					errors += "Expected page to contain " + expected + ".\r\n";
				}
			}

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}

	@Test
	public void testAddMinutesNavigation()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			// Verify the Spanish link
			driver.get(addMinutesEnglishURL);
			
			driver.findElement(spanishLink).click();
			
			if (driver.getCurrentUrl().equals(addMinutesSpanishURL) == false)
			{
				errors += "Spanish link did not go to spanish page.";
			}
			
			// Verify the English link
			driver.get(addMinutesSpanishURL);
			
			driver.findElement(englishLink).click();
			
			if (driver.getCurrentUrl().equals(addMinutesEnglishURL) == false)
			{
				errors += "English link did not go to English page.";
			}

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@Test
	public void verifyGoalPageText()
	{
		WebDriver driver = new FirefoxDriver();
		String goalText = "Enter Minutes:";

		try
		{
			driver.get(addGoalURL);

			WebElement body = driver.findElement(By.tagName("body"));

			String bodyText = body.getText();

			Assert.assertEquals(goalText, bodyText);
		}
		finally
		{
			driver.close();
		}
	}
	
	By minuteField = By.id("minutes");
	By addButton = By.xpath("//input[@value='Enter Goal Minutes']");
	
	@Test
	public void verifyGoalElementsPresent()
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addGoalURL);

			errors += TestHelper.verifyElementExists(driver, minuteField, "Could not find minutes text field with ID of minutes\r\n");
			errors += TestHelper.verifyElementExists(driver, addButton, "Could not find the Enter Goal Minutes button");

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@DataProvider(name = "invalidInt")
	public static Object[][] invalidInt() {
		return new Object[][] {{"-1"}, {"0"}, {"121"}};
	}
	
	@Test (dataProvider = "invalidInt")
	public void verifyGoalInvalidRange(String invalidInt)
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addGoalURL);
			
			driver.findElement(minuteField).clear();
			driver.findElement(minuteField).sendKeys(invalidInt);
			
			driver.findElement(addButton).click();
			
			if (driver.getCurrentUrl().equals(addMinutesURL) == true)
			{
				errors += "Goal page incorrectly navigated to the addMinutes page";
			}
			else
			{
				WebElement error = driver.findElement(By.id("goal.errors"));

				String errorText = error.getText();
				
				if (errorText.contains("Goal must be in the range 1 and 120") == false)
				{
					errors += "Error message 'Goal must be in the range 1 and 120' did not appear\r\n";
				}
			}
			
			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@DataProvider(name = "validInt")
	public static Object[][] validInt() {
		return new Object[][] {{"1"}, {"10"}, {"120"}, {"90"}, {"100"}};
	}
	
	@Test (dataProvider = "validInt")
	public void verifyGoalValidRange(String validInt)
	{
		WebDriver driver = new FirefoxDriver();
		String errors = "";

		try
		{
			driver.get(addGoalURL);
			
			driver.findElement(minuteField).clear();
			driver.findElement(minuteField).sendKeys(validInt);
			
			driver.findElement(addButton).click();
			
			if (driver.getCurrentUrl().equals(addMinutesURL) == false)
			{
				errors += "Goal button didn't navigate to the right page.\r\n";
			}
			else
			{
				WebElement body = driver.findElement(By.tagName("body"));

				String bodyText = body.getText();
				
				if (bodyText.contains("Our goal for the day is: " + validInt) == false)
				{
					errors += "Goal minutes weren't saved.\r\n";
				}
			}

			Assert.assertTrue(errors, errors.isEmpty());
		}
		finally
		{
			driver.close();
		}
	}
	
	@Test
	public void verifyActivityEndpoint()
	{
		String activitiesURL = AutomationConfig.url + "/activities.json";
		
		String response = TestHelper.makeRestRequest(activitiesURL).trim();
		
		System.out.println(response);
		
		Assert.assertEquals("[{\"desc\":\"Run\"},{\"desc\":\"Bike\"},{\"desc\":\"Swim\"}]", response);		
	}
	
	
	@Test
	public void helloWorldpdf()
	{
		String errors = "";

		if (TestHelper.checkLinkBroken(automationConfig.AutomationConfig.url + "/pdfs/helloworld.pdf") == false)
			errors += "Could not load up helloworld.pdf";

		Assert.assertTrue(errors, errors.isEmpty());
	}
}
