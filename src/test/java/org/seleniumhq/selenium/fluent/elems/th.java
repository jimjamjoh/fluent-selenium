package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseFluentWebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class th extends BaseTest {

    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }

    @Test
    public void th_functionality() {

        BaseFluentWebDriver fc = fwd.th()
                .th(By.xpath("@foo = 'bar'"))
                .th(By.cssSelector("baz"))
                .ths();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: th) -> we1\n" +
                        "we1.getTagName() -> 'th'\n" +
                        "we1.findElement(By.xpath: .//th[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'th'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'th'\n" +
                        "we3.findElements(By.tagName: th) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'th'\n" +
                        "we5.getTagName() -> 'th'\n"
        ));
    }

    @Test
    public void ths_functionality() {
        BaseFluentWebDriver fc = fwd.th()
                .ths(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: th) -> we1\n" +
                        "we1.getTagName() -> 'th'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'th'\n" +
                        "we3.getTagName() -> 'th'\n"
        ));
    }

    @Test
    public void th_mismatched() {
        try {
            fwd.th(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.th(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}