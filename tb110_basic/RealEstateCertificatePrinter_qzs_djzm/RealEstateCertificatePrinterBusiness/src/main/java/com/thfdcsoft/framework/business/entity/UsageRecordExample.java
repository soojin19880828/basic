package com.thfdcsoft.framework.business.entity;

import java.util.ArrayList;
import java.util.List;

public class UsageRecordExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public UsageRecordExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
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

		public Criteria andRecordIdIsNull() {
			addCriterion("RECORD_ID is null");
			return (Criteria) this;
		}

		public Criteria andRecordIdIsNotNull() {
			addCriterion("RECORD_ID is not null");
			return (Criteria) this;
		}

		public Criteria andRecordIdEqualTo(String value) {
			addCriterion("RECORD_ID =", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdNotEqualTo(String value) {
			addCriterion("RECORD_ID <>", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdGreaterThan(String value) {
			addCriterion("RECORD_ID >", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdGreaterThanOrEqualTo(String value) {
			addCriterion("RECORD_ID >=", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdLessThan(String value) {
			addCriterion("RECORD_ID <", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdLessThanOrEqualTo(String value) {
			addCriterion("RECORD_ID <=", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdLike(String value) {
			addCriterion("RECORD_ID like", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdNotLike(String value) {
			addCriterion("RECORD_ID not like", value, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdIn(List<String> values) {
			addCriterion("RECORD_ID in", values, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdNotIn(List<String> values) {
			addCriterion("RECORD_ID not in", values, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdBetween(String value1, String value2) {
			addCriterion("RECORD_ID between", value1, value2, "recordId");
			return (Criteria) this;
		}

		public Criteria andRecordIdNotBetween(String value1, String value2) {
			addCriterion("RECORD_ID not between", value1, value2, "recordId");
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

		public Criteria andUserFullnameIsNull() {
			addCriterion("USER_FULLNAME is null");
			return (Criteria) this;
		}

		public Criteria andUserFullnameIsNotNull() {
			addCriterion("USER_FULLNAME is not null");
			return (Criteria) this;
		}

		public Criteria andUserFullnameEqualTo(String value) {
			addCriterion("USER_FULLNAME =", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameNotEqualTo(String value) {
			addCriterion("USER_FULLNAME <>", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameGreaterThan(String value) {
			addCriterion("USER_FULLNAME >", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameGreaterThanOrEqualTo(String value) {
			addCriterion("USER_FULLNAME >=", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameLessThan(String value) {
			addCriterion("USER_FULLNAME <", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameLessThanOrEqualTo(String value) {
			addCriterion("USER_FULLNAME <=", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameLike(String value) {
			addCriterion("USER_FULLNAME like", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameNotLike(String value) {
			addCriterion("USER_FULLNAME not like", value, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameIn(List<String> values) {
			addCriterion("USER_FULLNAME in", values, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameNotIn(List<String> values) {
			addCriterion("USER_FULLNAME not in", values, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameBetween(String value1, String value2) {
			addCriterion("USER_FULLNAME between", value1, value2, "userFullname");
			return (Criteria) this;
		}

		public Criteria andUserFullnameNotBetween(String value1, String value2) {
			addCriterion("USER_FULLNAME not between", value1, value2, "userFullname");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberIsNull() {
			addCriterion("DEVICE_NUMBER is null");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberIsNotNull() {
			addCriterion("DEVICE_NUMBER is not null");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberEqualTo(String value) {
			addCriterion("DEVICE_NUMBER =", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberNotEqualTo(String value) {
			addCriterion("DEVICE_NUMBER <>", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberGreaterThan(String value) {
			addCriterion("DEVICE_NUMBER >", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberGreaterThanOrEqualTo(String value) {
			addCriterion("DEVICE_NUMBER >=", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberLessThan(String value) {
			addCriterion("DEVICE_NUMBER <", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberLessThanOrEqualTo(String value) {
			addCriterion("DEVICE_NUMBER <=", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberLike(String value) {
			addCriterion("DEVICE_NUMBER like", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberNotLike(String value) {
			addCriterion("DEVICE_NUMBER not like", value, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberIn(List<String> values) {
			addCriterion("DEVICE_NUMBER in", values, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberNotIn(List<String> values) {
			addCriterion("DEVICE_NUMBER not in", values, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberBetween(String value1, String value2) {
			addCriterion("DEVICE_NUMBER between", value1, value2, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andDeviceNumberNotBetween(String value1, String value2) {
			addCriterion("DEVICE_NUMBER not between", value1, value2, "deviceNumber");
			return (Criteria) this;
		}

		public Criteria andUsageTimeIsNull() {
			addCriterion("USAGE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andUsageTimeIsNotNull() {
			addCriterion("USAGE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andUsageTimeEqualTo(String value) {
			addCriterion("USAGE_TIME =", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeNotEqualTo(String value) {
			addCriterion("USAGE_TIME <>", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeGreaterThan(String value) {
			addCriterion("USAGE_TIME >", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeGreaterThanOrEqualTo(String value) {
			addCriterion("USAGE_TIME >=", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeLessThan(String value) {
			addCriterion("USAGE_TIME <", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeLessThanOrEqualTo(String value) {
			addCriterion("USAGE_TIME <=", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeLike(String value) {
			addCriterion("USAGE_TIME like", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeNotLike(String value) {
			addCriterion("USAGE_TIME not like", value, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeIn(List<String> values) {
			addCriterion("USAGE_TIME in", values, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeNotIn(List<String> values) {
			addCriterion("USAGE_TIME not in", values, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeBetween(String value1, String value2) {
			addCriterion("USAGE_TIME between", value1, value2, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUsageTimeNotBetween(String value1, String value2) {
			addCriterion("USAGE_TIME not between", value1, value2, "usageTime");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathIsNull() {
			addCriterion("USER_ID_PIC_PATH is null");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathIsNotNull() {
			addCriterion("USER_ID_PIC_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathEqualTo(String value) {
			addCriterion("USER_ID_PIC_PATH =", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathNotEqualTo(String value) {
			addCriterion("USER_ID_PIC_PATH <>", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathGreaterThan(String value) {
			addCriterion("USER_ID_PIC_PATH >", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathGreaterThanOrEqualTo(String value) {
			addCriterion("USER_ID_PIC_PATH >=", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathLessThan(String value) {
			addCriterion("USER_ID_PIC_PATH <", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathLessThanOrEqualTo(String value) {
			addCriterion("USER_ID_PIC_PATH <=", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathLike(String value) {
			addCriterion("USER_ID_PIC_PATH like", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathNotLike(String value) {
			addCriterion("USER_ID_PIC_PATH not like", value, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathIn(List<String> values) {
			addCriterion("USER_ID_PIC_PATH in", values, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathNotIn(List<String> values) {
			addCriterion("USER_ID_PIC_PATH not in", values, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathBetween(String value1, String value2) {
			addCriterion("USER_ID_PIC_PATH between", value1, value2, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserIdPicPathNotBetween(String value1, String value2) {
			addCriterion("USER_ID_PIC_PATH not between", value1, value2, "userIdPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathIsNull() {
			addCriterion("USER_DET_PIC_PATH is null");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathIsNotNull() {
			addCriterion("USER_DET_PIC_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathEqualTo(String value) {
			addCriterion("USER_DET_PIC_PATH =", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathNotEqualTo(String value) {
			addCriterion("USER_DET_PIC_PATH <>", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathGreaterThan(String value) {
			addCriterion("USER_DET_PIC_PATH >", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathGreaterThanOrEqualTo(String value) {
			addCriterion("USER_DET_PIC_PATH >=", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathLessThan(String value) {
			addCriterion("USER_DET_PIC_PATH <", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathLessThanOrEqualTo(String value) {
			addCriterion("USER_DET_PIC_PATH <=", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathLike(String value) {
			addCriterion("USER_DET_PIC_PATH like", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathNotLike(String value) {
			addCriterion("USER_DET_PIC_PATH not like", value, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathIn(List<String> values) {
			addCriterion("USER_DET_PIC_PATH in", values, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathNotIn(List<String> values) {
			addCriterion("USER_DET_PIC_PATH not in", values, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathBetween(String value1, String value2) {
			addCriterion("USER_DET_PIC_PATH between", value1, value2, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserDetPicPathNotBetween(String value1, String value2) {
			addCriterion("USER_DET_PIC_PATH not between", value1, value2, "userDetPicPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathIsNull() {
			addCriterion("USER_LOG_PATH is null");
			return (Criteria) this;
		}

		public Criteria andUserLogPathIsNotNull() {
			addCriterion("USER_LOG_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andUserLogPathEqualTo(String value) {
			addCriterion("USER_LOG_PATH =", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathNotEqualTo(String value) {
			addCriterion("USER_LOG_PATH <>", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathGreaterThan(String value) {
			addCriterion("USER_LOG_PATH >", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathGreaterThanOrEqualTo(String value) {
			addCriterion("USER_LOG_PATH >=", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathLessThan(String value) {
			addCriterion("USER_LOG_PATH <", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathLessThanOrEqualTo(String value) {
			addCriterion("USER_LOG_PATH <=", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathLike(String value) {
			addCriterion("USER_LOG_PATH like", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathNotLike(String value) {
			addCriterion("USER_LOG_PATH not like", value, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathIn(List<String> values) {
			addCriterion("USER_LOG_PATH in", values, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathNotIn(List<String> values) {
			addCriterion("USER_LOG_PATH not in", values, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathBetween(String value1, String value2) {
			addCriterion("USER_LOG_PATH between", value1, value2, "userLogPath");
			return (Criteria) this;
		}

		public Criteria andUserLogPathNotBetween(String value1, String value2) {
			addCriterion("USER_LOG_PATH not between", value1, value2, "userLogPath");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USAGE_RECORD
	 * @mbg.generated  Thu Jun 28 09:55:44 CST 2018
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
     * This class corresponds to the database table USAGE_RECORD
     *
     * @mbg.generated do_not_delete_during_merge Fri Jun 22 16:06:45 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}