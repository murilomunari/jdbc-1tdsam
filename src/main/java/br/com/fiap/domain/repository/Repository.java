package br.com.fiap.domain.repository;


import java.util.List;

/**
 * Interface para métodos de Repository
 * @author Francis
 * @version 1.0
 * @param <T>
 * @param <U>
 */
public interface Repository<T, U> {

    /**
     * <strong>Método para persistencia de Entidade</strong>
     *
     * @param t
     * @return
     */
    public T persist(T t);

    /**
     * Método que retorna todas as Entidades
     *
     * @return
     */
    public List<T> findAll();

    /**
     * Método que retorna um t pelo seu identificador
     *
     * @param u
     * @return
     */
    public T findById(U u);


}
