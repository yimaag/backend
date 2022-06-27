package com.yimaag.specification;

import com.yimaag.entity.PlanProfile;
import com.yimaag.pojo.CategoryP;
import com.yimaag.pojo.ContentP;
import com.yimaag.pojo.FoodMaterialsP;
import com.yimaag.pojo.ScaleP;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PlanProfileSpecification implements Specification<PlanProfile> {
    private SearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<PlanProfile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
    private void sort(Root<PlanProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
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
            if (sortParam.equals("categoryName")) {
                Join<PlanProfile, CategoryP> planProfileCategoryPJoin = root.join("category");
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(planProfileCategoryPJoin.get("name")));
                } else {
                    orders.add(builder.desc(planProfileCategoryPJoin.get("name")));
                }
            }
            if (sortParam.equals("categoryVisibility")) {
                Join<PlanProfile, CategoryP> planProfileCategoryPJoin = root.join("category");
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(planProfileCategoryPJoin.get("visibility")));
                } else {
                    orders.add(builder.desc(planProfileCategoryPJoin.get("visibility")));
                }
            }
            if (sortParam.equals("contentTitle")) {
                Join<PlanProfile, ContentP> materialContentPJoin = root.join("content");
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(materialContentPJoin.get("title")));
                } else {
                    orders.add(builder.desc(materialContentPJoin.get("title")));
                }
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
