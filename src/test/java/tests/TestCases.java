package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import config.Config;
import pageobjects.Indeed_pageObjects;
import utils.BaseClass;

public class TestCases {
	
	@Test(priority=1)

	public static void Apitests() throws IOException, InterruptedException {

		BaseClass.InitializeDriver();
		WebDriver driver = BaseClass.driver;

		Indeed_pageObjects IP = new  Indeed_pageObjects(driver);
		IP.WhatBox1();
		IP.WhereBox();
		IP.FindJobs_btn();
		//	IP.Dropdown_btn();
		//	IP.dropelements_btn();
		//	IP.alert_box();
		IP.ScrapData(Config.ApiTestingsheetName,Config.ApiTestingKeyword);

	}

	@Test(priority=2)

	public static void Postmantests() throws IOException, InterruptedException {

		BaseClass.InitializeDriver();
		WebDriver driver = BaseClass.driver;

		Indeed_pageObjects IP = new  Indeed_pageObjects(driver);
		IP.WhatBox2();
		IP.WhereBox();
		IP.FindJobs_btn();
	//	IP.Dropdown_btn();
	//	IP.dropelements_btn();
	//	IP.alert_box();
		IP.ScrapData(Config.PostmansheetName , Config.PostmanKeyword);

	}
	
	@Test(priority=3)

	public static void RestAssuredtests() throws IOException, InterruptedException {

		BaseClass.InitializeDriver();
		WebDriver driver = BaseClass.driver;

		Indeed_pageObjects IP = new  Indeed_pageObjects(driver);

		IP.WhatBox3();
		IP.WhereBox();
		IP.FindJobs_btn();
		//IP.Dropdown_btn();
		//IP.dropelements_btn();
		//IP.alert_box();
		IP.ScrapData(Config.RestAssuredsheetName , Config.RestAssuredKeyword);

	}

}
