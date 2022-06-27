package com.yimaag.specification;

import com.yimaag.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder {

    private final List<SearchCriteria> params;

    public UserSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public UserSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<User>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new UserSpecification(param));
        }

        Specification<User> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
