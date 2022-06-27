package com.yimaag.specification;

import com.yimaag.entity.Material;
import com.yimaag.entity.Poster;
import com.yimaag.entity.Scale;
import com.yimaag.pojo.ContentP;
import com.yimaag.pojo.FoodMaterialsP;
import com.yimaag.pojo.PosterDefinitionP;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PosterSpecification implements Specification<Poster> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Poster> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
    private void sort(Root<Poster> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
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
            if (sortParam.equals("posterDefinitionName")) {
                Join<Poster, PosterDefinitionP> posterPosterDefinitionPJoin = root.join("posterDefinition");
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(posterPosterDefinitionPJoin.get("name")));
                } else {
                    orders.add(builder.desc(posterPosterDefinitionPJoin.get("name")));
                }
            }
            if (sortParam.equals("contentTitle")) {
                Join<Poster, ContentP> posterContentPJoin = root.join("content");
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(posterContentPJoin.get("title")));
                } else {
                    orders.add(builder.desc(posterContentPJoin.get("title")));
                }
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
