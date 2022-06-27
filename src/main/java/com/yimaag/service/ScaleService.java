package com.yimaag.service;

import com.yimaag.entity.Scale;
import com.yimaag.pojo.ScaleP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScaleService {
    Scale getById(Long id);

    Scale create(ScaleP scaleP);

    Page<Scale> getAllPageable(Integer page, Integer itemCount, String name, String[] sortParam);

    List<Scale> getAll(String name, String[] sortParam);

    Scale update(ScaleP scaleP);

    void delete(Long id);
}
