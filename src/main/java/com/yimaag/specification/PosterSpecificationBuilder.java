package com.yimaag.specification;

import com.yimaag.entity.Poster;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PosterSpecificationBuilder {
    public PosterSpecificationBuilder() {
        params = new ArrayList<>();
    }

    private final List<SearchCriteria> params;

    public PosterSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Poster> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Poster>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new PosterSpecification(param));
        }

        Specification<Poster> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
