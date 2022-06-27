package com.yimaag.specification;

import com.yimaag.entity.FoodMaterials;
import com.yimaag.entity.Scale;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScaleSpecificationBuilder {
    private final List<SearchCriteria> params;

    public ScaleSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ScaleSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Scale> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Scale>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new ScaleSpecification(param));
        }

        Specification<Scale> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
