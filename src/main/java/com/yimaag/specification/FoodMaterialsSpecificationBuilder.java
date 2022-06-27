package com.yimaag.specification;

import com.yimaag.entity.FoodMaterials;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FoodMaterialsSpecificationBuilder {
    private final List<SearchCriteria> params;

    public FoodMaterialsSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public FoodMaterialsSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<FoodMaterials> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<FoodMaterials>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new FoodMaterialsSpecification(param));
        }

        Specification<FoodMaterials> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
