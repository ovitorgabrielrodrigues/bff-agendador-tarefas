package com.javanauta.bffagendadortarefas.controller;

import com.javanauta.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.javanauta.bffagendadortarefas.business.service.UsuarioService;
import com.javanauta.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.javanauta.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.javanauta.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")

public class
UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Salvar Usuarios", description = "criar um novo usuario")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucessso!")
    @ApiResponse(responseCode = "400", description = "usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<UsuarioDTOResponse> salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login usuario", description = "loga o usuario no sistema")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso!")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return usuarioService.loginUsuario(loginRequestDTO);
    }
    @GetMapping
    @Operation(summary = "Buscar dados de usuários por email ",
            description = "Buscar dados de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucessso!")
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<UsuarioDTOResponse> buscaUsuarioPorEmail(@RequestParam("email") String email, @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar Usuarios por Id", description = "Deleta os usuários")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso!")
    @ApiResponse(responseCode = "404", description = "usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email, @RequestHeader(name = "Authorization", required=false) String token){
        usuarioService.deletaUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados de usuario",
            description = "Atualiza os dados de usuario")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!")
    @ApiResponse(responseCode = "404", description = "usuário já cadastrado")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<UsuarioDTOResponse> atualizaDadoUsuario(@RequestBody UsuarioDTORequest dto, @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualiza Endereço de Usuários",
            description = "Atualiza endereço de usuario")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso!")
    @ApiResponse(responseCode = "404", description = "endereço não encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<EnderecoDTOResponse> atualizaEndereco(@RequestBody EnderecoDTORequest dto,
                                                                @RequestParam("id") Long id,
                                                                @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto, token));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualiza Telefone",
            description = "Atualiza o telefone de usuario")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TelefoneDTOResponse> atualizaTelefone(@RequestBody TelefoneDTORequest dto, @RequestParam("id") Long id, @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto, token));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Endereço cadastrado com sucesso",
            description = "Cadastra endereço de usuario")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<EnderecoDTOResponse> cadastraEndereco(@RequestBody EnderecoDTORequest dto, @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastra telefone de usuario",
            description = "cadastra telefone")
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TelefoneDTOResponse> cadastraTelefone(@RequestBody TelefoneDTORequest dto, @RequestHeader(name = "Authorization", required=false) String token){
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }
}
