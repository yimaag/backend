package com.yimaag.specification;

import com.yimaag.entity.PosterDefinition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PosterDefinitionSpecification implements Specification<PosterDefinition> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<PosterDefinition> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if ((criteria.getOperation().equalsIgnoreCase("!:"))) {
            return builder.notEqual(
                    root.get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("sort")) {
            sort(root, query, builder);
        }
        return null;
    }
    private void sort(Root<PosterDefinition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        criteriaQuery.distinct(false);
        List<String> values = (List<String>) criteria.getValue();
        List<Order> orders = new ArrayList<>();
        for (String value : values) {
            String sortParam, sortType;

            String[] parts = value.split("-");
            sortParam = parts[0];

            try {
                sortType = parts[1];
            } catch (Exception e) {
                sortType = "desc";
            }

            if (sortParam.equals("id")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("id")));
                } else {
                    orders.add(builder.desc(root.get("id")));
                }
            }
            if (sortParam.equals("code")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("code")));
                } else {
                    orders.add(builder.desc(root.get("code")));
                }
            }
            if (sortParam.equals("name")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("name")));
                } else {
                    orders.add(builder.desc(root.get("name")));
                }
            }
            if (sortParam.equals("mandatoryPoster")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("mandatoryPoster")));
                } else {
                    orders.add(builder.desc(root.get("mandatoryPoster")));
                }
            }
            if (sortParam.equals("aspectRatio")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("aspectRatio")));
                } else {
                    orders.add(builder.desc(root.get("aspectRatio")));
                }
            }
            if (sortParam.equals("width")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("width")));
                } else {
                    orders.add(builder.desc(root.get("width")));
                }
            }
            if (sortParam.equals("height")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("height")));
                } else {
                    orders.add(builder.desc(root.get("height")));
                }
            }
            if (sortParam.equals("fileSize")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("fileSize")));
                } else {
                    orders.add(builder.desc(root.get("fileSize")));
                }
            }
            if (sortParam.equals("compressRatio")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("compressRatio")));
                } else {
                    orders.add(builder.desc(root.get("compressRatio")));
                }
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
