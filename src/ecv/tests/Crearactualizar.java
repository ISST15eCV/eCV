// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class Crearactualizar {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void crearactualizar() {
    driver.get("http://localhost:8080/eCV/");
    driver.manage().window().setSize(new Dimension(1280, 1413));
    driver.findElement(By.cssSelector("form:nth-child(2) > .headerButton")).click();
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).sendKeys("Mario");
    driver.findElement(By.name("sname")).click();
    driver.findElement(By.name("sname")).sendKeys("Arroyo");
    driver.findElement(By.name("nif")).click();
    driver.findElement(By.name("nif")).sendKeys("12321123q");
    driver.findElement(By.name("bday")).click();
    driver.findElement(By.name("bday")).sendKeys("1");
    driver.findElement(By.name("bmonth")).click();
    driver.findElement(By.name("bmonth")).sendKeys("1");
    driver.findElement(By.name("byear")).click();
    driver.findElement(By.name("byear")).sendKeys("1999");
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).sendKeys("test@email.com");
    driver.findElement(By.name("password")).click();
    driver.findElement(By.name("password")).sendKeys("1234");
    driver.findElement(By.name("password2")).click();
    driver.findElement(By.name("password2")).sendKeys("1234");
    driver.findElement(By.cssSelector(".logButton")).click();
    {
      WebElement dropdown = driver.findElement(By.name("gender"));
      dropdown.findElement(By.xpath("//option[. = 'Hombre']")).click();
    }
    {
      WebElement element = driver.findElement(By.name("gender"));
      Action builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.name("gender"));
      Action builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.name("gender"));
      Action builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.name("gender")).click();
    driver.findElement(By.name("tlf")).click();
    driver.findElement(By.name("tlf")).sendKeys("123456789");
    driver.findElement(By.name("mobile")).click();
    driver.findElement(By.name("mobile")).sendKeys("123456789");
    driver.findElement(By.name("fax")).click();
    driver.findElement(By.name("aAddr")).click();
    driver.findElement(By.name("aAddr")).click();
    driver.findElement(By.name("aAddr")).sendKeys("calle 1");
    driver.findElement(By.name("aCity")).click();
    driver.findElement(By.name("aCity")).sendKeys("Madrid");
    driver.findElement(By.name("aRegion")).click();
    driver.findElement(By.name("aRegion")).sendKeys("Madrid");
    driver.findElement(By.name("aCountry")).click();
    driver.findElement(By.name("aCountry")).sendKeys("España");
    driver.findElement(By.name("nAddr")).click();
    driver.findElement(By.name("nAddr")).sendKeys("calle 2");
    driver.findElement(By.name("nCity")).click();
    driver.findElement(By.name("nCity")).sendKeys("Madrid");
    driver.findElement(By.name("nRegion")).click();
    driver.findElement(By.name("nCity")).sendKeys("Salamanca");
    driver.findElement(By.name("nRegion")).click();
    driver.findElement(By.name("nRegion")).sendKeys("Castilla y León");
    driver.findElement(By.name("nCountry")).click();
    driver.findElement(By.name("nCountry")).sendKeys("España");
    driver.findElement(By.name("freeText")).click();
    driver.findElement(By.name("freeText")).sendKeys("Datos opcionales");
    driver.findElement(By.cssSelector(".saveButton:nth-child(1)")).click();
    driver.findElement(By.name("file")).click();
    driver.findElement(By.name("file")).sendKeys("C:\fakepath\usuario.png");
    driver.findElement(By.cssSelector("p > button")).click();
    driver.findElement(By.cssSelector("form:nth-child(2) > .exportBttn")).click();
    vars.put("root", driver.getWindowHandle());
    driver.switchTo().window("vars.get("win9770").toString()");
    driver.switchTo().window("vars.get("root").toString()");
    driver.findElement(By.cssSelector("form:nth-child(3) > .exportBttn")).click();
    driver.findElement(By.cssSelector(".exportBttn")).click();
    driver.findElement(By.name("web")).click();
    driver.findElement(By.name("web")).sendKeys("pagina web");
    driver.findElement(By.name("aAddr")).click();
    driver.findElement(By.name("aAddr")).click();
    driver.findElement(By.name("aAddr")).sendKeys("calle 3");
    driver.findElement(By.cssSelector(".saveButton:nth-child(2)")).click();
    driver.findElement(By.cssSelector("form:nth-child(2) > .exportBttn")).click();
    driver.switchTo().window("vars.get("win5035").toString()");
    driver.switchTo().window("vars.get("win9770").toString()");
    driver.switchTo().window("vars.get("win5035").toString()");
    driver.switchTo().window("vars.get("root").toString()");
  }
}