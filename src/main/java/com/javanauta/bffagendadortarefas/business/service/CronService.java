package com.javanauta.bffagendadortarefas.business.service;

import com.javanauta.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefaDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@ToString
@Slf4j //Anotação que deixa chamar métodos de log
public class CronService {

    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora(){

        String token = login(converterParaRequestDTO());
        log.info("Iniciado busca de tarefas");

        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().minusHours(1);

        List<TarefaDTOResponse> listaTarefas = tarefaService.buscaTarefaAgendadasPorPeriodo(horaAtual, horaFutura, token);

        log.info("Tarefas encontradas" + listaTarefas);
        listaTarefas.forEach(tarefa -> {
            emailService.enviaEmail(tarefa);
            log.info("Email enviado para o suario " + tarefa.getEmailUsuario());
            tarefaService.alteraStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(),
                    token);});
        log.info("Finalizada a busca de tarefas" + listaTarefas.toString());
    }

    public String login(LoginRequestDTO dto){
        return usuarioService.loginUsuario(dto);
    }

    public LoginRequestDTO converterParaRequestDTO(){
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
