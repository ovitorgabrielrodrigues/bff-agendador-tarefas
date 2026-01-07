package com.javanauta.bffagendadortarefas.business.service;


import com.javanauta.bffagendadortarefas.business.dto.in.TarefaDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.infrastructure.client.TarefasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefaService {

    private final TarefasClient tarefasClient;

    public TarefaDTOResponse gravarTarefa(String token, TarefaDTORequest dto) {
        return tarefasClient.gravarTarefas(dto, token);
    }

    public List<TarefaDTOResponse> buscaTarefaAgendadasPorPeriodo(LocalDateTime dataIncial,
                                                                  LocalDateTime dataFinal,
                                                                  String token) {
        return tarefasClient.buscalistarTarefasPorPeriodo(dataIncial, dataFinal, token);
    }

    public List<TarefaDTOResponse> buscaTarefaPorEmail(String token) {
        return tarefasClient.buscarTarefaPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token) {
        tarefasClient.deletarTarefaPorId(id, token);
    }

    public TarefaDTOResponse alteraStatus(StatusNotificacaoEnum status, String id, String token) {
       return tarefasClient.alterarStatusNotificacao(status, id, token);
    }

    public TarefaDTOResponse updateTarefas(TarefaDTORequest dto, String id, String token) {
        return tarefasClient.updateTarefa(dto, id, token);
    }
}