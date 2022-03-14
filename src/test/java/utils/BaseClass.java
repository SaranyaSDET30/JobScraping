package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
				public static WebDriver driver;
				
	public static void InitializeDriver() {
					
		 			WebDriverManager.chromedriver().setup();
		 			//// headless mode
//		 			ChromeOptions options = new ChromeOptions();
//		 			options.addArguments("headless");
//		 			 driver=new ChromeDriver(options);
		 			 driver=new ChromeDriver();
				     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				     driver.manage().window().maximize();
				     driver.get(Config.URL);
			
	}
	
}
