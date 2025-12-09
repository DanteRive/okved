package com.example.okved.controller;

import com.example.okved.exception.PhoneNormalizationException;
import com.example.okved.model.ErrorResponseDto;
import com.example.okved.model.OkvedSearchRequestDto;
import com.example.okved.model.OkvedSearchResponseDto;
import com.example.okved.service.OkvedSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер, предоставляющий HTTP API для поиска ОКВЭД по номеру телефона.
 */
@RestController
@RequestMapping("/api/v1/okved")
@RequiredArgsConstructor
public class OkvedSearchController {

    private final OkvedSearchService okvedSearchService;

    /**
     * Выполняет поиск кода ОКВЭД по номеру телефона.
     *
     * @param request DTO с номером телефона
     * @return результат поиска или информация об ошибке нормализации
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody OkvedSearchRequestDto request) {
        try {
            OkvedSearchResponseDto response =
                    okvedSearchService.searchByPhoneNumber(request.getPhoneNumber());
            return ResponseEntity.ok(response);
        } catch (PhoneNormalizationException ex) {
            ErrorResponseDto error = new ErrorResponseDto(false, "INVALID_PHONE", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }
    }
}