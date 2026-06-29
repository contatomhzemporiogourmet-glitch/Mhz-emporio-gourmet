package com.mhz.api.service;

import com.mhz.api.dto.ProdutoDTO;
import com.mhz.api.dto.ProdutoCreateUpdateDTO;
import com.mhz.api.entity.*;
import com.mhz.api.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                         CategoriaRepository categoriaRepository,
                         MarcaRepository marcaRepository,
                         FornecedorRepository fornecedorRepository,
                         EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public ProdutoDTO obterPorId(String id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setViewsCount(produto.getViewsCount() + 1);
        produtoRepository.save(produto);
        return convertToDTO(produto);
    }

    public ProdutoDTO obterPorSlug(String slug) {
        Produto produto = produtoRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setViewsCount(produto.getViewsCount() + 1);
        produtoRepository.save(produto);
        return convertToDTO(produto);
    }

    public Page<ProdutoDTO> obterTodos(Pageable pageable) {
        return produtoRepository.findAllDisponivel(pageable)
            .map(this::convertToDTO);
    }

    public Page<ProdutoDTO> obterPorCategoria(String categoriaId, Pageable pageable) {
        return produtoRepository.findByCategoriaId(categoriaId, pageable)
            .map(this::convertToDTO);
    }

    public Page<ProdutoDTO> obterPorMarca(String marcaId, Pageable pageable) {
        return produtoRepository.findByMarcaId(marcaId, pageable)
            .map(this::convertToDTO);
    }

    public Page<ProdutoDTO> buscar(String termo, Pageable pageable) {
        return produtoRepository.searchByNome(termo, pageable)
            .map(this::convertToDTO);
    }

    public ProdutoDTO criar(ProdutoCreateUpdateDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setDescricaoDetalhada(dto.getDescricaoDetalhada());
        produto.setSlug(dto.getNome().toLowerCase().replace(" ", "-"));
        produto.setPreco(dto.getPreco());
        produto.setPrecoCusto(dto.getPrecoCusto());
        
        BigDecimal margem = dto.getPreco().subtract(dto.getPrecoCusto())
            .divide(dto.getPreco(), 2, java.math.RoundingMode.HALF_UP)
            .multiply(new BigDecimal(100));
        produto.setMargem(margem);
        
        produto.setEstoque(dto.getEstoque());
        produto.setImagemPrincipalUrl(dto.getImagemPrincipalUrl());
        produto.setDisponivel(true);
        produto.setAtivo(true);
        produto.setSeoTitle(dto.getSeoTitle());
        produto.setSeoDescription(dto.getSeoDescription());
        produto.setSeoKeywords(dto.getSeoKeywords());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        if (dto.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
            produto.setMarca(marca);
        }

        if (dto.getFornecedorId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
            produto.setFornecedor(fornecedor);
        }

        Produto produtoSalvo = produtoRepository.save(produto);

        Estoque estoque = new Estoque();
        estoque.setProduto(produtoSalvo);
        estoque.setQuantidade(dto.getEstoque());
        estoque.setQuantidadeDisponivel(dto.getEstoque());
        estoqueRepository.save(estoque);

        return convertToDTO(produtoSalvo);
    }

    public ProdutoDTO atualizar(String id, ProdutoCreateUpdateDTO dto) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setDescricaoDetalhada(dto.getDescricaoDetalhada());
        produto.setPreco(dto.getPreco());
        produto.setPrecoCusto(dto.getPrecoCusto());
        
        BigDecimal margem = dto.getPreco().subtract(dto.getPrecoCusto())
            .divide(dto.getPreco(), 2, java.math.RoundingMode.HALF_UP)
            .multiply(new BigDecimal(100));
        produto.setMargem(margem);
        
        produto.setSeoTitle(dto.getSeoTitle());
        produto.setSeoDescription(dto.getSeoDescription());
        produto.setSeoKeywords(dto.getSeoKeywords());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        if (dto.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
            produto.setMarca(marca);
        }

        Produto produtoAtualizado = produtoRepository.save(produto);
        return convertToDTO(produtoAtualizado);
    }

    public void deletar(String id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setDescricaoDetalhada(produto.getDescricaoDetalhada());
        dto.setSlug(produto.getSlug());
        dto.setPreco(produto.getPreco());
        dto.setPrecoCusto(produto.getPrecoCusto());
        dto.setMargem(produto.getMargem());
        dto.setEstoque(produto.getEstoque());
        dto.setDisponivel(produto.getDisponivel());
        dto.setImagemPrincipalUrl(produto.getImagemPrincipalUrl());
        dto.setViewsCount(produto.getViewsCount());
        dto.setFavoritosCount(produto.getFavoritosCount());
        dto.setAvaliacaoMedia(produto.getAvaliacaoMedia());
        dto.setDataCriacao(produto.getDataCriacao());
        
        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        }
        
        if (produto.getMarca() != null) {
            dto.setMarcaId(produto.getMarca().getId());
            dto.setMarcaNome(produto.getMarca().getNome());
        }
        
        if (produto.getFornecedor() != null) {
            dto.setFornecedorId(produto.getFornecedor().getId());
        }
        
        return dto;
    }
}