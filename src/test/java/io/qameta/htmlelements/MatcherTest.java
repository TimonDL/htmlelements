package io.qameta.htmlelements;

import io.qameta.htmlelements.example.element.SuggestItem;
import io.qameta.htmlelements.statement.Listener;
import org.openqa.selenium.WebDriver;
import io.qameta.htmlelements.example.TestData;
import io.qameta.htmlelements.example.element.SuggestItem;
import io.qameta.htmlelements.example.page.SearchPage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

import static io.qameta.htmlelements.matcher.DisplayedMatcher.displayed;
import static io.qameta.htmlelements.matcher.HasAttributeMatcher.hasClass;
import static io.qameta.htmlelements.matcher.HasTextMatcher.hasText;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class MatcherTest {

    private WebDriver driver = TestData.mockDriver();

    @Test
    @SuppressWarnings("unchecked")
    public void testOutput() throws Exception {
        WebPageFactory pageObjectFactory = new WebPageFactory()
                .listener(new Listener() {
                    @Override
                    public void beforeMethodCall(String description, Method method, Object[] args) {
                        System.out.println(format("%s %s [%s]", description, method.getName(), args));
                    }
                });

        SearchPage searchPage = pageObjectFactory.get(driver, SearchPage.class);
        System.out.println(searchPage.toString());

        System.out.println(searchPage.searchArrow().form("form").toString());

        searchPage.searchArrow().suggest()
                .filter(WebElement::isDisplayed)
                .should(hasSize(2));

        searchPage.searchArrow()
                .should(displayed());

        searchPage.searchArrow()
                .should(hasClass("search2 suggest2-form suggest2-form__node i-bem search2_js_inited"));

        searchPage.searchArrow()
                .should(hasClass(containsString("search2_js_inited")));

        searchPage.searchArrow()
                .waitUntil("", hasText("search-arrow"), 10);

        searchPage.searchArrow().suggest()
                .convert(SuggestItem::title)
                .forEach(System.out::println);

        searchPage.searchArrow().suggest()
                .should(everyItem(notNullValue()));

    }

}
