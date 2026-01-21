package com.mydreams.service;

import com.mydreams.dto.ProductoRequest;
import com.mydreams.entity.Producto;
import com.mydreams.exception.ResourceNotFoundException;
import com.mydreams.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    public List<Producto> listarTodos() {
        return productoRepository.findByActivoTrue();
    }
    
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }
    
    @Transactional
    public Producto crear(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setImagenUrl(request.getImagenUrl());
        producto.setCategoria(request.getCategoria());
        producto.setActivo(true);
        
        return productoRepository.save(producto);
    }
    
    @Transactional
    public Producto actualizar(Long id, ProductoRequest request) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setImagenUrl(request.getImagenUrl());
        producto.setCategoria(request.getCategoria());
        
        return productoRepository.save(producto);
    }
    
    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}
