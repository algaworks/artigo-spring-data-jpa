package com.algaworks.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.estoque.model.Produto;
import com.algaworks.estoque.repository.Produtos;

@RestController
@RequestMapping("/produtos")
public class ProdutosResource {
	
	@Autowired
	private Produtos produtos;
	
	@GetMapping("/por-nome")
	public Produto porNome(@RequestParam String nome) {
		return produtos.findByNome(nome);
	}
	
	@GetMapping("/por-nome-comecando-com")
	public List<Produto> porNomeComecandoCom(@RequestParam String nome) {
		return produtos.findByNomeStartingWithIgnoreCase(nome);
	}
	
	@GetMapping("/sem-descricao")
	public List<Produto> semDescricao() {
		return produtos.findByDescricaoIsNull();
	}
	
	@GetMapping("/{id}")
	public Produto buscar(@PathVariable Long id) {
		return produtos.findOne(id);
	}
	
	@GetMapping
	public Page<Produto> pesquisar(
			@RequestParam(defaultValue = "0") int pagina, 
			@RequestParam(defaultValue = "10") int porPagina,
			@RequestParam(defaultValue = "nome") String ordenacao,
			@RequestParam(defaultValue = "ASC") Sort.Direction direcao) {
		return produtos.findAll(new PageRequest(
				pagina, porPagina, new Sort(direcao, ordenacao)));
	}
	
	@PostMapping
	public Produto salvar(@RequestBody Produto produto) {
		return produtos.save(produto);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		produtos.delete(id);
	}
}