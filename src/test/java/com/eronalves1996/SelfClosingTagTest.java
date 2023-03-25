package com.eronalves1996;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelfClosingTagTest {

    private final JSX parsedInputTag = JSX.parse("""
                <input />
                """);

    @Test
    public void testSelfClosingTag()
    {
        Iterator<JSXToken> tokens = parsedInputTag.tokens();
        JSXToken token = tokens.next();

        assertNotNull(token);
        assertEquals(JSXToken.JSXSelfClosingElement.class, token.getClass());
    }

    @Test
    public void testParseAJSXElementNameFromSelfClosingTag() {
        JSXTokenIterator tokens = parsedInputTag.tokens();
        JSXToken token = tokens.next();

        List<JSXToken> subTokens = token.subTokens();
        JSXToken jsxElementName = subTokens.get(0);

        assertEquals(1, subTokens.size());
        assertEquals(JSXToken.JSXElementName.class, jsxElementName.getClass());

        JSXToken.JSXElementName name = (JSXToken.JSXElementName) jsxElementName;
        assertEquals("input", name.identifier);
    }

    @Test
    public void testParseElementWithSingleAttribute(){
        JSX inputWithSingleAttribute = JSX.parse("""
                <input type="password" />
                """);
        JSXTokenIterator tokens = inputWithSingleAttribute.tokens();
        JSXToken token = tokens.next();
        List<JSXToken> jsxTokens = token.subTokens();

        assertEquals(2, jsxTokens.size());

        JSXToken jsxElementName = jsxTokens.get(0);
        JSXToken jsxAttributes = jsxTokens.get(1);

        JSXToken.JSXElementName name = (JSXToken.JSXElementName) jsxElementName;
        JSXToken.JSXAttributes attributes = (JSXToken.JSXAttributes) jsxAttributes;

        assertEquals("input", name.identifier);
        assertNotNull(attributes);
        assertTrue(attributes.has("type"));
        assertEquals("password", attributes.get("type"));
    }
}
