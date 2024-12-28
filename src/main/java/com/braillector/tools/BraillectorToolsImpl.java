package com.braillector.tools;

import org.springframework.stereotype.Component;

@Component
public class BraillectorToolsImpl implements BraillectorTools {
    @Override
    public char convertDigitToBraille(char c) {
        switch (c) {
            case '0': return '\u281A';
            case '1': return '\u2801';
            case '2': return '\u2803';
            case '3': return '\u2809';
            case '4': return '\u2819';
            case '5': return '\u2811';
            case '6': return '\u280B';
            case '7': return '\u281B';
            case '8': return '\u2813';
            case '9': return '\u280A';
            default: return c; // Caracter no soportado
        }
    }

    @Override
    public char convertLetterToBraille(char c) {
        switch (c) {
            case 'a': return '\u2801';
            case 'b': return '\u2803';
            case 'c': return '\u2809';
            case 'd': return '\u2819';
            case 'e': return '\u2811';
            case 'f': return '\u280B';
            case 'g': return '\u281B';
            case 'h': return '\u2813';
            case 'i': return '\u280A';
            case 'j': return '\u281A';
            case 'k': return '\u2805';
            case 'l': return '\u2807';
            case 'm': return '\u280D';
            case 'n': return '\u281D';
            case 'o': return '\u2815';
            case 'p': return '\u280F';
            case 'q': return '\u281F';
            case 'r': return '\u2817';
            case 's': return '\u280E';
            case 't': return '\u281E';
            case 'u': return '\u2825';
            case 'v': return '\u2827';
            case 'w': return '\u283A';
            case 'x': return '\u282D';
            case 'y': return '\u283D';
            case 'z': return '\u2835';
            case 'á': return '\u2837';
            case 'é': return '\u282e';
            case 'í': return '\u280c';
            case 'ó': return '\u282c';
            case 'ú': return '\u2833';
            case 'ñ': return '\u283b';
            default: return c; // Caracter no soportado
        }
    }

    @Override
    public char convertPunctuationToBraille(char c) {
        switch (c) {
            case '.': return '\u2832';
            case ',': return '\u2802';
            case ';': return '\u2833';
            case ':': return '\u2812';
            case '!': return '\u2816';
            case '¡': return '\u2822';
            case '¿': return '\u282E';
            case '?': return '\u2826';
            case '(': case ')': return '\u2836';
            case '-': return '\u2824';
            case '\'': return '\u2804';
            case '\"': return '\u2810';
            case '@': return '\u2808';
            case '=': return '\u2834';
            case '$': return '\u282b';
            case '©': return '\u282f';
            case '*': return '\u2814';
            case '/': return '\u2838';
            default: return c; // Caracter no soportado
        }

    }
}
