package com.waveaccess.tacocloud;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2ETestHomePage {

    private static final String URL = "http://localhost";
    @LocalServerPort
    private int PORT;
    private static WebDriver webDriver;

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
    public void testHomePage() {
        String homePage = URL + ":" + PORT;
        webDriver.get(homePage);
        String titleText = webDriver.getTitle();
        Assert.assertEquals("Taco Cloud", titleText);
        String h1Text = webDriver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals("Welcome to...",h1Text);
        String imgSrc = webDriver.findElement(By.tagName("img")).getAttribute("src");
        Assert.assertEquals(homePage+"/images/TacoCloud.png",imgSrc);
    }
}
