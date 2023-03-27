package com.eronalves1996;

import com.eronalves1996.tokens.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OpenAndClosingTagsTest {


    @Test
    public void testShouldProduceErrorAboutEmptyOpenAndClosingTags() {
        assertThrows(RuntimeException.class, ()-> JSX.parse("""
                <html>
                </html>
                """));
    }

    @Test
    public void testShouldParseOpeningAndClosingTagsWithInnerText(){
        JSX divElementWithInnerText = JSX.parse("""
                <div>
                    This is a test text
                </div>
                """);
        JSXElement tokens = divElementWithInnerText.tokens;
        List<JSXToken> topLevelTokens = tokens.topLevelTokens;
        topLevelTokens.stream().forEach(token -> {
            if(token instanceof JSXOpeningElement) {
                List<JSXToken> jsxTokens = token.subTokens();
                assertEquals(1, jsxTokens.size());

                JSXElementName jsxElementName = (JSXElementName) jsxTokens.get(0);
                assertEquals("div", jsxElementName.identifier);
            }

            if(token instanceof JSXChild) {
                List<JSXToken> jsxTokens = token.subTokens();

                assertEquals(1, jsxTokens.size());
                assertTrue(jsxTokens.get(0) instanceof JSXText);

                assertEquals("This is a test text", ((JSXText) jsxTokens.get(0)).value);
            }

            if(token instanceof JSXClosingElement) {
                List<JSXToken> jsxTokens = token.subTokens();
                assertEquals(1, jsxTokens.size());

                JSXElementName jsxElementName = (JSXElementName) jsxTokens.get(0);
                assertEquals("div", jsxElementName.identifier);
            }
        });
    }

    private AssertToken assertElement(JSXToken token){
        return new AssertToken(){
            @Override
            public AssertToken isType(Class clazz) {
                assertEquals(clazz, token.getClass());
                return assertElement(token);
            }

            @Override
            public AssertToken subTokenQuantity(int qtd) {
                assertEquals(qtd, token.subTokens().size());
                return assertElement(token);
            }

            @Override
            public AssertToken has(Class clazz) {
                Optional<JSXToken> filtered = token.subTokens().stream().filter(t -> t.getClass() == clazz).findFirst();
                assertTrue(filtered.isPresent());
                return assertElement(token);
            }

            @Override
            public AssertToken innerNameIs(String name) {
                Optional<JSXToken> first = token.subTokens().stream().filter(t -> t.getClass() == JSXElementName.class).findFirst();
                if (first.isEmpty()) throw new RuntimeException("Element don't have any JSXElementName");
                JSXToken jsxToken = first.get();
                String identifier = ((JSXElementName) jsxToken).identifier;
                assertEquals(name, identifier);
                return assertElement(token);
            }


        };
    }

    private interface AssertToken {
        AssertToken isType(Class clazz);
        AssertToken subTokenQuantity(int qtd);
        AssertToken has (Class clazz);
        AssertToken innerNameIs(String string);

    }
}
