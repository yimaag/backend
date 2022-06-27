package com.yimaag.specification;

import com.yimaag.entity.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategorySpecificationBuilder {
    private final List<SearchCriteria> params;

    public CategorySpecificationBuilder() {
        params = new ArrayList<>();
    }

    public CategorySpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Category> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Category>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new CategorySpecification(param));
        }

        Specification<Category> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
