package com.crudCommerce.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudCommerce.DTO.MovimentacaoDTO;
import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Conteiner;
import com.crudCommerce.model.Movimentacao;
import com.crudCommerce.model.TipoMovimentacao;
import com.crudCommerce.service.ConteinerService;
import com.crudCommerce.service.MovimentacaoService;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Autowired
	private ConteinerService conteinerService;
	
	private Movimentacao converteDtoToEntity(MovimentacaoDTO dto) {
		Movimentacao movimentacao = new Movimentacao();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		Optional<Conteiner> conteiner = this.conteinerService.findConteinerById(dto.getIdConteiner());
	
		if(conteiner.isPresent()) {
			movimentacao.setConteiner(conteiner.get());
		}
		
		movimentacao.setTipoMovimentacao(TipoMovimentacao.valueOf(dto.getTipoMovimentacao()));
		
		if(dto.getDataHoraInicio() != null || dto.getDataHoraFim().equals("")) {
			movimentacao.setDataHoraInicio(LocalDateTime.parse(dto.getDataHoraInicio(), formatter));
		}
		
		if(dto.getDataHoraFim() != null || dto.getDataHoraFim().equals("")) {
			movimentacao.setDataHoraFim(LocalDateTime.parse(dto.getDataHoraFim(), formatter));
		}
		
		return movimentacao;
	}
	
	@GetMapping
	public ResponseEntity<List<Movimentacao>> list() {
		List<Movimentacao> movimentacoes = this.movimentacaoService.listAllMovimentacao();
		return ResponseEntity.ok(movimentacoes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findMovimentacaoById(@PathVariable("id") Integer id) {
		Optional<Movimentacao> movimentacao = this.movimentacaoService.findMovimentacaoById(id);
		
		if(movimentacao.isPresent()) {
			return new ResponseEntity<Movimentacao>(movimentacao.get(), HttpStatus.OK);
		}else 
			return ResponseEntity.badRequest().body("Ocorreu um erro ao buscar esta movimentação");
	}
	
	@PostMapping
	public ResponseEntity<?> createMovimentacao(@RequestBody MovimentacaoDTO dto) {
		try {
			Movimentacao movimentacao = converteDtoToEntity(dto);
			
			Movimentacao movimentacaoSaved = this.movimentacaoService.saveMovimentacao(movimentacao);
			return new ResponseEntity<Movimentacao>(movimentacaoSaved, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMovimentacao(
			@PathVariable("id") Integer id,
			@RequestBody MovimentacaoDTO dto) {
		return this.movimentacaoService.findMovimentacaoById(id).map(entity -> {
			try {
				Movimentacao movimentacao = converteDtoToEntity(dto 	);
				movimentacao.setId(entity.getId());
				
				Movimentacao movimentacaoSaved = this.movimentacaoService.saveMovimentacao(movimentacao);
				return new ResponseEntity<Movimentacao>(movimentacaoSaved, HttpStatus.OK);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() ->
				ResponseEntity.badRequest().body("Ocorreu um erro ao buscar a movimentação"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteMovimentacao(@PathVariable("id")Integer id) {
		this.movimentacaoService.deleteMovimentacao(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
