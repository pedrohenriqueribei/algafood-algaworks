package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.model.DTO.input.UsuarioAlterarSenhaDTOinput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaNaoConfereException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MGS_USUARIO_NAO_ENCONTRADO = "Usuário não pode ser removido!!";

	private static final String SENHA_INCORRETA = "A senha atual incorreta";
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		entityManager.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException("Já existe um usuário cadastrado com o e-mail: " +usuarioExistente.get().getEmail());
		}
		
		return usuarioRepositorio.save(usuario);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			usuarioRepositorio.deleteById(id);
			usuarioRepositorio.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException (id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MGS_USUARIO_NAO_ENCONTRADO+ " Código: "+ id);
		}
	}
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepositorio.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	public List<Usuario> listar(){
		return usuarioRepositorio.findAll();
	}
	
	@Transactional
	public void alterarSenha(Usuario usuario, UsuarioAlterarSenhaDTOinput dtoInput) {
		
		if(usuario.senhaCoincidemCom(dtoInput.getSenhaAtual())) {
			usuario.setSenha(dtoInput.getNovaSenha());
		} else {
			throw new SenhaNaoConfereException(SENHA_INCORRETA);
		}
	}
}
