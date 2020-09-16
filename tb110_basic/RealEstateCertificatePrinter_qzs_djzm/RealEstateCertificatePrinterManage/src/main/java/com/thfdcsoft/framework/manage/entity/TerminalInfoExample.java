package com.thfdcsoft.framework.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class TerminalInfoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public TerminalInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andTerminalIdIsNull() {
			addCriterion("TERMINAL_ID is null");
			return (Criteria) this;
		}

		public Criteria andTerminalIdIsNotNull() {
			addCriterion("TERMINAL_ID is not null");
			return (Criteria) this;
		}

		public Criteria andTerminalIdEqualTo(String value) {
			addCriterion("TERMINAL_ID =", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdNotEqualTo(String value) {
			addCriterion("TERMINAL_ID <>", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdGreaterThan(String value) {
			addCriterion("TERMINAL_ID >", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdGreaterThanOrEqualTo(String value) {
			addCriterion("TERMINAL_ID >=", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdLessThan(String value) {
			addCriterion("TERMINAL_ID <", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdLessThanOrEqualTo(String value) {
			addCriterion("TERMINAL_ID <=", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdLike(String value) {
			addCriterion("TERMINAL_ID like", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdNotLike(String value) {
			addCriterion("TERMINAL_ID not like", value, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdIn(List<String> values) {
			addCriterion("TERMINAL_ID in", values, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdNotIn(List<String> values) {
			addCriterion("TERMINAL_ID not in", values, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdBetween(String value1, String value2) {
			addCriterion("TERMINAL_ID between", value1, value2, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalIdNotBetween(String value1, String value2) {
			addCriterion("TERMINAL_ID not between", value1, value2, "terminalId");
			return (Criteria) this;
		}

		public Criteria andTerminalModelIsNull() {
			addCriterion("TERMINAL_MODEL is null");
			return (Criteria) this;
		}

		public Criteria andTerminalModelIsNotNull() {
			addCriterion("TERMINAL_MODEL is not null");
			return (Criteria) this;
		}

		public Criteria andTerminalModelEqualTo(String value) {
			addCriterion("TERMINAL_MODEL =", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelNotEqualTo(String value) {
			addCriterion("TERMINAL_MODEL <>", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelGreaterThan(String value) {
			addCriterion("TERMINAL_MODEL >", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelGreaterThanOrEqualTo(String value) {
			addCriterion("TERMINAL_MODEL >=", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelLessThan(String value) {
			addCriterion("TERMINAL_MODEL <", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelLessThanOrEqualTo(String value) {
			addCriterion("TERMINAL_MODEL <=", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelLike(String value) {
			addCriterion("TERMINAL_MODEL like", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelNotLike(String value) {
			addCriterion("TERMINAL_MODEL not like", value, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelIn(List<String> values) {
			addCriterion("TERMINAL_MODEL in", values, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelNotIn(List<String> values) {
			addCriterion("TERMINAL_MODEL not in", values, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelBetween(String value1, String value2) {
			addCriterion("TERMINAL_MODEL between", value1, value2, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTerminalModelNotBetween(String value1, String value2) {
			addCriterion("TERMINAL_MODEL not between", value1, value2, "terminalModel");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdIsNull() {
			addCriterion("TERM_GROUP_ID is null");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdIsNotNull() {
			addCriterion("TERM_GROUP_ID is not null");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdEqualTo(String value) {
			addCriterion("TERM_GROUP_ID =", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdNotEqualTo(String value) {
			addCriterion("TERM_GROUP_ID <>", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdGreaterThan(String value) {
			addCriterion("TERM_GROUP_ID >", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdGreaterThanOrEqualTo(String value) {
			addCriterion("TERM_GROUP_ID >=", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdLessThan(String value) {
			addCriterion("TERM_GROUP_ID <", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdLessThanOrEqualTo(String value) {
			addCriterion("TERM_GROUP_ID <=", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdLike(String value) {
			addCriterion("TERM_GROUP_ID like", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdNotLike(String value) {
			addCriterion("TERM_GROUP_ID not like", value, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdIn(List<String> values) {
			addCriterion("TERM_GROUP_ID in", values, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdNotIn(List<String> values) {
			addCriterion("TERM_GROUP_ID not in", values, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdBetween(String value1, String value2) {
			addCriterion("TERM_GROUP_ID between", value1, value2, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andTermGroupIdNotBetween(String value1, String value2) {
			addCriterion("TERM_GROUP_ID not between", value1, value2, "termGroupId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdIsNull() {
			addCriterion("ADMI_AREA_ID is null");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdIsNotNull() {
			addCriterion("ADMI_AREA_ID is not null");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdEqualTo(String value) {
			addCriterion("ADMI_AREA_ID =", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdNotEqualTo(String value) {
			addCriterion("ADMI_AREA_ID <>", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdGreaterThan(String value) {
			addCriterion("ADMI_AREA_ID >", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdGreaterThanOrEqualTo(String value) {
			addCriterion("ADMI_AREA_ID >=", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdLessThan(String value) {
			addCriterion("ADMI_AREA_ID <", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdLessThanOrEqualTo(String value) {
			addCriterion("ADMI_AREA_ID <=", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdLike(String value) {
			addCriterion("ADMI_AREA_ID like", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdNotLike(String value) {
			addCriterion("ADMI_AREA_ID not like", value, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdIn(List<String> values) {
			addCriterion("ADMI_AREA_ID in", values, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdNotIn(List<String> values) {
			addCriterion("ADMI_AREA_ID not in", values, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdBetween(String value1, String value2) {
			addCriterion("ADMI_AREA_ID between", value1, value2, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andAdmiAreaIdNotBetween(String value1, String value2) {
			addCriterion("ADMI_AREA_ID not between", value1, value2, "admiAreaId");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrIsNull() {
			addCriterion("TERM_DEPLOY_ADDR is null");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrIsNotNull() {
			addCriterion("TERM_DEPLOY_ADDR is not null");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrEqualTo(String value) {
			addCriterion("TERM_DEPLOY_ADDR =", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrNotEqualTo(String value) {
			addCriterion("TERM_DEPLOY_ADDR <>", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrGreaterThan(String value) {
			addCriterion("TERM_DEPLOY_ADDR >", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrGreaterThanOrEqualTo(String value) {
			addCriterion("TERM_DEPLOY_ADDR >=", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrLessThan(String value) {
			addCriterion("TERM_DEPLOY_ADDR <", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrLessThanOrEqualTo(String value) {
			addCriterion("TERM_DEPLOY_ADDR <=", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrLike(String value) {
			addCriterion("TERM_DEPLOY_ADDR like", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrNotLike(String value) {
			addCriterion("TERM_DEPLOY_ADDR not like", value, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrIn(List<String> values) {
			addCriterion("TERM_DEPLOY_ADDR in", values, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrNotIn(List<String> values) {
			addCriterion("TERM_DEPLOY_ADDR not in", values, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrBetween(String value1, String value2) {
			addCriterion("TERM_DEPLOY_ADDR between", value1, value2, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTermDeployAddrNotBetween(String value1, String value2) {
			addCriterion("TERM_DEPLOY_ADDR not between", value1, value2, "termDeployAddr");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusIsNull() {
			addCriterion("TERMINAL_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusIsNotNull() {
			addCriterion("TERMINAL_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusEqualTo(String value) {
			addCriterion("TERMINAL_STATUS =", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusNotEqualTo(String value) {
			addCriterion("TERMINAL_STATUS <>", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusGreaterThan(String value) {
			addCriterion("TERMINAL_STATUS >", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusGreaterThanOrEqualTo(String value) {
			addCriterion("TERMINAL_STATUS >=", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusLessThan(String value) {
			addCriterion("TERMINAL_STATUS <", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusLessThanOrEqualTo(String value) {
			addCriterion("TERMINAL_STATUS <=", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusLike(String value) {
			addCriterion("TERMINAL_STATUS like", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusNotLike(String value) {
			addCriterion("TERMINAL_STATUS not like", value, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusIn(List<String> values) {
			addCriterion("TERMINAL_STATUS in", values, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusNotIn(List<String> values) {
			addCriterion("TERMINAL_STATUS not in", values, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusBetween(String value1, String value2) {
			addCriterion("TERMINAL_STATUS between", value1, value2, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andTerminalStatusNotBetween(String value1, String value2) {
			addCriterion("TERMINAL_STATUS not between", value1, value2, "terminalStatus");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperIsNull() {
			addCriterion("REMAINING_PAPER is null");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperIsNotNull() {
			addCriterion("REMAINING_PAPER is not null");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperEqualTo(String value) {
			addCriterion("REMAINING_PAPER =", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperNotEqualTo(String value) {
			addCriterion("REMAINING_PAPER <>", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperGreaterThan(String value) {
			addCriterion("REMAINING_PAPER >", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperGreaterThanOrEqualTo(String value) {
			addCriterion("REMAINING_PAPER >=", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperLessThan(String value) {
			addCriterion("REMAINING_PAPER <", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperLessThanOrEqualTo(String value) {
			addCriterion("REMAINING_PAPER <=", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperLike(String value) {
			addCriterion("REMAINING_PAPER like", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperNotLike(String value) {
			addCriterion("REMAINING_PAPER not like", value, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperIn(List<String> values) {
			addCriterion("REMAINING_PAPER in", values, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperNotIn(List<String> values) {
			addCriterion("REMAINING_PAPER not in", values, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperBetween(String value1, String value2) {
			addCriterion("REMAINING_PAPER between", value1, value2, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andRemainingPaperNotBetween(String value1, String value2) {
			addCriterion("REMAINING_PAPER not between", value1, value2, "remainingPaper");
			return (Criteria) this;
		}

		public Criteria andDeployNumberIsNull() {
			addCriterion("DEPLOY_NUMBER is null");
			return (Criteria) this;
		}

		public Criteria andDeployNumberIsNotNull() {
			addCriterion("DEPLOY_NUMBER is not null");
			return (Criteria) this;
		}

		public Criteria andDeployNumberEqualTo(String value) {
			addCriterion("DEPLOY_NUMBER =", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberNotEqualTo(String value) {
			addCriterion("DEPLOY_NUMBER <>", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberGreaterThan(String value) {
			addCriterion("DEPLOY_NUMBER >", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberGreaterThanOrEqualTo(String value) {
			addCriterion("DEPLOY_NUMBER >=", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberLessThan(String value) {
			addCriterion("DEPLOY_NUMBER <", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberLessThanOrEqualTo(String value) {
			addCriterion("DEPLOY_NUMBER <=", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberLike(String value) {
			addCriterion("DEPLOY_NUMBER like", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberNotLike(String value) {
			addCriterion("DEPLOY_NUMBER not like", value, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberIn(List<String> values) {
			addCriterion("DEPLOY_NUMBER in", values, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberNotIn(List<String> values) {
			addCriterion("DEPLOY_NUMBER not in", values, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberBetween(String value1, String value2) {
			addCriterion("DEPLOY_NUMBER between", value1, value2, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andDeployNumberNotBetween(String value1, String value2) {
			addCriterion("DEPLOY_NUMBER not between", value1, value2, "deployNumber");
			return (Criteria) this;
		}

		public Criteria andExtendFieldIsNull() {
			addCriterion("EXTEND_FIELD is null");
			return (Criteria) this;
		}

		public Criteria andExtendFieldIsNotNull() {
			addCriterion("EXTEND_FIELD is not null");
			return (Criteria) this;
		}

		public Criteria andExtendFieldEqualTo(String value) {
			addCriterion("EXTEND_FIELD =", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldNotEqualTo(String value) {
			addCriterion("EXTEND_FIELD <>", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldGreaterThan(String value) {
			addCriterion("EXTEND_FIELD >", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldGreaterThanOrEqualTo(String value) {
			addCriterion("EXTEND_FIELD >=", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldLessThan(String value) {
			addCriterion("EXTEND_FIELD <", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldLessThanOrEqualTo(String value) {
			addCriterion("EXTEND_FIELD <=", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldLike(String value) {
			addCriterion("EXTEND_FIELD like", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldNotLike(String value) {
			addCriterion("EXTEND_FIELD not like", value, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldIn(List<String> values) {
			addCriterion("EXTEND_FIELD in", values, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldNotIn(List<String> values) {
			addCriterion("EXTEND_FIELD not in", values, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldBetween(String value1, String value2) {
			addCriterion("EXTEND_FIELD between", value1, value2, "extendField");
			return (Criteria) this;
		}

		public Criteria andExtendFieldNotBetween(String value1, String value2) {
			addCriterion("EXTEND_FIELD not between", value1, value2, "extendField");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TERMINAL_INFO
	 * @mbg.generated  Wed Jun 27 19:40:07 CST 2018
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TERMINAL_INFO
     *
     * @mbg.generated do_not_delete_during_merge Fri Nov 24 09:08:16 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}