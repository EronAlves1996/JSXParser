package com.eronalves1996;

import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SelfClosingTagTest {

    @Test
    public void testSelfClosingTag()
    {
        JSX parsedInputTag = JSX.parse("""
                <input />
                """);

        Iterator<JSXToken> tokens = parsedInputTag.tokens().iterator();
        JSXToken token = tokens.next();

        assertNotNull(token);
        assertEquals(JSXToken.JSXSelfClosingElement.class, token.getClass());
    }
}
