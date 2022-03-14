package pageobjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.Config;
import utils.utils;

public class Indeed_pageObjects {

	WebDriver driver;
	utils xlUtils;

	public Indeed_pageObjects(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}


	@FindBy(id="text-input-what")
	WebElement What_Box;

	@FindBy(id="text-input-where")
	WebElement Where_Box;

	@FindBy(css="button[class='yosegi-InlineWhatWhere-primaryButton']")
	WebElement FindJobs;

	@FindBy(xpath="//button[@id='filter-dateposted']")
	WebElement Dropdown;

	@FindBy(xpath="//a[normalize-space()='Last 24 hours']")
	WebElement DropdownElement;

	@FindBy(id="popover-x")
	WebElement alert;

	@FindBy(xpath="//iframe[@title='widget containing checkbox for hCaptcha security challenge']")
	WebElement iframe_captcha;

	@FindBy(xpath="//div[@id='checkbox']")
	WebElement iframe_captcha1;

	@FindBy(xpath = "//div[@class='job_seen_beacon']")    
	List<WebElement> job_cards;
	
	@FindBy(xpath = "//div[@id='mosaic-provider-jobcards']/a")    
	List<WebElement> job_url;
	
	@FindBy(xpath="//iframe[@id='vjs-container-iframe']")
	WebElement frame_element;

	@FindBy(xpath="//h1[@class='icl-u-xs-mb--xs icl-u-xs-mt--none jobsearch-JobInfoHeader-title is-embedded']")
	WebElement job_title;

	@FindBy(xpath="//div[@class='jobsearch-CompanyInfoContainer']/div/div/div/div[2]/div")
	WebElement job_location;

	@FindBy(xpath="//div[@class='jobsearch-InlineCompanyRating icl-u-xs-mt--xs icl-u-xs-mb--md']")
	WebElement job_CompanyName;

	@FindBy(css="#jobDescriptionText")
	WebElement job_description;

	@FindBy(css="p[class='jobsearch-HiringInsights-entry--bullet'] span[class='jobsearch-HiringInsights-entry--text']")
	WebElement job_postedtime;

	@FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='pagination-list']/li"))
	List<WebElement> pagination;

	@FindBy(xpath="//a[@aria-label='Next']")
	WebElement next_page;

	public void WhatBox1() throws InterruptedException {
		What_Box.sendKeys(Config.ApiTestingKeyword);
		Thread.sleep(1000);
	}
	public void WhatBox2() throws InterruptedException {
		What_Box.sendKeys(Config.PostmanKeyword);
		Thread.sleep(1000);
	}
	public void WhatBox3() throws InterruptedException {
		What_Box.sendKeys(Config.RestAssuredKeyword);
		Thread.sleep(1000);
	}

	public void WhereBox() throws InterruptedException {
		Where_Box.sendKeys(Keys.CONTROL+"a");
		Where_Box.sendKeys(Keys.DELETE);
		Where_Box.sendKeys(Config.LocationKeyword);
		Thread.sleep(1000);
	}

	public void FindJobs_btn() throws InterruptedException {
		FindJobs.click();
		Thread.sleep(1000);
	}
	public void Dropdown_btn() throws InterruptedException {
		Dropdown.click();
		Thread.sleep(1000);
	}
	public void dropelements_btn() throws InterruptedException {
		DropdownElement.click();
		Thread.sleep(1000);
	}

	public void alert_box() {

		try {
			System.out.println("PopUp Alert");
			alert.click();
		}
		catch (Exception e) {
			System.out.println("No pop up");
		}
	}

	public void iframe_handle() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe_captcha));

			wait.until(ExpectedConditions.elementToBeClickable(iframe_captcha1)).click();
			System.out.println("Clicked the checkbox");
		}catch(Exception e) {}

	}

	public void JobCards() throws InterruptedException {
		int totcount = job_cards.size();
		System.out.println(totcount);
		Thread.sleep(1000);
	}

	public void frame() {
		driver.switchTo().frame(frame_element);
	}

	public String JobTitle() {
		return job_title.getText();
	}

	public String JobLocation() {
		return job_location.getText();
	}

	public String JobCompanyName() {
		return job_CompanyName.getText();
	}

	public String JobDescription() {
		return  job_description.getText();
	}

	public String JobPostedTime() {
		return  job_postedtime.getText();
	}

	public String TimeStamp() {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
		
	public void nextpageclick() {
		next_page.click();
	}

	public void ScrapData(String SheetName , String Keyword) throws InterruptedException, IOException {


		utils xlUtils=new utils(Config.EXCEL_PATH);

		ArrayList<String> data = new ArrayList<String>();
		
		data.add("Job Category");
		data.add("Job Title");
		data.add("Job Location");
		data.add("Job Company name");
		data.add("Job Description");
		data.add("Job posted Time");
		data.add("Job URL");
		data.add("Job Scraped Time");
		

		xlUtils.writeToExcel(data,SheetName,0);

		int pgSize = pagination.size();
		System.out.println(pgSize);
		
		int excelRow=1;
		//    for (int j = 1; j <= pgSize; j++)
		for (int j = 0; j < pgSize; j++)
		{
			Thread.sleep(2000);

			int totcount = job_cards.size();
			System.out.println(totcount);
			Thread.sleep(1000);

			////to get job title

			for(int i=1;i<totcount;i++) {

				alert_box();
				String JobURL = job_url.get(i).getAttribute("href");
				System.out.println("Job URL :" + JobURL);
				Thread.sleep(1000);
				
				job_cards.get(i).click(); 
				Thread.sleep(1000);		
				frame();	 
				Thread.sleep(1000);
				alert_box();
				String JobTitle = JobTitle();
				System.out.println("Job Title : " + JobTitle);

				String JobCompName = JobCompanyName();
				System.out.println("Job Company Name : " + JobCompName); 

				String JobLocation = JobLocation();
				System.out.println("Job Location : " + JobLocation);

				String JobDesc = JobDescription();
				System.out.println("Job Description : " + JobDesc);

				String Jobtime = JobPostedTime();
				System.out.println("Job Posted Time : " + Jobtime);

				String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
				System.out.println(timeStamp);

				data.clear();
				data.add(Keyword);
				data.add(JobTitle);
				data.add(JobCompName);
				data.add(JobLocation);
				data.add(JobDesc);
				data.add(Jobtime);  
				data.add(JobURL);
				data.add(timeStamp);
				
				xlUtils.writeToExcel(data,SheetName,excelRow);
				excelRow++;

				Thread.sleep(3000);
				driver.switchTo().defaultContent();
				Thread.sleep(1000);
			}

			try {
				Thread.sleep(5000);
				nextpageclick();
				Thread.sleep(5000);
				alert_box();
			}catch(Exception e) {
				break;
			}
		}
		System.out.println("WebScrapping Done successfully");
		driver.close(); 
	}


}




