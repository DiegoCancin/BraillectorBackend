package com.braillector.service;

import com.braillector.dto.ResponseDTO;
import com.braillector.tools.BraillectorTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBraillectorServiceImpl implements IBraillectorService {

    private final BraillectorTools braillectorTools;

    @Autowired
    public IBraillectorServiceImpl(BraillectorTools braillectorTools) {
        this.braillectorTools = braillectorTools;
    }

    @Override
    public ResponseDTO textoABraille(String input) {
       final char BRAILLE_CAPITAL_PREFIX = '\u2820';
       final char BRAILLE_NUMBER_PREFIX = '\u283C';
       final char BRAILLE_SPACE = '\u2800';

        StringBuilder braille = new StringBuilder();
        boolean isNumber = false;

        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                braille.append(BRAILLE_CAPITAL_PREFIX);
                c = Character.toLowerCase(c);
            }

            if (Character.isDigit(c)) {
                if (!isNumber) {
                    braille.append(BRAILLE_NUMBER_PREFIX);
                    isNumber = true;
                }
                braille.append(this.braillectorTools.convertDigitToBraille(c));
            } else {
                if (isNumber) {
                    isNumber = false;
                }
                if (c == ' ') {
                    braille.append(BRAILLE_SPACE);
                }  else if ((c >= 'a' && c <= 'z') || c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú' || c == 'ñ') {
                    braille.append(this.braillectorTools.convertLetterToBraille(c));
                } else {
                    braille.append(this.braillectorTools.convertPunctuationToBraille(c));
                }
            }
        }
        return new ResponseDTO().setMessage(braille.toString());
    }
}
