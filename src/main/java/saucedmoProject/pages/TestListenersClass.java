package saucedmoProject.pages;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListenersClass implements ITestListener {
    public void onStart(ITestContext context) {
        System.out.println("Test execution is started.......");
    }

    public void onTestStart(ITestResult result) {

        System.out.println("Tes started");
    }

    public void onTestSuccess(ITestResult result) {

        System.out.println("Test Case Pass");
    }

    public void onTestFailure(ITestResult result) {

        System.out.println("Test Fail");
    }

    public void onFinish(ITestResult context) {
        System.out.println("Test execution Completed ");

    }

}
