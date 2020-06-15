package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ParentTestIMDb {
	 WebDriver driver;
	 By searchBoxLocator = By.id("suggestion-search");
	 By searchButtonLocator = By.id("suggestion-search-button");

	public  void setUp() {
		//System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");  //propiedad explorador chrome		
		driver = new ChromeDriver();  //driver de chrome
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("http:\\imdb.com");//navegar a la pagina de IMDB.com
	}

	public  void clickElement(By elementLocator) {

		driver.findElement(elementLocator).click();
	}

	public  void inputText(By elementLocator, String text) {
		driver.findElement(elementLocator).sendKeys(text);
	}

	public  void verifyLinkExists(String linkText) {
		try { // para que se ponga el try cathc en automatico se selecciona el if else y se da clic derecho se selcciona sorroun with > try catch block
			if(driver.findElement(By.partialLinkText(linkText)).isDisplayed())
				System.out.println("The movie star " + linkText + " is displayed");
		} catch (Exception e) {  //lo del catch es lo que corresponde al else
			System.out.println("The movie star " + linkText + " is not displayed");
			System.exit(-1);
		}
	}

	public  void testMovieSearch(String movieName, String movieStar, String movieYear) {

		inputText(searchBoxLocator, movieName);

		clickElement(searchButtonLocator);

		//Verificar que exista un link de esa pelicula


		clickMovieLink(movieName, movieYear);

		//verificar que el director sea correcto
		verifyLinkExists(movieStar);
	}


	public  void clickMovieLink(String movieName, String movieYear) {
		String movieLinkXpath = "//td[contains(.,'"+ movieYear +"')]//a[contains(text(), '"+ movieName +"')]";
		driver.findElement(By.xpath(movieLinkXpath)).click();		
	}

	public  void verifyLinkExists(String movieName, String movieYear) {
		WebElement correctColumn = null;

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Results for ']")));

		String movieXpath = "//h3[text() = 'Titles']/following-sibling::table//td";
		List<WebElement> movieLinks = driver.findElements(By.xpath(movieXpath));
		for(WebElement we: movieLinks) {
			if(we.getText().contains(movieYear)) {
				correctColumn = we;
				break;
			}	
		}
		
		if(correctColumn != null)
			System.out.println("Movie " + movieName + " exists.");
		else
			System.out.println("Movie " + movieName + " does not exist.");
		
		
	}

	public  void tearDown() {
		driver.quit();
	}


}
