package ru.maxproject.translatebot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxproject.translatebot.service.AdminService;

import java.util.List;

@Tag(name = "Управление админ панелью")
@RestController
@RequestMapping("api/${api.version}/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Получение списка id всех пользователей")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Получение всех id"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/client-ids")
    public ResponseEntity<List<String>> getAllIds() {
        return new ResponseEntity<List<String>>(adminService.getAllClientsIds(), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка сообщений пользователя")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Получение всех сообщений"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/{chatId}")

    public ResponseEntity<Page<String>> getAllMessages(
            @PathVariable @NotBlank Long chatId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new ResponseEntity<Page<String>>((Page<String>) adminService.getMessagesById(PageRequest.of(offset, limit),chatId), HttpStatus.OK);
    }

}
