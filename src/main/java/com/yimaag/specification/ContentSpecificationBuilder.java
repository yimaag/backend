package com.yimaag.specification;

import com.yimaag.entity.Content;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ContentSpecificationBuilder {
    private final List<SearchCriteria> params;

    public ContentSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ContentSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Content> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Content>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new ContentSpecification(param));
        }

        Specification<Content> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
