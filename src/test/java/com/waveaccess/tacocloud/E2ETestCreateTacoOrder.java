package com.waveaccess.tacocloud;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2ETestCreateTacoOrder {
    @LocalServerPort
    private int PORT;
    private static final String URL = "http://localhost";
    private static WebDriver webDriver;

    @Autowired
    TestRestTemplate rest;
    @BeforeClass
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void testInit(){
        webDriver = new ChromeDriver();
    }

    @AfterClass
    public static void teardown() {
        webDriver.quit();
    }

    @Test
    public void CreatingTacoOrderWithValidFields_HappyTest(){
        webDriver.get(getHomePage());
        clickTacoImage();
        assertDesignPageElements();
        chooseIngredientsAndGiveTacoName("Вкусняшка","COTO","CARN","TMTO","SLSA","JACK");
        fillAllOrderFields("orders/current");
        submitOrder("orders/current");
        assertEquals(getHomePage(),webDriver.getCurrentUrl());
    }

    @Test
    public void CreatingTacoOrderWithValidFields_NegativeTest(){
        webDriver.get(getHomePage());
        clickTacoImage();
        assertDesignPageElements();
        chooseIngredientsAndGiveTacoName("Вкусняшка","COTO","CARN","TMTO","SLSA","JACK");
        submitEmptyOrder();
        fillAllOrderFields("orders");
        submitOrder("orders");
        assertEquals(getHomePage(),webDriver.getCurrentUrl());
    }

    private void clickTacoImage(){
        assertEquals(getHomePage(),webDriver.getCurrentUrl());
        webDriver.findElement(By.tagName("a")).click();
    }

    private String getHomePage(){
        return URL+":"+PORT+"/";
    }

    private void assertDesignPageElements() {
        assertEquals(getHomePage()+"design", webDriver.getCurrentUrl());
        List<WebElement> ingredientGroups = webDriver.findElements(By.className("ingredient-group"));
        assertEquals(5, ingredientGroups.size());

        WebElement wrapGroup = webDriver.findElement(By.cssSelector("div.ingredient-group#wraps"));
        List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
        assertEquals(2, wraps.size());
        assertIngredient(wrapGroup, 0, "FLTO", "Пшеничная лепешка");
        assertIngredient(wrapGroup, 1, "COTO", "Кукурузная лепешка");

        WebElement proteinGroup = webDriver.findElement(By.cssSelector("div.ingredient-group#proteins"));
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, proteins.size());
        assertIngredient(proteinGroup, 0, "GRBF", "Говядина");
        assertIngredient(proteinGroup, 1, "CARN", "Свинина");

        WebElement cheeseGroup = webDriver.findElement(By.cssSelector("div.ingredient-group#cheeses"));
        List<WebElement> cheeses = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, cheeses.size());
        assertIngredient(cheeseGroup, 0, "CHED", "Чеддер");
        assertIngredient(cheeseGroup, 1, "JACK", "Джек");

        WebElement veggieGroup = webDriver.findElement(By.cssSelector("div.ingredient-group#veggies"));
        List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, veggies.size());
        assertIngredient(veggieGroup, 0, "TMTO", "Помидоры");
        assertIngredient(veggieGroup, 1, "LETC", "Салат");

        WebElement sauceGroup = webDriver.findElement(By.cssSelector("div.ingredient-group#sauces"));
        List<WebElement> sauces = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, sauces.size());
        assertIngredient(sauceGroup, 0, "SLSA", "Сальса");
        assertIngredient(sauceGroup, 1, "SRCR", "Сметана");
    }

    private void assertIngredient(WebElement ingredientGroup, int index,String id,String name){
        List<WebElement> ingredients = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = ingredients.get(index);
        assertEquals(id,ingredient.findElement(By.tagName("input")).getAttribute("value"));
        assertEquals(name,ingredient.findElement(By.tagName("span")).getText());
    }

    private void chooseIngredientsAndGiveTacoName(String name, String... ingredients){
        for (String str : ingredients){
            webDriver.findElement(By.cssSelector("input[value='"+str+"']")).click();
        }
        webDriver.findElement(By.cssSelector("input#name")).sendKeys(name);
        webDriver.findElement(By.cssSelector("form")).submit();
    }

    private void fillAllOrderFields(String curPage){
        assertEquals(getHomePage()+curPage,webDriver.getCurrentUrl());
        fillField("input#name","Федя");
        fillField("input#street","Пушкина");
        fillField("input#city","Оренбург");
        fillField("input#state","Оренбургская обл.");
        fillField("input#zip","12312");
        fillField("input#ccNumber","11111111111111111");
        fillField("input#ccExpiration","12/28");
        fillField("input#ccCVV","123");
    }

    private void fillField(String fieldName, String fieldValue){
        WebElement field = webDriver.findElement(By.cssSelector(fieldName));
        field.clear();
        field.sendKeys(fieldValue);
    }

    private void submitOrder(String curUrl){
        assertEquals(getHomePage()+curUrl,webDriver.getCurrentUrl());
        webDriver.findElement(By.cssSelector("form")).submit();
    }

    private void submitEmptyOrder(){
        assertEquals(getHomePage()+"orders/current",webDriver.getCurrentUrl());
        webDriver.findElement(By.cssSelector("form")).submit();
        assertEquals(getHomePage()+"orders",webDriver.getCurrentUrl());
        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(8,validationErrors.size());
        assertTrue(validationErrors.contains("Необходимо заполнить имя"));
        assertTrue(validationErrors.contains("Необходимо заполнить улицу"));
        assertTrue(validationErrors.contains("Необходимо заполнить город"));
        assertTrue(validationErrors.contains("Необходимо заполнить область"));
        assertTrue(validationErrors.contains("Необходимо заполнить индекс"));
        assertTrue(validationErrors.contains("Необходимо заполнить номер карты"));
        assertTrue(validationErrors.contains("Обязательный формат даты MM/YY"));
        assertTrue(validationErrors.contains("Некорректный CVV"));
    }

    private List<String> getValidationErrorTexts(){
        List<WebElement> errorElements = webDriver.findElements(By.className("validationError"));
        List<String> validationErorrs = errorElements.stream().map(el->el.getText()).toList();
        return validationErorrs;
    }
}
