package com.braillector.service;

import com.braillector.dto.ResponseDTO;

public interface IBraillectorService {
    ResponseDTO textoABraille(String texto);
}
