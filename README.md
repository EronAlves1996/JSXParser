# JSX Parser

Pequeno projeto de parser em JSX para transformar strings html/jsx em representação em memória.

## Idealização

Eu tenho um projeto em Java também que pretende provar que componentização é possível, unicamente através do código, sem
contar com as muletas que JSF oferece. Simplesmente usando um simples JSP e realizando a injeção de código HTML nele.

Uma dificuldade que encontrei seria integrar texto html + renderização de código de componente com uma boa UX. Inspirado
pelas ideias do Remix, principalmente layout routing, vi que seria possível e me houve a ideia de trabalhar neste parser
de JSX.

## Estado atual do projeto

Atualmente este projeto consegue fazer o parser de strings HTML normais, sem interpolação JSX.

## Planos para o futuro

* Incluir interpolação
* Abrir meios de integração para outras bibliotecas

## Como usar

```java
JSX parsed = JSX.parse("""
<div>
    <p>Seu código aqui</p>
</div>
""");
```

Resultado da representação em memória:

```java
JSX { 
    tokens = JSXElement { 
        tokens = [
                JSXOpeningElement { 
                  subtokens = [  
                          JSXElementName { 
                            identifier = 'div'
                          }
                  ]
                }, 
                JSXChildren { 
                  subTokens = [ 
                          JSXElement { 
                            tokens = [
                                    JSXOpeningElement { 
                                      subtokens = [
                                              JSXElementName { 
                                                identifier = 'p'
                                              }
                                      ]
                                    }, 
                                    JSXChildren { 
                                      subTokens = [
                                              JSXText { 
                                                value = 'Seu código aqui'
                                              }
                                      ]
                                    }, 
                                    JSXClosingElement { 
                                      subTokens = [
                                              JSXElementName {
                                                identifier = 'p'
                                              }
                                      ]
                                    }
                            ]
                          }
                  ]
                }, 
                JSXClosingElement { 
                  subTokens = [
                          JSXElementName { 
                            identifier = 'div'
                          }
                  ]
                }
        ]
    }
}
```
