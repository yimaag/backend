package com.yimaag.specification;

import com.yimaag.entity.PosterDefinition;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PosterDefinitionSpecificationBuilder {
    private final List<SearchCriteria> params;

    public PosterDefinitionSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public PosterDefinitionSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<PosterDefinition> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<PosterDefinition>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new PosterDefinitionSpecification(param));
        }

        Specification<PosterDefinition> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
