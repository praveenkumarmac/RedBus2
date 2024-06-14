package in.redTrain.StepDefinition;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TrainBooking1 {
	public static WebDriver driver;
	public static WebDriverWait Wait;
	public static int day;

	@Given("Launch the browser and appilication")
	public void launch_the_browser_and_appilication() {
		try {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();		
			options.addArguments("disable-notifications");
			options.addArguments("disable-popups");
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
			String url = "https://www.redbus.in";
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
			Wait = new WebDriverWait(driver, Duration.ofSeconds(10));			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User clicks on redRail")
	public void user_clicks_on_red_rail() {
		try {
			//WebElement travelChoice = driver.findElement(By.xpath("//a[text()='redRail']"));
			WebElement travelChoice = driver.findElement(By.xpath("//span[text()='Train Tickets']"));		
			travelChoice.click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User enters the value in from place")
	public void user_enters_the_value_in_from_place() {
		try {
			String fromPlace = "Chennai";
			//WebElement from = driver.findElement(By.xpath("//label[text()='From']"));
			//WebElement from = driver.findElement(By.xpath("//div[@class='search-box search-box-src form-control']"));
			WebElement from = driver.findElement(By.xpath("//input[@id='src']"));		
			from.sendKeys(fromPlace);		
			WebElement frompl = driver.findElement(By.xpath("//div[text()='Chennai - All Stations']"));		
			frompl.click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User enters the value in to place")
	public void user_enters_the_value_in_to_place() {
		try {
			String toPlace = "Trichy";		
			//input[@id='src']
			WebElement to = driver.findElement(By.xpath("//input[@id='dst']"));		
			to.sendKeys(toPlace);		
			WebElement topl = driver.findElement(By.xpath("//div[text()='Tiruchchirapali']"));
			Wait.until(ExpectedConditions.elementToBeClickable(topl));
			topl.click();
			Thread.sleep(6000);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User clicks on date")
	public void user_clicks_on_date() throws InterruptedException {
		try {
			//WebElement date = driver.findElement(By.xpath("//span[text()='4' and contains(@class,'dkWAbH')]"));		
			//WebElement date = driver.findElement(By.xpath("//div[@class='date-text']"));
			//WebElement date = driver.findElement(By.xpath("//img[@class='train-icon']"));
			//WebElement date = driver.findElement(By.xpath("//span[@class='sc-htoDjs cvnjgw' and text()='14']"));
			//WebElement date = driver.findElement(By.xpath("//div[@class='home_calendar']"));
			//WebElement date = driver.findElement(By.xpath("//img[@class='train-icon']"));
			//WebElement date = driver.findElement(By.xpath("//div[@class='home_calendar']"));
			//			Thread.sleep(6000);
			//			WebElement date = driver.findElement(By.xpath("//div[@class='home_date_wrap']"));
			LocalDate date = LocalDate.now();
			day = date.getDayOfMonth();
			System.out.println("Today date:"+day);
			day++;

			WebElement dateButton = driver.findElement(By.xpath("//img[@alt='calendar_icon']"));
			Wait.until(ExpectedConditions.elementToBeClickable(dateButton));
			Actions act = new Actions(driver);
			act.moveToElement(dateButton).build();
			Thread.sleep(10000);
			dateButton.click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User selects a data  in the Date DropDown")
	public void user_selects_a_data_in_the_date_drop_down() {
		try {			
			WebElement dateAction = driver.findElement(By.xpath("//span[text()="+day+" "+"]"));
			Wait.until(ExpectedConditions.elementToBeClickable(dateAction));
			dateAction.click();

			//			Actions act = new Actions(driver);
			//			act.click(date);
			//			
			//			date.click();
			//			Thread.sleep(6000);
			//			JavascriptExecutor js = (JavascriptExecutor)driver;
			//			js.executeScript("arguments[0].click", date);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User selects a free cancellation")
	public void user_selects_a_free_cancellation() {
		try {
			//Actions key = new Actions(driver);
			driver.findElement(By.xpath("//div[@class='checkbox_wrap']")).click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@When("User clicks on search button")
	public void user_clicks_on_search_button() {
		try {
			//WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'SEARCH')]"));
			WebElement searchButton = driver.findElement(By.xpath("//button[text()='search trains']"));
			searchButton.click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Then("Validate the train displayed in the UI")
	public void validate_the_train_displayed_in_the_ui() {	
		try {
			List<WebElement> trains= driver.findElements(By.xpath("//span[@class='srp_train_name']"));
			List<WebElement> deptime = driver.findElements(By.xpath("//span[@class='srp_departure_time']"));
			List<WebElement> arrtime = driver.findElements(By.xpath("//span[@class='srp_arrival_time']"));
			List<WebElement> place = driver.findElements(By.xpath("//div[@class='srp_timimngs_wrap srp_src_dst_stations']"));
			for (int i=0;i<trains.size();i++) {
				if(i==trains.size()-1) {
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("window.scrollTo(0,document.body.scrollHeight)",trains.get(i));
					trains= driver.findElements(By.xpath("//span[@class='srp_train_name']"));
					deptime = driver.findElements(By.xpath("//span[@class='srp_departure_time']"));
					arrtime = driver.findElements(By.xpath("//span[@class='srp_arrival_time']"));
					place = driver.findElements(By.xpath("//div[@class='srp_timimngs_wrap srp_src_dst_stations']"));
				}
				else {
					String train = trains.get(i).getText();
					String DepartureTime = deptime.get(i).getText();   
					String ArrivalTime = arrtime.get(i).getText();
					String location = place.get(i).getText();
					System.out.println("train: "+train+"Departure Time:"+DepartureTime+"Arrival Time:"+ArrivalTime+"place:"+location);
					//boolean src1 = t4.toLowerCase();
				}
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Then("validate the trains displayed as per given value")
	public void validate_the_trains_displayed_as_per_given_value() {
		try {
			System.out.println("\n"+"Trains from Chennai to Trichy: "+"\n");
			List<WebElement> trains = driver.findElements(By.xpath("//span[@class='srp_train_name']"));
			List<WebElement> depPlaces = driver.findElements(By.xpath("//div[text()='Chennai - All Stations']"));
			List<WebElement> arrPlaces = driver.findElements(By.xpath("//div[text()='Tiruchchirapali']"));
			for (int i = 0; i < trains.size(); i++) {
				String depPlace = depPlaces.get(i).getText();
				String arrPlace = arrPlaces.get(i).getText();
				System.out.println(trains);
				System.out.println(depPlace);
				System.out.println(arrPlace);
				String from = "Chennai";
				String to = "Tiruchrpali Twn";
				if (from.equals(depPlace) && to.equals(arrPlace)) {
					System.out.println("Trains: " + trains.get(i).getText());
				}else {
					System.out.println("No Trains: " + trains.get(i).getText());
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
