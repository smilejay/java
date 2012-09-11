package com.selenium.test;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class TempGoogle {
	public static void main(String[] args) {
		final String sUrl = "http://www.google.com.hk/";
		System.setProperty("webdriver.ie.driver","C:\\Users\\yren9\\workspace\\selenium\\IEDriverServer.exe");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		WebDriver oWebDriver = new InternetExplorerDriver(ieCapabilities);
		oWebDriver.get(sUrl);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 // Use name locator to identify the search input field.
		WebElement oSearchInputElem = oWebDriver.findElement(By.name("q"));
		oSearchInputElem.sendKeys("smilejay");
		WebElement oGoogleSearchBtn = oWebDriver.findElement(By.xpath("//input[@name='btnK']"));
		oGoogleSearchBtn.click();
		try {
			Thread.sleep(5000);
		}
		catch(InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		oWebDriver.close();
	}
}
