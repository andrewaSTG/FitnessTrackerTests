package testHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class TestHelper {
	public static String verifyElementExists(WebDriver driver, By findBy, String errorMessage)
	{
		try
		{
			driver.findElement(findBy);
		}
		catch (NoSuchElementException e)
		{
			return errorMessage;
		}

		return "";
	}

	public static boolean checkLinkBroken(String url)
	{
		HttpURLConnection connection;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			int response = connection.getResponseCode();

			// Check if response is in one of the 'acceptable' ranges
			if (response >= 200 && response < 400)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public static String makeRestRequest(String url)
	{
		HttpURLConnection connection;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-length", "0");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			
			connection.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
