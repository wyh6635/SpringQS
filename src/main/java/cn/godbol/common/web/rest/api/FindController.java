package cn.godbol.common.web.rest.api;

import cn.godbol.common.service.api.FindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * Created by li on 17-2-20.
 */
public interface FindController<T, ID extends Serializable, Q extends Serializable, DTO> {

    FindService<T, ID> getService();

    @GetMapping
    ResponseEntity findAll(Q param);

    @GetMapping(path = "/{id}")
    ResponseEntity findOne(@PathVariable ID id);

    T DTOToEntity(DTO dto);

    DTO entityToDTO(T entity);
}
