package com.eronalves1996;

import com.eronalves1996.tokens.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelfClosingTagTest {

    private final JSX parsedInputTag = JSX.parse("""
                <input />
                """);

    @Test
    public void testSelfClosingTag()
    {
        JSXElement tokens = parsedInputTag.tokens;
        JSXToken token = tokens.topLevelTokens.get(0);

        assertNotNull(token);
        assertEquals(JSXSelfClosingElement.class, token.getClass());
    }

    @Test
    public void testParseAJSXElementNameFromSelfClosingTag() {
        JSXElement tokens = parsedInputTag.tokens;
        JSXToken token = tokens.topLevelTokens.get(0);

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
        JSXElement tokens = inputWithSingleAttribute.tokens;
        JSXToken topLevelToken = tokens.topLevelTokens.get(0);
        List<JSXToken> jsxTokens = topLevelToken.subTokens();

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

    @Test
    public void testParseElementWithManyAttributes(){
        JSX inputWithManyAttributes = JSX.parse("""
                <input type="password" id="ximenes" class="mauricio-vieira" />
                """);
        JSXElement tokens = inputWithManyAttributes.tokens;
        JSXToken topLevelToken = tokens.topLevelTokens.get(0);

        assertEquals(JSXSelfClosingElement.class, topLevelToken.getClass());

        List<JSXToken> subTokens = topLevelToken.subTokens();

        assertEquals(2, subTokens.size());

        subTokens.iterator().forEachRemaining(token -> {
            if (token instanceof JSXElementName) assertEquals(((JSXElementName) token).identifier, "input");
            else if (token instanceof JSXAttributes) {
                assertTrue(((JSXAttributes) token).has("type"));
                assertTrue(((JSXAttributes) token).has("id"));
                assertTrue(((JSXAttributes) token).has("class"));

                assertEquals("password", ((JSXAttributes) token).get("type"));
                assertEquals("ximenes", ((JSXAttributes) token).get("id"));
                assertEquals("mauricio-vieira", ((JSXAttributes) token).get("class"));
            }
        });
    }

    @Test
    public void testDifferentSelfClosedTagWithManyAttributes(){
        JSX imgTag = JSX.parse("""
                <img src="file/xscap" width="100" />
                """);
        System.out.println(imgTag);
        JSXElement tokens = imgTag.tokens;
        JSXToken topLevelToken = tokens.topLevelTokens.get(0);

        assertEquals(JSXSelfClosingElement.class, topLevelToken.getClass());

        List<JSXToken> jsxTokens = topLevelToken.subTokens();

        jsxTokens.iterator().forEachRemaining(token -> {
            if(token instanceof JSXElementName) assertEquals("img", ((JSXElementName) token).identifier);
            else if(token instanceof JSXAttributes) {
                assertTrue(((JSXAttributes) token).has("src"));
                assertTrue(((JSXAttributes) token).has("width"));
                assertEquals("file/xscap", ((JSXAttributes) token).get("src"));
                assertEquals("100", ((JSXAttributes) token).get("width"));
            }
            else {
                throw new RuntimeException("Unexpected Token");
            }
        });
    }


}
