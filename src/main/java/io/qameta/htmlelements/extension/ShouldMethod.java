package io.qameta.htmlelements.extension;

import io.qameta.htmlelements.context.Context;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HandleWith(ShouldMethod.Extension.class)
public @interface ShouldMethod {

    class Extension implements MethodHandler<Object> {

        @Override
        @SuppressWarnings("unchecked")
        public Object handle(Context context, Object proxy, Method method, Object[] args) throws Throwable {
            String message = (String) args[0];
            Matcher matcher = (Matcher) args[1];
            if (!matcher.matches(proxy)) {
                StringDescription description = new StringDescription();
                description.appendText(message).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText("\n     but: ");
                matcher.describeMismatch(proxy, description);
                throw new AssertionError(description.toString());
            }
            return proxy;
        }
    }

}
