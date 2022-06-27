package com.yimaag.specification;

import com.yimaag.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                if (criteria.getKey() == "userRole") {
                    return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue());
                }
                return builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if ((criteria.getOperation().equalsIgnoreCase("!:"))) {
            return builder.notEqual(
                    root.get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("in")) {
            if (criteria.getKey().equals("userRole")) {
                query.distinct(true);
                return root.join("userRole").in(criteria.getValue());
            }
            return root.get(criteria.getOperation()).in(criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("sort")) {
            sort(root, query, builder);
        }
        return null;
    }
    private void sort(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
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
            if (sortParam.equals("name")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("name")));
                } else {
                    orders.add(builder.desc(root.get("name")));
                }
            }
            if (sortParam.equals("surname")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("surname")));
                } else {
                    orders.add(builder.desc(root.get("surname")));
                }
            }
            if (sortParam.equals("username")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("username")));
                } else {
                    orders.add(builder.desc(root.get("username")));
                }
            }
            if (sortParam.equals("email")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("email")));
                } else {
                    orders.add(builder.desc(root.get("email")));
                }
            }
            if (sortParam.equals("password")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("password")));
                } else {
                    orders.add(builder.desc(root.get("password")));
                }
            }
            if (sortParam.equals("userRole")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("userRole")));
                } else {
                    orders.add(builder.desc(root.get("userRole")));
                }
            }
            if (sortParam.equals("creationTime")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("creationTime")));
                } else {
                    orders.add(builder.desc(root.get("creationTime")));
                }
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
