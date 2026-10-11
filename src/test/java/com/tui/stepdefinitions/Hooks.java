package com.tui.stepdefinitions;

import com.tui.base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.tui.base.BaseTest;
public class Hooks {

    private final BaseTest baseTest = new BaseTest();

    @Before
    public void setUpScenario() throws Exception {
        baseTest.setUp();
    }

    @After
    public void tearDownScenario() {
        baseTest.tearDown();
    }
}