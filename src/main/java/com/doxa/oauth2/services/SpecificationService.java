//package com.doxa.oauth2.services;
//
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//
//public interface SpecificationService {
//	Specification<FinancialInstitution> filterGlobally(String query);
//
//	public Specification<FinancialInstitution> filterUser(String query);
//
//    public Predicate getPredicateV1(CriteriaBuilder criteriaBuilder, String s, Root root);
//}
