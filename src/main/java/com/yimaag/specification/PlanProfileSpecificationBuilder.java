package com.yimaag.specification;

import com.yimaag.entity.PlanProfile;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PlanProfileSpecificationBuilder {
    private final List<SearchCriteria> params;

    public PlanProfileSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public PlanProfileSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<PlanProfile> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<PlanProfile>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new PlanProfileSpecification(param));
        }

        Specification<PlanProfile> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
