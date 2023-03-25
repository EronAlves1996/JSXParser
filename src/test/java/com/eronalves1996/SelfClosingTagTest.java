package com.eronalves1996;

import com.eronalves1996.processors.JSXAttributes;
import com.eronalves1996.processors.JSXElementName;
import com.eronalves1996.processors.JSXSelfClosingElement;
import com.eronalves1996.processors.JSXToken;
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
        assertEquals(JSXSelfClosingElement.class, token.getClass());
    }

    @Test
    public void testParseAJSXElementNameFromSelfClosingTag() {
        JSXTokenIterator tokens = parsedInputTag.tokens();
        JSXToken token = tokens.next();

        List<JSXToken> subTokens = token.subTokens();
        JSXToken jsxElementName = subTokens.get(0);

        assertEquals(1, subTokens.size());
        assertEquals(JSXElementName.class, jsxElementName.getClass());

        JSXElementName name = (JSXElementName) jsxElementName;
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

        JSXElementName name = (JSXElementName) jsxElementName;
        JSXAttributes attributes = (JSXAttributes) jsxAttributes;

        assertEquals("input", name.identifier);
        assertNotNull(attributes);
        assertTrue(attributes.has("type"));
        assertEquals("password", attributes.get("type"));
    }
}
