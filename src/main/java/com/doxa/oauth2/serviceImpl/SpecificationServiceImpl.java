//package com.doxa.oauth2.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Expression;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import com.doxa.oauth2.DTO.financialInstitution.SearchCriteria;
//import com.doxa.oauth2.DTO.financialInstitution.SearchOperation;
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.services.SpecificationService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class SpecificationServiceImpl implements SpecificationService {
//
//	private List<Predicate> filterFinancialInstitution(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder builder, String query) {
//		 List<SearchCriteria> searchCriteriaList = extractSearchCriteria(query);
//	        List<Predicate> predicates = new ArrayList<>();
//	        for (SearchCriteria searchCriteria : searchCriteriaList) {
//	            Expression<String> path = builder.upper(root.get(searchCriteria.getKey()).as(String.class));
//	            switch (searchCriteria.getOperation()) {
//	                case SearchOperation.EQUALS:
//	                    predicates.add(builder.equal(path, searchCriteria.getValue()));
//	                    break;
//	                case SearchOperation.LIKE:
//	                    predicates.add(builder.like(path, "%" + searchCriteria.getValue() + "%"));
//	                    break;
//	                case SearchOperation.LESS_THAN:
//	                    predicates.add(builder.lessThan(path, (String) searchCriteria.getValue()));
//	                    break;
//	                case SearchOperation.GREATER_THAN:
//	                    predicates.add(builder.greaterThan(path, (String) searchCriteria.getValue()));
//	                    break;
//	                case SearchOperation.GREATER_THAN_OR_EQUAL:
//	                    predicates.add(builder.greaterThanOrEqualTo(path, (String) searchCriteria.getValue()));
//	                    break;
//	                case SearchOperation.LESS_THAN_OR_EQUAL:
//	                    predicates.add(builder.lessThanOrEqualTo(path, (String) searchCriteria.getValue()));
//	                    break;
//	                default:
//	                    log.info("Invalid builder");
//	            }
//	        }
//	        return predicates;
//	    }
//
//	@Override
//	public Specification<FinancialInstitution> filterUser(String originalQuery) {
//		return (root, criteriaQuery, builder) -> {
//			String query = originalQuery;
//			List<Predicate> predicates = new ArrayList<>();
//			criteriaQuery.orderBy(builder.desc(root.get("id")));
//			List<SearchCriteria> searchCriteriaList = extractSearchCriteria(query);
//			Optional<SearchCriteria> searchCriteriaUserOptional = searchCriteriaList.stream().filter(searchCriteria -> searchCriteria.getKey().equals("name")).findFirst();
//			Optional<SearchCriteria> searchCriteriaEmailOptional = searchCriteriaList.stream().filter(searchCriteria -> searchCriteria.getKey().equals("email")).findFirst();
//			if (searchCriteriaUserOptional.isPresent()) {
//				SearchCriteria searchCriteria = searchCriteriaUserOptional.get();
//				Join<FinancialInstitution, User> joinUser = root.join("users");
//				Expression<String> path = builder.upper(joinUser.get("name").as(String.class));
//				switch (searchCriteria.getOperation()) {
//				case SearchOperation.EQUALS:
//					predicates.add(builder.equal(path, searchCriteria.getValue()));
//					break;
//				case SearchOperation.LIKE:
//					predicates.add(builder.like(path, "%" + searchCriteria.getValue() + "%"));
//					break;
//				case SearchOperation.LESS_THAN:
//					predicates.add(builder.lessThan(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.GREATER_THAN:
//					predicates.add(builder.greaterThan(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.GREATER_THAN_OR_EQUAL:
//					predicates.add(builder.greaterThanOrEqualTo(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.LESS_THAN_OR_EQUAL:
//					predicates.add(builder.lessThanOrEqualTo(path, (String) searchCriteria.getValue()));
//					break;
//				default:
//					log.info("Invalid builder");
//				}
//				 query = removeSearchCriteria(query, "name");
//			 }
//			// predicates.addAll(filterFinancialInstitution(root, criteriaQuery, builder, query));
//			if (searchCriteriaEmailOptional.isPresent()) {
//				SearchCriteria searchCriteria = searchCriteriaUserOptional.get();
//				Join<FinancialInstitution, User> joinUser = root.join("users");
//				Expression<String> path = builder.upper(joinUser.get("email").as(String.class));
//				switch (searchCriteria.getOperation()) {
//				case SearchOperation.EQUALS:
//					predicates.add(builder.equal(path, searchCriteria.getValue()));
//					break;
//				case SearchOperation.LIKE:
//					predicates.add(builder.like(path, "%" + searchCriteria.getValue() + "%"));
//					break;
//				case SearchOperation.LESS_THAN:
//					predicates.add(builder.lessThan(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.GREATER_THAN:
//					predicates.add(builder.greaterThan(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.GREATER_THAN_OR_EQUAL:
//					predicates.add(builder.greaterThanOrEqualTo(path, (String) searchCriteria.getValue()));
//					break;
//				case SearchOperation.LESS_THAN_OR_EQUAL:
//					predicates.add(builder.lessThanOrEqualTo(path, (String) searchCriteria.getValue()));
//					break;
//				default:
//					log.info("Invalid builder");
//				}
//				 query = removeSearchCriteria(query, "email");
//			}
//			predicates.addAll(filterFinancialInstitution(root, criteriaQuery, builder, query));
//			return builder.and(predicates.toArray(new Predicate[0]));
//		};
//	}
//
//	private List<SearchCriteria> extractSearchCriteria(String query) {
//		List<SearchCriteria> predicates = new ArrayList<>();
//		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|>=|<=|=)([\\w\\s\\S]+)");
//		String[] queries = query.split(",");
//		for (String q : queries) {
//			Matcher matcher = pattern.matcher(q);
//			while (matcher.find()) {
//				predicates.add(new SearchCriteria(matcher.group(1), matcher.group(2),
//						matcher.group(3).toUpperCase(Locale.ROOT)));
//			}
//		}
//		return predicates;
//	}
//
//    private String removeSearchCriteria(String query, String removeKey) {
//        List<String> newQuery = new ArrayList<>();
//        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|>=|<=|=)([\\w\\s\\S]+)");
//        String[] queries = query.split(",");
//        for (String q : queries) {
//            Matcher matcher = pattern.matcher(q);
//            if (matcher.find()) {
//                if (matcher.group(1).equals(removeKey)) {
//                    continue;
//                }
//            }
//            newQuery.add(q);
//        }
//        return StringUtils.join(newQuery, ",");
//    }
//
//	@Override
//	public Specification<FinancialInstitution> filterGlobally(String query) {
//		return fullNameLike(query).or(fiCodeLike(query)).or(fiCodeLike(query)).or(emailLike(query))
//				.or(fiNameLike(query)).or(statusLike(query));
//	}
//
//	private Specification<FinancialInstitution> fiNameLike(String query) {
//		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//				.like(criteriaBuilder.upper(root.get("fiName")), "%" + query + "%"));
//	}
//
//	private Specification<FinancialInstitution> statusLike(String query) {
//		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//				.like(criteriaBuilder.upper(root.get("status")), "%" + query + "%"));
//	}
//
//	private Specification<FinancialInstitution> emailLike(String query) {
//		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("email")),
//				"%" + query + "%"));
//	}
//
//	private Specification<FinancialInstitution> fullNameLike(String query) {
//		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//				.like(criteriaBuilder.upper(root.get("fullName")), "%" + query + "%"));
//	}
//
//	private Specification<FinancialInstitution> fiCodeLike(String query) {
//		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//				.like(criteriaBuilder.upper(root.get("fiCode")), "%" + query + "%"));
//	}
//
//	@Override
//	public Predicate getPredicateV1(CriteriaBuilder criteriaBuilder, String s, Root root) {
//		List<Predicate> predicateList = new ArrayList<>();
//		if (Objects.nonNull(s)) {
//			List<SearchCriteria> query = extractSearchCriteria(s);
//			for (SearchCriteria qu : query) {
//				if (qu != null && qu.getKey().equals("name")) {
//					getCriteriaBuilderCondition(qu.getOperation(), qu.getValue().toString(), criteriaBuilder,
//							criteriaBuilder.upper(root.get("name")), predicateList);
//
//				}
//				if (qu != null && qu.getKey().equals("email")) {
//					getCriteriaBuilderCondition(qu.getOperation(), qu.getValue().toString(), criteriaBuilder,
//							criteriaBuilder.upper(root.get("email")), predicateList);
//
//				}
//
//				if (qu != null && qu.getKey().equals("workNumber")) {
//					getCriteriaBuilderCondition(qu.getOperation(), qu.getValue().toString(), criteriaBuilder,
//							criteriaBuilder.upper(root.get("workNumber")), predicateList);
//				}
//
//				if (qu != null && qu.getKey().equals("createdAt")) {
//					predicateList.add(criteriaBuilder.equal(
//							criteriaBuilder.function("date_trunc", java.sql.Date.class, criteriaBuilder.literal("day"),
//									root.get("createdAt")),
//							criteriaBuilder.function("to_timestamp", java.sql.Date.class,
//									criteriaBuilder.literal(qu.getValue()), criteriaBuilder.literal("dd-mm-yyyy"))));
//				}
//
//				if (qu != null && qu.getKey().equals("designation")) {
//					getCriteriaBuilderCondition(qu.getOperation(), qu.getValue().toString(), criteriaBuilder,
//							criteriaBuilder.upper(root.get("designation")), predicateList);
//				}
//
//				if (qu != null && qu.getKey().equals("isActive")) {
//					getCriteriaBuilderCondition(qu.getOperation(), qu.getValue().toString(), criteriaBuilder,
//							criteriaBuilder.upper(root.get("isActive")), predicateList);
//				}
//			}
//		}
//		return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
//	}
//
//	private List getCriteriaBuilderCondition(String filterType, String filterText, CriteriaBuilder cb,
//			Expression<String> exp, List predicateList) {
//		if (filterType.equalsIgnoreCase("notContains"))
//			predicateList.add(cb.notLike(exp, "%" + filterText.toUpperCase() + "%"));
//		else if (filterType.equalsIgnoreCase(":"))
//			predicateList.add(cb.like(exp, "%" + filterText.toUpperCase() + "%"));
//		else if (filterType.equalsIgnoreCase("="))
//			predicateList.add(cb.equal(exp, filterText.toUpperCase()));
//		else if (filterType.equalsIgnoreCase("notEqual"))
//			predicateList.add(cb.notEqual(exp, filterText.toUpperCase()));
//		else if (filterType.equalsIgnoreCase("startsWith"))
//			predicateList.add(cb.like(exp, filterText.toUpperCase() + "%"));
//		else if (filterType.equalsIgnoreCase("endsWith"))
//			predicateList.add(cb.like(exp, "%" + filterText.toUpperCase()));
//		return predicateList;
//	}
//}
