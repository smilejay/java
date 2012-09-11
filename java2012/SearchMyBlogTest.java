package com.selenium.test;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
public class SearchMyBlogTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://smilejay.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	@Test
	public void testSearchMyBlog() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("s")).clear();
		driver.findElement(By.name("s")).sendKeys("Linux");
		driver.findElement(By.cssSelector("input.button")).click();
		int check1 = driver.findElement(By.className("title")).getText().indexOf("搜索结果");
		int check2 = driver.findElement(By.className("info")).getText().indexOf("关键字");
		int check3 = driver.findElement(By.className("info")).getText().indexOf("Linux");
		boolean flag = false;
		if ((check1 != -1) && (check2 != -1) && (check3 != -1) ) {
			flag = true;
		}
		System.out.println(driver.findElement(By.className("info")).getText());
		Assert.assertTrue(flag);
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
