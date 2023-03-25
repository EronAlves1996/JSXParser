package com.eronalves1996;

import com.eronalves1996.processors.JSXElement;
import com.eronalves1996.processors.JSXElementName;
import com.eronalves1996.processors.JSXToken;
import com.eronalves1996.processors.JSXOpeningElement;
import com.eronalves1996.processors.JSXClosingElement;
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
