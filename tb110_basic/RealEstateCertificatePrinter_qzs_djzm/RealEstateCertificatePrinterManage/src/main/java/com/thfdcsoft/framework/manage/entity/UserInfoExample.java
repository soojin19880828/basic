package com.thfdcsoft.framework.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class UserInfoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public UserInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
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

		public Criteria andUserIdIsNull() {
			addCriterion("USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(String value) {
			addCriterion("USER_ID =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(String value) {
			addCriterion("USER_ID <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(String value) {
			addCriterion("USER_ID >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("USER_ID >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(String value) {
			addCriterion("USER_ID <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(String value) {
			addCriterion("USER_ID <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLike(String value) {
			addCriterion("USER_ID like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotLike(String value) {
			addCriterion("USER_ID not like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<String> values) {
			addCriterion("USER_ID in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<String> values) {
			addCriterion("USER_ID not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(String value1, String value2) {
			addCriterion("USER_ID between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(String value1, String value2) {
			addCriterion("USER_ID not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andRoleIdIsNull() {
			addCriterion("ROLE_ID is null");
			return (Criteria) this;
		}

		public Criteria andRoleIdIsNotNull() {
			addCriterion("ROLE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andRoleIdEqualTo(String value) {
			addCriterion("ROLE_ID =", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotEqualTo(String value) {
			addCriterion("ROLE_ID <>", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdGreaterThan(String value) {
			addCriterion("ROLE_ID >", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdGreaterThanOrEqualTo(String value) {
			addCriterion("ROLE_ID >=", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdLessThan(String value) {
			addCriterion("ROLE_ID <", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdLessThanOrEqualTo(String value) {
			addCriterion("ROLE_ID <=", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdLike(String value) {
			addCriterion("ROLE_ID like", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotLike(String value) {
			addCriterion("ROLE_ID not like", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdIn(List<String> values) {
			addCriterion("ROLE_ID in", values, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotIn(List<String> values) {
			addCriterion("ROLE_ID not in", values, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdBetween(String value1, String value2) {
			addCriterion("ROLE_ID between", value1, value2, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotBetween(String value1, String value2) {
			addCriterion("ROLE_ID not between", value1, value2, "roleId");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("USER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("USER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("USER_NAME =", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("USER_NAME <>", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("USER_NAME >", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("USER_NAME >=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("USER_NAME <", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("USER_NAME <=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("USER_NAME like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("USER_NAME not like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameIn(List<String> values) {
			addCriterion("USER_NAME in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotIn(List<String> values) {
			addCriterion("USER_NAME not in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("USER_NAME between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("USER_NAME not between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserMobileIsNull() {
			addCriterion("USER_MOBILE is null");
			return (Criteria) this;
		}

		public Criteria andUserMobileIsNotNull() {
			addCriterion("USER_MOBILE is not null");
			return (Criteria) this;
		}

		public Criteria andUserMobileEqualTo(String value) {
			addCriterion("USER_MOBILE =", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileNotEqualTo(String value) {
			addCriterion("USER_MOBILE <>", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileGreaterThan(String value) {
			addCriterion("USER_MOBILE >", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileGreaterThanOrEqualTo(String value) {
			addCriterion("USER_MOBILE >=", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileLessThan(String value) {
			addCriterion("USER_MOBILE <", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileLessThanOrEqualTo(String value) {
			addCriterion("USER_MOBILE <=", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileLike(String value) {
			addCriterion("USER_MOBILE like", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileNotLike(String value) {
			addCriterion("USER_MOBILE not like", value, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileIn(List<String> values) {
			addCriterion("USER_MOBILE in", values, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileNotIn(List<String> values) {
			addCriterion("USER_MOBILE not in", values, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileBetween(String value1, String value2) {
			addCriterion("USER_MOBILE between", value1, value2, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserMobileNotBetween(String value1, String value2) {
			addCriterion("USER_MOBILE not between", value1, value2, "userMobile");
			return (Criteria) this;
		}

		public Criteria andUserEmailIsNull() {
			addCriterion("USER_EMAIL is null");
			return (Criteria) this;
		}

		public Criteria andUserEmailIsNotNull() {
			addCriterion("USER_EMAIL is not null");
			return (Criteria) this;
		}

		public Criteria andUserEmailEqualTo(String value) {
			addCriterion("USER_EMAIL =", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailNotEqualTo(String value) {
			addCriterion("USER_EMAIL <>", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailGreaterThan(String value) {
			addCriterion("USER_EMAIL >", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailGreaterThanOrEqualTo(String value) {
			addCriterion("USER_EMAIL >=", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailLessThan(String value) {
			addCriterion("USER_EMAIL <", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailLessThanOrEqualTo(String value) {
			addCriterion("USER_EMAIL <=", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailLike(String value) {
			addCriterion("USER_EMAIL like", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailNotLike(String value) {
			addCriterion("USER_EMAIL not like", value, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailIn(List<String> values) {
			addCriterion("USER_EMAIL in", values, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailNotIn(List<String> values) {
			addCriterion("USER_EMAIL not in", values, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailBetween(String value1, String value2) {
			addCriterion("USER_EMAIL between", value1, value2, "userEmail");
			return (Criteria) this;
		}

		public Criteria andUserEmailNotBetween(String value1, String value2) {
			addCriterion("USER_EMAIL not between", value1, value2, "userEmail");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameIsNull() {
			addCriterion("LOGIN_USERNAME is null");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameIsNotNull() {
			addCriterion("LOGIN_USERNAME is not null");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameEqualTo(String value) {
			addCriterion("LOGIN_USERNAME =", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameNotEqualTo(String value) {
			addCriterion("LOGIN_USERNAME <>", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameGreaterThan(String value) {
			addCriterion("LOGIN_USERNAME >", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameGreaterThanOrEqualTo(String value) {
			addCriterion("LOGIN_USERNAME >=", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameLessThan(String value) {
			addCriterion("LOGIN_USERNAME <", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameLessThanOrEqualTo(String value) {
			addCriterion("LOGIN_USERNAME <=", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameLike(String value) {
			addCriterion("LOGIN_USERNAME like", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameNotLike(String value) {
			addCriterion("LOGIN_USERNAME not like", value, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameIn(List<String> values) {
			addCriterion("LOGIN_USERNAME in", values, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameNotIn(List<String> values) {
			addCriterion("LOGIN_USERNAME not in", values, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameBetween(String value1, String value2) {
			addCriterion("LOGIN_USERNAME between", value1, value2, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginUsernameNotBetween(String value1, String value2) {
			addCriterion("LOGIN_USERNAME not between", value1, value2, "loginUsername");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordIsNull() {
			addCriterion("LOGIN_PASSWORD is null");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordIsNotNull() {
			addCriterion("LOGIN_PASSWORD is not null");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordEqualTo(String value) {
			addCriterion("LOGIN_PASSWORD =", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordNotEqualTo(String value) {
			addCriterion("LOGIN_PASSWORD <>", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordGreaterThan(String value) {
			addCriterion("LOGIN_PASSWORD >", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("LOGIN_PASSWORD >=", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordLessThan(String value) {
			addCriterion("LOGIN_PASSWORD <", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordLessThanOrEqualTo(String value) {
			addCriterion("LOGIN_PASSWORD <=", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordLike(String value) {
			addCriterion("LOGIN_PASSWORD like", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordNotLike(String value) {
			addCriterion("LOGIN_PASSWORD not like", value, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordIn(List<String> values) {
			addCriterion("LOGIN_PASSWORD in", values, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordNotIn(List<String> values) {
			addCriterion("LOGIN_PASSWORD not in", values, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordBetween(String value1, String value2) {
			addCriterion("LOGIN_PASSWORD between", value1, value2, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andLoginPasswordNotBetween(String value1, String value2) {
			addCriterion("LOGIN_PASSWORD not between", value1, value2, "loginPassword");
			return (Criteria) this;
		}

		public Criteria andUserStatusIsNull() {
			addCriterion("USER_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andUserStatusIsNotNull() {
			addCriterion("USER_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andUserStatusEqualTo(String value) {
			addCriterion("USER_STATUS =", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusNotEqualTo(String value) {
			addCriterion("USER_STATUS <>", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusGreaterThan(String value) {
			addCriterion("USER_STATUS >", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusGreaterThanOrEqualTo(String value) {
			addCriterion("USER_STATUS >=", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusLessThan(String value) {
			addCriterion("USER_STATUS <", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusLessThanOrEqualTo(String value) {
			addCriterion("USER_STATUS <=", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusLike(String value) {
			addCriterion("USER_STATUS like", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusNotLike(String value) {
			addCriterion("USER_STATUS not like", value, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusIn(List<String> values) {
			addCriterion("USER_STATUS in", values, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusNotIn(List<String> values) {
			addCriterion("USER_STATUS not in", values, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusBetween(String value1, String value2) {
			addCriterion("USER_STATUS between", value1, value2, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserStatusNotBetween(String value1, String value2) {
			addCriterion("USER_STATUS not between", value1, value2, "userStatus");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberIsNull() {
			addCriterion("USER_IDNUMBER is null");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberIsNotNull() {
			addCriterion("USER_IDNUMBER is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberEqualTo(String value) {
			addCriterion("USER_IDNUMBER =", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberNotEqualTo(String value) {
			addCriterion("USER_IDNUMBER <>", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberGreaterThan(String value) {
			addCriterion("USER_IDNUMBER >", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberGreaterThanOrEqualTo(String value) {
			addCriterion("USER_IDNUMBER >=", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberLessThan(String value) {
			addCriterion("USER_IDNUMBER <", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberLessThanOrEqualTo(String value) {
			addCriterion("USER_IDNUMBER <=", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberLike(String value) {
			addCriterion("USER_IDNUMBER like", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberNotLike(String value) {
			addCriterion("USER_IDNUMBER not like", value, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberIn(List<String> values) {
			addCriterion("USER_IDNUMBER in", values, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberNotIn(List<String> values) {
			addCriterion("USER_IDNUMBER not in", values, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberBetween(String value1, String value2) {
			addCriterion("USER_IDNUMBER between", value1, value2, "userIdnumber");
			return (Criteria) this;
		}

		public Criteria andUserIdnumberNotBetween(String value1, String value2) {
			addCriterion("USER_IDNUMBER not between", value1, value2, "userIdnumber");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USER_INFO
	 * @mbg.generated  Wed Jun 27 16:13:20 CST 2018
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
     * This class corresponds to the database table USER_INFO
     *
     * @mbg.generated do_not_delete_during_merge Mon Nov 20 17:24:26 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}