package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MGS_USUARIO_NAO_ENCONTRADO = "Usuário não pode ser removido!!";
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
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
}
