package indeed;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.utils;
public class IndeedScrapingPage{
	
	WebDriver driver=null;
	
	@Test
	public void Indeedpage() throws IOException, InterruptedException {	
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	//	driver.get("https://ca.indeed.com/?r=us");
		driver.get("https://www.indeed.com/");
	    driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Api Testing");
	    Thread.sleep(1000);  
	    WebElement element=driver.findElement(By.id("text-input-where"));
	    element.sendKeys(Keys.CONTROL+"a");
	    element.sendKeys(Keys.DELETE);
	    element.sendKeys("Remote");
	    Thread.sleep(1000);  
	    	    
	    driver.findElement(By.cssSelector("button[class='yosegi-InlineWhatWhere-primaryButton']")).click();
		Thread.sleep(1000);
		
			
		WebElement dropdown = driver.findElement(By.xpath("//button[@id='filter-dateposted']"));
		dropdown.click();
		Thread.sleep(1000);  
		driver.findElement(By.xpath("//a[normalize-space()='Last 24 hours']")).click();
		Thread.sleep(5000);
			
		try {
			System.out.println("PopUp Here");
			driver.findElement(By.id("popover-x")).click();
		}
		catch (Exception e) {
			System.out.println("No pop up");
		}
		
		try {
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	                    By.xpath("//iframe[@title='widget containing checkbox for hCaptcha security challenge']")));
	           
	            wait.until(ExpectedConditions.elementToBeClickable(
	                        By.xpath("//div[@id='checkbox']"))).click();
	 
	            System.out.println("Clicked the checkbox");
	}catch(Exception e) {}
	    
	    utils xlUtils=new utils(Config.EXCEL_PATH);
		
	    ArrayList<String> data = new ArrayList<String>();
		data.add("Job Title");
		data.add("Job Location");
		data.add("Job Company name");
		data.add("Job Description");
		data.add("Job posted Time");
		data.add("Time stamp");
		
		xlUtils.writeToExcel(data,Config.ApiTestingsheetName,0);
		   
				int excelRow=0;
				
	    List<WebElement> pagination = driver.findElements(By.xpath("//ul[@class='pagination-list']/li"));
	    int pgSize = pagination.size();
	    System.out.println(pgSize);
	    for (int j = 1; j < pgSize; j++) {
	    Thread.sleep(2000);
	  //  WebElement pagei = driver.findElement(By.xpath("(//ul[@class='pagination-list']/li)[" + j + "]"));  
      //    WebElement pagei = driver.findElement(By.xpath("//a[@aria-label='Next']"));
	//	  driver.findElement(By.xpath("//div[@class='pagination']/ul/li["+j+"]"));
		
	    //// To find no of job cards 
	
	    List <WebElement> job_cards=driver.findElements(By.xpath("//div[@class='job_seen_beacon']"));
	   int totcount = job_cards.size();
	   System.out.println(totcount);
	   
	   data.clear();
	 
	    ////to get job title
	   for(int i=0;i<totcount;i++) {
		 		
		job_cards.get(i).click();     
		
		Thread.sleep(1000);  		
	    WebElement frame_element=driver.findElement(By.xpath("//iframe[@id='vjs-container-iframe']"));
	    driver.switchTo().frame(frame_element);
	    
	    String URL = driver.getCurrentUrl();
	    System.out.println(URL);
	 
	  try {
	   WebElement Job_title= driver.findElement(By.xpath("//h1[@class='icl-u-xs-mb--xs icl-u-xs-mt--none jobsearch-JobInfoHeader-title is-embedded']"));
	    String JobTitle=Job_title.getText();
	   System.out.println(JobTitle);
	   data.add(JobTitle);
	  }catch(Exception e) {}
	    
	  try {
	   WebElement jobCompName = driver.findElement(By.xpath("//div[@class='jobsearch-InlineCompanyRating icl-u-xs-mt--xs icl-u-xs-mb--md']"));
	   String JobCompName=jobCompName.getText();
	   System.out.println(JobCompName);
	   data.add(JobCompName);	   
	  }catch(Exception e) {}
	  
	  try {	    
	   WebElement jobLocation = driver.findElement(By.xpath("//div[@class='jobsearch-CompanyInfoContainer']/div/div/div/div[2]/div"));
	   String Joblocation = jobLocation.getText();
	   System.out.println(Joblocation);
	   data.add(Joblocation);
	  }catch(Exception e) {}
	  
	  try { 
	   WebElement job_description = driver.findElement(By.cssSelector("#jobDescriptionText"));
	    String JobDesc = job_description.getText();   
	    System.out.println(JobDesc);
	    data.add(JobDesc);
	  }catch(Exception e) {}
		   
	  try {
	   WebElement job_postedtime = driver.findElement(By.cssSelector("p[class='jobsearch-HiringInsights-entry--bullet'] span[class='jobsearch-HiringInsights-entry--text']"));
	   String JobTime = job_postedtime.getText(); 
	   System.out.println(JobTime);
	   data.add(JobTime);
	  }catch(Exception e) {}
	   
	   String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
	   System.out.println(timeStamp);
	   data.add(timeStamp);
	   	
		xlUtils.writeToExcel(data,Config.ApiTestingsheetName,(i+excelRow));
		 
		 driver.switchTo().defaultContent();
	   }
	   excelRow=excelRow+totcount;
	   System.out.println(excelRow);
	   try {
	   WebElement pagei= driver.findElement(By.xpath("//a[@aria-label='Next']"));
	   Thread.sleep(5000);
	   pagei.click();
	   Thread.sleep(5000);
	   }catch(Exception e) {
		   break;
	   }
		    }
	   System.out.println("WebScrapping Done successfully");
	 driver.close(); 
	}

}



