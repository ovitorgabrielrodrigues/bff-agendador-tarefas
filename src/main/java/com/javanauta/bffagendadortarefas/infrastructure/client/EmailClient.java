package com.javanauta.bffagendadortarefas.infrastructure.client;

import com.javanauta.bffagendadortarefas.business.dto.in.TarefaDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    void enviarEmail(@RequestBody TarefaDTOResponse dto);
}