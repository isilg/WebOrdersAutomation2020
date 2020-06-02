package com.weborders.pages;

import com.weborders.utilities.ConfigurationReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractBasePage{

    @FindBy(id="ctl00_MainContent_username")
    private WebElement userName;

    @FindBy(id="ctl00_MainContent_password")
    private WebElement password;

    //this method will login for us
    public void login(){
        String usernameValue = ConfigurationReader.getProperty("username");  //How can we read username. We can read it from ConfigurationReader
        String passwordValue = ConfigurationReader.getProperty("password");
        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);
    }

    //method overloading
    public void login(String usernameValue, String passwordValue){
        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue);
    }


}
