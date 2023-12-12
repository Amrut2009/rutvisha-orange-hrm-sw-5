package com.orangehrmlive.demo.testsuite;

import com.orangehrmlive.demo.customlisteners.CustomListeners;
import com.orangehrmlive.demo.pages.DashboardPage;
import com.orangehrmlive.demo.pages.HomePage;
import com.orangehrmlive.demo.pages.LoginPage;
import com.orangehrmlive.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import resources.testdata.TestData;

import java.util.Objects;

/**
 * Created by Jay Vaghani
 */
@Listeners(CustomListeners.class)
public class LoginPageTest extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        dashboardPage = new DashboardPage();
    }

    @Test(groups = {"sanity", "smoke", "regression"})
    public void verifyUserShouldLoginSuccessFully() {


//        Enter username
        loginPage.sendDataToUserNameField("Admin");
//        Enter password
        loginPage.sendDataToPasswordField("admin123");
//        Click on Login Button
        loginPage.clickOnLoginButton();
//        Verify "WelCome" Message
        String expectedMessage = "Dashboard";
        String actualMessage = dashboardPage.getWelcomeText().substring(0, expectedMessage.length());
        Assert.assertEquals(actualMessage, expectedMessage, "DashboardPage is not displayed");
    }

    @Test(groups = {"smoke", "regression"})
       public void verifyThatTheLogoDisplayOnHomePage(){
//        Login To The application
        loginPage.loginToTheApplication("Admin", "admin123");

//        3. verifyUserShouldLogOutSuccessFully()
//        Login To The application
//        Click on User Profile logo
        dashboardPage.clickOnUserProfileLogo();
//        Mouse hover on "Logout" and click
        dashboardPage.mouseHoverAndClickOnLogOut();
//        Verify the text "Login Panel" is displayed
        String expectedLoginPanelText = "Login";
        String actualLoginPanelText = loginPage.verifyTheLoginPanelText();
        Assert.assertEquals(actualLoginPanelText, expectedLoginPanelText);
    }

    @Test(dataProvider = "credentials", dataProviderClass = TestData.class, groups = {"regression"})
    public void verifyErrorMessageWithInvalidCredentials(String uName, String password, String eMessage){
        loginPage.loginToTheApplication(uName, password);

        if(Objects.equals(uName, "")){
            String actualMessage = loginPage.getUserNameFieldBlankErrorMessage();
            Assert.assertEquals(actualMessage, eMessage);
        } else if (Objects.equals(password, "")){
            String actualMessage = loginPage.getPasswordFieldBlankErrorMessage();
            Assert.assertEquals(actualMessage, eMessage);
        }else{
            String actualMessage = loginPage.getInvalidCredentialErrorMessage();
            Assert.assertEquals(actualMessage, eMessage);
        }
    }

}
