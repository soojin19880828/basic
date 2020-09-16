package com.thfdcsoft.framework.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class ModuleInfoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public ModuleInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
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

		public Criteria andModuleIdIsNull() {
			addCriterion("MODULE_ID is null");
			return (Criteria) this;
		}

		public Criteria andModuleIdIsNotNull() {
			addCriterion("MODULE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andModuleIdEqualTo(String value) {
			addCriterion("MODULE_ID =", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdNotEqualTo(String value) {
			addCriterion("MODULE_ID <>", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdGreaterThan(String value) {
			addCriterion("MODULE_ID >", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdGreaterThanOrEqualTo(String value) {
			addCriterion("MODULE_ID >=", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdLessThan(String value) {
			addCriterion("MODULE_ID <", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdLessThanOrEqualTo(String value) {
			addCriterion("MODULE_ID <=", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdLike(String value) {
			addCriterion("MODULE_ID like", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdNotLike(String value) {
			addCriterion("MODULE_ID not like", value, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdIn(List<String> values) {
			addCriterion("MODULE_ID in", values, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdNotIn(List<String> values) {
			addCriterion("MODULE_ID not in", values, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdBetween(String value1, String value2) {
			addCriterion("MODULE_ID between", value1, value2, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleIdNotBetween(String value1, String value2) {
			addCriterion("MODULE_ID not between", value1, value2, "moduleId");
			return (Criteria) this;
		}

		public Criteria andModuleNameIsNull() {
			addCriterion("MODULE_NAME is null");
			return (Criteria) this;
		}

		public Criteria andModuleNameIsNotNull() {
			addCriterion("MODULE_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andModuleNameEqualTo(String value) {
			addCriterion("MODULE_NAME =", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameNotEqualTo(String value) {
			addCriterion("MODULE_NAME <>", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameGreaterThan(String value) {
			addCriterion("MODULE_NAME >", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameGreaterThanOrEqualTo(String value) {
			addCriterion("MODULE_NAME >=", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameLessThan(String value) {
			addCriterion("MODULE_NAME <", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameLessThanOrEqualTo(String value) {
			addCriterion("MODULE_NAME <=", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameLike(String value) {
			addCriterion("MODULE_NAME like", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameNotLike(String value) {
			addCriterion("MODULE_NAME not like", value, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameIn(List<String> values) {
			addCriterion("MODULE_NAME in", values, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameNotIn(List<String> values) {
			addCriterion("MODULE_NAME not in", values, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameBetween(String value1, String value2) {
			addCriterion("MODULE_NAME between", value1, value2, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleNameNotBetween(String value1, String value2) {
			addCriterion("MODULE_NAME not between", value1, value2, "moduleName");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeIsNull() {
			addCriterion("MODULE_DESCRIBE is null");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeIsNotNull() {
			addCriterion("MODULE_DESCRIBE is not null");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeEqualTo(String value) {
			addCriterion("MODULE_DESCRIBE =", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeNotEqualTo(String value) {
			addCriterion("MODULE_DESCRIBE <>", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeGreaterThan(String value) {
			addCriterion("MODULE_DESCRIBE >", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeGreaterThanOrEqualTo(String value) {
			addCriterion("MODULE_DESCRIBE >=", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeLessThan(String value) {
			addCriterion("MODULE_DESCRIBE <", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeLessThanOrEqualTo(String value) {
			addCriterion("MODULE_DESCRIBE <=", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeLike(String value) {
			addCriterion("MODULE_DESCRIBE like", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeNotLike(String value) {
			addCriterion("MODULE_DESCRIBE not like", value, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeIn(List<String> values) {
			addCriterion("MODULE_DESCRIBE in", values, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeNotIn(List<String> values) {
			addCriterion("MODULE_DESCRIBE not in", values, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeBetween(String value1, String value2) {
			addCriterion("MODULE_DESCRIBE between", value1, value2, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleDescribeNotBetween(String value1, String value2) {
			addCriterion("MODULE_DESCRIBE not between", value1, value2, "moduleDescribe");
			return (Criteria) this;
		}

		public Criteria andModuleUrlIsNull() {
			addCriterion("MODULE_URL is null");
			return (Criteria) this;
		}

		public Criteria andModuleUrlIsNotNull() {
			addCriterion("MODULE_URL is not null");
			return (Criteria) this;
		}

		public Criteria andModuleUrlEqualTo(String value) {
			addCriterion("MODULE_URL =", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlNotEqualTo(String value) {
			addCriterion("MODULE_URL <>", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlGreaterThan(String value) {
			addCriterion("MODULE_URL >", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlGreaterThanOrEqualTo(String value) {
			addCriterion("MODULE_URL >=", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlLessThan(String value) {
			addCriterion("MODULE_URL <", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlLessThanOrEqualTo(String value) {
			addCriterion("MODULE_URL <=", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlLike(String value) {
			addCriterion("MODULE_URL like", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlNotLike(String value) {
			addCriterion("MODULE_URL not like", value, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlIn(List<String> values) {
			addCriterion("MODULE_URL in", values, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlNotIn(List<String> values) {
			addCriterion("MODULE_URL not in", values, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlBetween(String value1, String value2) {
			addCriterion("MODULE_URL between", value1, value2, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleUrlNotBetween(String value1, String value2) {
			addCriterion("MODULE_URL not between", value1, value2, "moduleUrl");
			return (Criteria) this;
		}

		public Criteria andModuleLevelIsNull() {
			addCriterion("MODULE_LEVEL is null");
			return (Criteria) this;
		}

		public Criteria andModuleLevelIsNotNull() {
			addCriterion("MODULE_LEVEL is not null");
			return (Criteria) this;
		}

		public Criteria andModuleLevelEqualTo(String value) {
			addCriterion("MODULE_LEVEL =", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelNotEqualTo(String value) {
			addCriterion("MODULE_LEVEL <>", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelGreaterThan(String value) {
			addCriterion("MODULE_LEVEL >", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelGreaterThanOrEqualTo(String value) {
			addCriterion("MODULE_LEVEL >=", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelLessThan(String value) {
			addCriterion("MODULE_LEVEL <", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelLessThanOrEqualTo(String value) {
			addCriterion("MODULE_LEVEL <=", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelLike(String value) {
			addCriterion("MODULE_LEVEL like", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelNotLike(String value) {
			addCriterion("MODULE_LEVEL not like", value, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelIn(List<String> values) {
			addCriterion("MODULE_LEVEL in", values, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelNotIn(List<String> values) {
			addCriterion("MODULE_LEVEL not in", values, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelBetween(String value1, String value2) {
			addCriterion("MODULE_LEVEL between", value1, value2, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andModuleLevelNotBetween(String value1, String value2) {
			addCriterion("MODULE_LEVEL not between", value1, value2, "moduleLevel");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdIsNull() {
			addCriterion("SUPE_MODULE_ID is null");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdIsNotNull() {
			addCriterion("SUPE_MODULE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdEqualTo(String value) {
			addCriterion("SUPE_MODULE_ID =", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdNotEqualTo(String value) {
			addCriterion("SUPE_MODULE_ID <>", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdGreaterThan(String value) {
			addCriterion("SUPE_MODULE_ID >", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdGreaterThanOrEqualTo(String value) {
			addCriterion("SUPE_MODULE_ID >=", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdLessThan(String value) {
			addCriterion("SUPE_MODULE_ID <", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdLessThanOrEqualTo(String value) {
			addCriterion("SUPE_MODULE_ID <=", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdLike(String value) {
			addCriterion("SUPE_MODULE_ID like", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdNotLike(String value) {
			addCriterion("SUPE_MODULE_ID not like", value, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdIn(List<String> values) {
			addCriterion("SUPE_MODULE_ID in", values, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdNotIn(List<String> values) {
			addCriterion("SUPE_MODULE_ID not in", values, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdBetween(String value1, String value2) {
			addCriterion("SUPE_MODULE_ID between", value1, value2, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andSupeModuleIdNotBetween(String value1, String value2) {
			addCriterion("SUPE_MODULE_ID not between", value1, value2, "supeModuleId");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderIsNull() {
			addCriterion("DISPLAY_ORDER is null");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderIsNotNull() {
			addCriterion("DISPLAY_ORDER is not null");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderEqualTo(String value) {
			addCriterion("DISPLAY_ORDER =", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderNotEqualTo(String value) {
			addCriterion("DISPLAY_ORDER <>", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderGreaterThan(String value) {
			addCriterion("DISPLAY_ORDER >", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderGreaterThanOrEqualTo(String value) {
			addCriterion("DISPLAY_ORDER >=", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderLessThan(String value) {
			addCriterion("DISPLAY_ORDER <", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderLessThanOrEqualTo(String value) {
			addCriterion("DISPLAY_ORDER <=", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderLike(String value) {
			addCriterion("DISPLAY_ORDER like", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderNotLike(String value) {
			addCriterion("DISPLAY_ORDER not like", value, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderIn(List<String> values) {
			addCriterion("DISPLAY_ORDER in", values, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderNotIn(List<String> values) {
			addCriterion("DISPLAY_ORDER not in", values, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderBetween(String value1, String value2) {
			addCriterion("DISPLAY_ORDER between", value1, value2, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andDisplayOrderNotBetween(String value1, String value2) {
			addCriterion("DISPLAY_ORDER not between", value1, value2, "displayOrder");
			return (Criteria) this;
		}

		public Criteria andIconCssIsNull() {
			addCriterion("ICON_CSS is null");
			return (Criteria) this;
		}

		public Criteria andIconCssIsNotNull() {
			addCriterion("ICON_CSS is not null");
			return (Criteria) this;
		}

		public Criteria andIconCssEqualTo(String value) {
			addCriterion("ICON_CSS =", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssNotEqualTo(String value) {
			addCriterion("ICON_CSS <>", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssGreaterThan(String value) {
			addCriterion("ICON_CSS >", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssGreaterThanOrEqualTo(String value) {
			addCriterion("ICON_CSS >=", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssLessThan(String value) {
			addCriterion("ICON_CSS <", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssLessThanOrEqualTo(String value) {
			addCriterion("ICON_CSS <=", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssLike(String value) {
			addCriterion("ICON_CSS like", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssNotLike(String value) {
			addCriterion("ICON_CSS not like", value, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssIn(List<String> values) {
			addCriterion("ICON_CSS in", values, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssNotIn(List<String> values) {
			addCriterion("ICON_CSS not in", values, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssBetween(String value1, String value2) {
			addCriterion("ICON_CSS between", value1, value2, "iconCss");
			return (Criteria) this;
		}

		public Criteria andIconCssNotBetween(String value1, String value2) {
			addCriterion("ICON_CSS not between", value1, value2, "iconCss");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table MODULE_INFO
	 * @mbg.generated  Wed Nov 22 11:37:43 CST 2017
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
     * This class corresponds to the database table MODULE_INFO
     *
     * @mbg.generated do_not_delete_during_merge Mon Nov 20 17:24:26 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}