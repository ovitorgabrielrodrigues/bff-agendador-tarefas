package com.javanauta.bffagendadortarefas.infrastructure.client;

import com.javanauta.bffagendadortarefas.business.dto.in.TarefaDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefasClient {

    @PostMapping
    TarefaDTOResponse gravarTarefas(@RequestBody TarefaDTORequest dto,
                                    @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefaDTOResponse> buscalistarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal, @RequestHeader("Authorization") String token);


    @GetMapping
    List<TarefaDTOResponse> buscarTarefaPorEmail(@RequestHeader("Authorization") String token);


    @DeleteMapping
    void deletarTarefaPorId(@RequestParam("id") String id,
                            @RequestHeader("Authorization") String token);

    @PatchMapping
    TarefaDTOResponse alterarStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                               @RequestParam("id") String id,
                                               @RequestHeader("Authorization") String token);

    @PutMapping
    TarefaDTOResponse updateTarefa(@RequestBody TarefaDTORequest dto, @RequestParam("id") String id,
                                   @RequestHeader("Authorization") String token);
}