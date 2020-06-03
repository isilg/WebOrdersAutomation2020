package com.weborders.pages;

import com.weborders.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//every page of our application has corresponding page class. It is Page Obj Model
public abstract class AbstractBasePage {
    protected WebDriver driver = Driver.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver,20);

    @FindBy(tagName = "h1")
    protected WebElement pageLogo;

    public String getPageLogoText(){
        return pageLogo.getText();  //return page logo's text
    }

    public AbstractBasePage(){
        //this code will execute when obj created of this class
        PageFactory.initElements(driver,this);   //this line is for finding by Annotations
                                                         //we do that here in the base class cause when we create more
                                                         //page classes they will derive from this class we will not do this again
    }


    /**
     * Specify component name as a parameter, like: View all products or Orders
     * @param component
     */
    public void navigateTo(String component){
        String locator = "//a[text()='" + component + "']";   //component is parameter of our method (on the below: navigateTo(String component))
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).click();
    }



}
