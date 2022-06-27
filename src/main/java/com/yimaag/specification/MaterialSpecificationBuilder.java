package com.yimaag.specification;

import com.yimaag.entity.Material;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MaterialSpecificationBuilder {
    private final List<SearchCriteria> params;

    public MaterialSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public MaterialSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Material> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Material>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new MaterialSpecification(param));
        }

        Specification<Material> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
