package com.yimaag.specification;

import com.yimaag.entity.MediaFile;
import com.yimaag.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MediaFileSpecification implements Specification<MediaFile> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<MediaFile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                if (criteria.getKey() == "mediaType") {
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
            if (criteria.getKey().equals("mediaType")) {
                query.distinct(true);
                return root.join("mediaType").in(criteria.getValue());
            }
            return root.get(criteria.getOperation()).in(criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("sort")) {
            sort(root, query, builder);
        }
        return null;
    }
    private void sort(Root<MediaFile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
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
            if (sortParam.equals("fileName")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("fileName")));
                } else {
                    orders.add(builder.desc(root.get("fileName")));
                }
            }
            if (sortParam.equals("mediaType")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("mediaType")));
                } else {
                    orders.add(builder.desc(root.get("mediaType")));
                }
            }
            if (sortParam.equals("contentTitle")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("title")));
                } else {
                    orders.add(builder.desc(root.get("title")));
                }
            }
            if (sortParam.equals("profiled")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("profiled")));
                } else {
                    orders.add(builder.desc(root.get("profiled")));
                }
            }
            if (sortParam.equals("transferred")) {
                if (sortType.equals("asc")) {
                    orders.add(builder.asc(root.get("transferred")));
                } else {
                    orders.add(builder.desc(root.get("transferred")));
                }
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
