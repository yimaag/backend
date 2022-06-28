package com.yimaag.specification;

import com.yimaag.entity.MediaFile;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MediaFileSpecificationBuilder {
    private final List<SearchCriteria> params;

    public MediaFileSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public MediaFileSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<MediaFile> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<MediaFile>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new MediaFileSpecification(param));
        }

        Specification<MediaFile> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
