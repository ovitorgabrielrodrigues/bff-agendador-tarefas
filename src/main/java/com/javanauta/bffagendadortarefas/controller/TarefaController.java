package com.javanauta.bffagendadortarefas.controller;


import com.javanauta.bffagendadortarefas.business.dto.in.TarefaDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.business.service.TarefaService;
import com.javanauta.bffagendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)

public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    @Operation(summary = "Salva tarefas de usuario", description = "Cria uma nova terefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefaDTOResponse> gravarTarefas(@RequestBody TarefaDTORequest dto,
                                                           @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por período",
            description = "busca tarefas cadastrada por periodo.")
    @ApiResponse(responseCode = "200", description = "Terefa encontradas com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<List<TarefaDTOResponse>> buscalistarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.buscaTarefaAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca lista de tarefas por email de usuarios",
            description = "Busca de tarefas cadastradas por usuário")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<List<TarefaDTOResponse>> buscarTarefaPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        List<TarefaDTOResponse> tarefas = tarefaService.buscaTarefaPorEmail(token);
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefa por id de usuario.",
            description = "Deleta tarefas cadastradas por id")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<Void> deletarTarefaPorId(@RequestParam("id") String id,
                                                   @RequestHeader(name = "Authorization", required = false) String token) {
        tarefaService.deletaTarefaPorId(id, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefas",
            description = "altera status das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Status das tarefas alterdas com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefaDTOResponse> alterarStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                      @RequestParam("id") String id,
                                                                      @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.alteraStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefas",
            description = "Altera dados das terefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefas alteradas com sucessso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefaDTOResponse> updateTarefa(@RequestBody TarefaDTORequest dto, @RequestParam("id") String id, String token) {
        return ResponseEntity.ok(tarefaService.updateTarefas(dto, id, token));
    }
}
