package com.thfdcsoft.framework.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class PrintRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrintRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andBizNumberIsNull() {
            addCriterion("BIZ_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andBizNumberIsNotNull() {
            addCriterion("BIZ_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andBizNumberEqualTo(String value) {
            addCriterion("BIZ_NUMBER =", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberNotEqualTo(String value) {
            addCriterion("BIZ_NUMBER <>", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberGreaterThan(String value) {
            addCriterion("BIZ_NUMBER >", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberGreaterThanOrEqualTo(String value) {
            addCriterion("BIZ_NUMBER >=", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberLessThan(String value) {
            addCriterion("BIZ_NUMBER <", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberLessThanOrEqualTo(String value) {
            addCriterion("BIZ_NUMBER <=", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberLike(String value) {
            addCriterion("BIZ_NUMBER like", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberNotLike(String value) {
            addCriterion("BIZ_NUMBER not like", value, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberIn(List<String> values) {
            addCriterion("BIZ_NUMBER in", values, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberNotIn(List<String> values) {
            addCriterion("BIZ_NUMBER not in", values, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberBetween(String value1, String value2) {
            addCriterion("BIZ_NUMBER between", value1, value2, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andBizNumberNotBetween(String value1, String value2) {
            addCriterion("BIZ_NUMBER not between", value1, value2, "bizNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberIsNull() {
            addCriterion("CERT_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andCertNumberIsNotNull() {
            addCriterion("CERT_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andCertNumberEqualTo(String value) {
            addCriterion("CERT_NUMBER =", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberNotEqualTo(String value) {
            addCriterion("CERT_NUMBER <>", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberGreaterThan(String value) {
            addCriterion("CERT_NUMBER >", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_NUMBER >=", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberLessThan(String value) {
            addCriterion("CERT_NUMBER <", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberLessThanOrEqualTo(String value) {
            addCriterion("CERT_NUMBER <=", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberLike(String value) {
            addCriterion("CERT_NUMBER like", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberNotLike(String value) {
            addCriterion("CERT_NUMBER not like", value, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberIn(List<String> values) {
            addCriterion("CERT_NUMBER in", values, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberNotIn(List<String> values) {
            addCriterion("CERT_NUMBER not in", values, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberBetween(String value1, String value2) {
            addCriterion("CERT_NUMBER between", value1, value2, "certNumber");
            return (Criteria) this;
        }

        public Criteria andCertNumberNotBetween(String value1, String value2) {
            addCriterion("CERT_NUMBER not between", value1, value2, "certNumber");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinIsNull() {
            addCriterion("TRAN_STATUS_WIN is null");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinIsNotNull() {
            addCriterion("TRAN_STATUS_WIN is not null");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinEqualTo(String value) {
            addCriterion("TRAN_STATUS_WIN =", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinNotEqualTo(String value) {
            addCriterion("TRAN_STATUS_WIN <>", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinGreaterThan(String value) {
            addCriterion("TRAN_STATUS_WIN >", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_STATUS_WIN >=", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinLessThan(String value) {
            addCriterion("TRAN_STATUS_WIN <", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinLessThanOrEqualTo(String value) {
            addCriterion("TRAN_STATUS_WIN <=", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinLike(String value) {
            addCriterion("TRAN_STATUS_WIN like", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinNotLike(String value) {
            addCriterion("TRAN_STATUS_WIN not like", value, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinIn(List<String> values) {
            addCriterion("TRAN_STATUS_WIN in", values, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinNotIn(List<String> values) {
            addCriterion("TRAN_STATUS_WIN not in", values, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinBetween(String value1, String value2) {
            addCriterion("TRAN_STATUS_WIN between", value1, value2, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusWinNotBetween(String value1, String value2) {
            addCriterion("TRAN_STATUS_WIN not between", value1, value2, "tranStatusWin");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizIsNull() {
            addCriterion("TRAN_STATUS_BIZ is null");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizIsNotNull() {
            addCriterion("TRAN_STATUS_BIZ is not null");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizEqualTo(String value) {
            addCriterion("TRAN_STATUS_BIZ =", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizNotEqualTo(String value) {
            addCriterion("TRAN_STATUS_BIZ <>", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizGreaterThan(String value) {
            addCriterion("TRAN_STATUS_BIZ >", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_STATUS_BIZ >=", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizLessThan(String value) {
            addCriterion("TRAN_STATUS_BIZ <", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizLessThanOrEqualTo(String value) {
            addCriterion("TRAN_STATUS_BIZ <=", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizLike(String value) {
            addCriterion("TRAN_STATUS_BIZ like", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizNotLike(String value) {
            addCriterion("TRAN_STATUS_BIZ not like", value, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizIn(List<String> values) {
            addCriterion("TRAN_STATUS_BIZ in", values, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizNotIn(List<String> values) {
            addCriterion("TRAN_STATUS_BIZ not in", values, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizBetween(String value1, String value2) {
            addCriterion("TRAN_STATUS_BIZ between", value1, value2, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andTranStatusBizNotBetween(String value1, String value2) {
            addCriterion("TRAN_STATUS_BIZ not between", value1, value2, "tranStatusBiz");
            return (Criteria) this;
        }

        public Criteria andCertScanPathIsNull() {
            addCriterion("CERT_SCAN_PATH is null");
            return (Criteria) this;
        }

        public Criteria andCertScanPathIsNotNull() {
            addCriterion("CERT_SCAN_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andCertScanPathEqualTo(String value) {
            addCriterion("CERT_SCAN_PATH =", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathNotEqualTo(String value) {
            addCriterion("CERT_SCAN_PATH <>", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathGreaterThan(String value) {
            addCriterion("CERT_SCAN_PATH >", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_SCAN_PATH >=", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathLessThan(String value) {
            addCriterion("CERT_SCAN_PATH <", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathLessThanOrEqualTo(String value) {
            addCriterion("CERT_SCAN_PATH <=", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathLike(String value) {
            addCriterion("CERT_SCAN_PATH like", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathNotLike(String value) {
            addCriterion("CERT_SCAN_PATH not like", value, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathIn(List<String> values) {
            addCriterion("CERT_SCAN_PATH in", values, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathNotIn(List<String> values) {
            addCriterion("CERT_SCAN_PATH not in", values, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathBetween(String value1, String value2) {
            addCriterion("CERT_SCAN_PATH between", value1, value2, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andCertScanPathNotBetween(String value1, String value2) {
            addCriterion("CERT_SCAN_PATH not between", value1, value2, "certScanPath");
            return (Criteria) this;
        }

        public Criteria andUsageIdIsNull() {
            addCriterion("USAGE_ID is null");
            return (Criteria) this;
        }

        public Criteria andUsageIdIsNotNull() {
            addCriterion("USAGE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUsageIdEqualTo(String value) {
            addCriterion("USAGE_ID =", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdNotEqualTo(String value) {
            addCriterion("USAGE_ID <>", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdGreaterThan(String value) {
            addCriterion("USAGE_ID >", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdGreaterThanOrEqualTo(String value) {
            addCriterion("USAGE_ID >=", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdLessThan(String value) {
            addCriterion("USAGE_ID <", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdLessThanOrEqualTo(String value) {
            addCriterion("USAGE_ID <=", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdLike(String value) {
            addCriterion("USAGE_ID like", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdNotLike(String value) {
            addCriterion("USAGE_ID not like", value, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdIn(List<String> values) {
            addCriterion("USAGE_ID in", values, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdNotIn(List<String> values) {
            addCriterion("USAGE_ID not in", values, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdBetween(String value1, String value2) {
            addCriterion("USAGE_ID between", value1, value2, "usageId");
            return (Criteria) this;
        }

        public Criteria andUsageIdNotBetween(String value1, String value2) {
            addCriterion("USAGE_ID not between", value1, value2, "usageId");
            return (Criteria) this;
        }

        public Criteria andPrintTimeIsNull() {
            addCriterion("PRINT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPrintTimeIsNotNull() {
            addCriterion("PRINT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPrintTimeEqualTo(String value) {
            addCriterion("PRINT_TIME =", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeNotEqualTo(String value) {
            addCriterion("PRINT_TIME <>", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeGreaterThan(String value) {
            addCriterion("PRINT_TIME >", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PRINT_TIME >=", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeLessThan(String value) {
            addCriterion("PRINT_TIME <", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeLessThanOrEqualTo(String value) {
            addCriterion("PRINT_TIME <=", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeLike(String value) {
            addCriterion("PRINT_TIME like", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeNotLike(String value) {
            addCriterion("PRINT_TIME not like", value, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeIn(List<String> values) {
            addCriterion("PRINT_TIME in", values, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeNotIn(List<String> values) {
            addCriterion("PRINT_TIME not in", values, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeBetween(String value1, String value2) {
            addCriterion("PRINT_TIME between", value1, value2, "printTime");
            return (Criteria) this;
        }

        public Criteria andPrintTimeNotBetween(String value1, String value2) {
            addCriterion("PRINT_TIME not between", value1, value2, "printTime");
            return (Criteria) this;
        }

        public Criteria andSeqNumberIsNull() {
            addCriterion("SEQ_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andSeqNumberIsNotNull() {
            addCriterion("SEQ_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNumberEqualTo(String value) {
            addCriterion("SEQ_NUMBER =", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberNotEqualTo(String value) {
            addCriterion("SEQ_NUMBER <>", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberGreaterThan(String value) {
            addCriterion("SEQ_NUMBER >", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberGreaterThanOrEqualTo(String value) {
            addCriterion("SEQ_NUMBER >=", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberLessThan(String value) {
            addCriterion("SEQ_NUMBER <", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberLessThanOrEqualTo(String value) {
            addCriterion("SEQ_NUMBER <=", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberLike(String value) {
            addCriterion("SEQ_NUMBER like", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberNotLike(String value) {
            addCriterion("SEQ_NUMBER not like", value, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberIn(List<String> values) {
            addCriterion("SEQ_NUMBER in", values, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberNotIn(List<String> values) {
            addCriterion("SEQ_NUMBER not in", values, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberBetween(String value1, String value2) {
            addCriterion("SEQ_NUMBER between", value1, value2, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andSeqNumberNotBetween(String value1, String value2) {
            addCriterion("SEQ_NUMBER not between", value1, value2, "seqNumber");
            return (Criteria) this;
        }

        public Criteria andTimeBookIsNull() {
            addCriterion("TIME_BOOK is null");
            return (Criteria) this;
        }

        public Criteria andTimeBookIsNotNull() {
            addCriterion("TIME_BOOK is not null");
            return (Criteria) this;
        }

        public Criteria andTimeBookEqualTo(String value) {
            addCriterion("TIME_BOOK =", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookNotEqualTo(String value) {
            addCriterion("TIME_BOOK <>", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookGreaterThan(String value) {
            addCriterion("TIME_BOOK >", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookGreaterThanOrEqualTo(String value) {
            addCriterion("TIME_BOOK >=", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookLessThan(String value) {
            addCriterion("TIME_BOOK <", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookLessThanOrEqualTo(String value) {
            addCriterion("TIME_BOOK <=", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookLike(String value) {
            addCriterion("TIME_BOOK like", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookNotLike(String value) {
            addCriterion("TIME_BOOK not like", value, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookIn(List<String> values) {
            addCriterion("TIME_BOOK in", values, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookNotIn(List<String> values) {
            addCriterion("TIME_BOOK not in", values, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookBetween(String value1, String value2) {
            addCriterion("TIME_BOOK between", value1, value2, "timeBook");
            return (Criteria) this;
        }

        public Criteria andTimeBookNotBetween(String value1, String value2) {
            addCriterion("TIME_BOOK not between", value1, value2, "timeBook");
            return (Criteria) this;
        }

        public Criteria andObligeeIsNull() {
            addCriterion("OBLIGEE is null");
            return (Criteria) this;
        }

        public Criteria andObligeeIsNotNull() {
            addCriterion("OBLIGEE is not null");
            return (Criteria) this;
        }

        public Criteria andObligeeEqualTo(String value) {
            addCriterion("OBLIGEE =", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeNotEqualTo(String value) {
            addCriterion("OBLIGEE <>", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeGreaterThan(String value) {
            addCriterion("OBLIGEE >", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeGreaterThanOrEqualTo(String value) {
            addCriterion("OBLIGEE >=", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeLessThan(String value) {
            addCriterion("OBLIGEE <", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeLessThanOrEqualTo(String value) {
            addCriterion("OBLIGEE <=", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeLike(String value) {
            addCriterion("OBLIGEE like", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeNotLike(String value) {
            addCriterion("OBLIGEE not like", value, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeIn(List<String> values) {
            addCriterion("OBLIGEE in", values, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeNotIn(List<String> values) {
            addCriterion("OBLIGEE not in", values, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeBetween(String value1, String value2) {
            addCriterion("OBLIGEE between", value1, value2, "obligee");
            return (Criteria) this;
        }

        public Criteria andObligeeNotBetween(String value1, String value2) {
            addCriterion("OBLIGEE not between", value1, value2, "obligee");
            return (Criteria) this;
        }

        public Criteria andOwnershipIsNull() {
            addCriterion("OWNERSHIP is null");
            return (Criteria) this;
        }

        public Criteria andOwnershipIsNotNull() {
            addCriterion("OWNERSHIP is not null");
            return (Criteria) this;
        }

        public Criteria andOwnershipEqualTo(String value) {
            addCriterion("OWNERSHIP =", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipNotEqualTo(String value) {
            addCriterion("OWNERSHIP <>", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipGreaterThan(String value) {
            addCriterion("OWNERSHIP >", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipGreaterThanOrEqualTo(String value) {
            addCriterion("OWNERSHIP >=", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipLessThan(String value) {
            addCriterion("OWNERSHIP <", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipLessThanOrEqualTo(String value) {
            addCriterion("OWNERSHIP <=", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipLike(String value) {
            addCriterion("OWNERSHIP like", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipNotLike(String value) {
            addCriterion("OWNERSHIP not like", value, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipIn(List<String> values) {
            addCriterion("OWNERSHIP in", values, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipNotIn(List<String> values) {
            addCriterion("OWNERSHIP not in", values, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipBetween(String value1, String value2) {
            addCriterion("OWNERSHIP between", value1, value2, "ownership");
            return (Criteria) this;
        }

        public Criteria andOwnershipNotBetween(String value1, String value2) {
            addCriterion("OWNERSHIP not between", value1, value2, "ownership");
            return (Criteria) this;
        }

        public Criteria andLocatedIsNull() {
            addCriterion("LOCATED is null");
            return (Criteria) this;
        }

        public Criteria andLocatedIsNotNull() {
            addCriterion("LOCATED is not null");
            return (Criteria) this;
        }

        public Criteria andLocatedEqualTo(String value) {
            addCriterion("LOCATED =", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedNotEqualTo(String value) {
            addCriterion("LOCATED <>", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedGreaterThan(String value) {
            addCriterion("LOCATED >", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedGreaterThanOrEqualTo(String value) {
            addCriterion("LOCATED >=", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedLessThan(String value) {
            addCriterion("LOCATED <", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedLessThanOrEqualTo(String value) {
            addCriterion("LOCATED <=", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedLike(String value) {
            addCriterion("LOCATED like", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedNotLike(String value) {
            addCriterion("LOCATED not like", value, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedIn(List<String> values) {
            addCriterion("LOCATED in", values, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedNotIn(List<String> values) {
            addCriterion("LOCATED not in", values, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedBetween(String value1, String value2) {
            addCriterion("LOCATED between", value1, value2, "located");
            return (Criteria) this;
        }

        public Criteria andLocatedNotBetween(String value1, String value2) {
            addCriterion("LOCATED not between", value1, value2, "located");
            return (Criteria) this;
        }

        public Criteria andUnitNumberIsNull() {
            addCriterion("UNIT_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andUnitNumberIsNotNull() {
            addCriterion("UNIT_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNumberEqualTo(String value) {
            addCriterion("UNIT_NUMBER =", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberNotEqualTo(String value) {
            addCriterion("UNIT_NUMBER <>", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberGreaterThan(String value) {
            addCriterion("UNIT_NUMBER >", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_NUMBER >=", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberLessThan(String value) {
            addCriterion("UNIT_NUMBER <", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberLessThanOrEqualTo(String value) {
            addCriterion("UNIT_NUMBER <=", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberLike(String value) {
            addCriterion("UNIT_NUMBER like", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberNotLike(String value) {
            addCriterion("UNIT_NUMBER not like", value, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberIn(List<String> values) {
            addCriterion("UNIT_NUMBER in", values, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberNotIn(List<String> values) {
            addCriterion("UNIT_NUMBER not in", values, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberBetween(String value1, String value2) {
            addCriterion("UNIT_NUMBER between", value1, value2, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andUnitNumberNotBetween(String value1, String value2) {
            addCriterion("UNIT_NUMBER not between", value1, value2, "unitNumber");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIsNull() {
            addCriterion("BUSI_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIsNotNull() {
            addCriterion("BUSI_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusiTypeEqualTo(String value) {
            addCriterion("BUSI_TYPE =", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotEqualTo(String value) {
            addCriterion("BUSI_TYPE <>", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeGreaterThan(String value) {
            addCriterion("BUSI_TYPE >", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_TYPE >=", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLessThan(String value) {
            addCriterion("BUSI_TYPE <", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLessThanOrEqualTo(String value) {
            addCriterion("BUSI_TYPE <=", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLike(String value) {
            addCriterion("BUSI_TYPE like", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotLike(String value) {
            addCriterion("BUSI_TYPE not like", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIn(List<String> values) {
            addCriterion("BUSI_TYPE in", values, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotIn(List<String> values) {
            addCriterion("BUSI_TYPE not in", values, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeBetween(String value1, String value2) {
            addCriterion("BUSI_TYPE between", value1, value2, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotBetween(String value1, String value2) {
            addCriterion("BUSI_TYPE not between", value1, value2, "busiType");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightIsNull() {
            addCriterion("NATURE_OF_RIGHT is null");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightIsNotNull() {
            addCriterion("NATURE_OF_RIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightEqualTo(String value) {
            addCriterion("NATURE_OF_RIGHT =", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightNotEqualTo(String value) {
            addCriterion("NATURE_OF_RIGHT <>", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightGreaterThan(String value) {
            addCriterion("NATURE_OF_RIGHT >", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightGreaterThanOrEqualTo(String value) {
            addCriterion("NATURE_OF_RIGHT >=", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightLessThan(String value) {
            addCriterion("NATURE_OF_RIGHT <", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightLessThanOrEqualTo(String value) {
            addCriterion("NATURE_OF_RIGHT <=", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightLike(String value) {
            addCriterion("NATURE_OF_RIGHT like", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightNotLike(String value) {
            addCriterion("NATURE_OF_RIGHT not like", value, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightIn(List<String> values) {
            addCriterion("NATURE_OF_RIGHT in", values, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightNotIn(List<String> values) {
            addCriterion("NATURE_OF_RIGHT not in", values, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightBetween(String value1, String value2) {
            addCriterion("NATURE_OF_RIGHT between", value1, value2, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andNatureOfRightNotBetween(String value1, String value2) {
            addCriterion("NATURE_OF_RIGHT not between", value1, value2, "natureOfRight");
            return (Criteria) this;
        }

        public Criteria andUseageIsNull() {
            addCriterion("USEAGE is null");
            return (Criteria) this;
        }

        public Criteria andUseageIsNotNull() {
            addCriterion("USEAGE is not null");
            return (Criteria) this;
        }

        public Criteria andUseageEqualTo(String value) {
            addCriterion("USEAGE =", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotEqualTo(String value) {
            addCriterion("USEAGE <>", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThan(String value) {
            addCriterion("USEAGE >", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThanOrEqualTo(String value) {
            addCriterion("USEAGE >=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThan(String value) {
            addCriterion("USEAGE <", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThanOrEqualTo(String value) {
            addCriterion("USEAGE <=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLike(String value) {
            addCriterion("USEAGE like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotLike(String value) {
            addCriterion("USEAGE not like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageIn(List<String> values) {
            addCriterion("USEAGE in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotIn(List<String> values) {
            addCriterion("USEAGE not in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageBetween(String value1, String value2) {
            addCriterion("USEAGE between", value1, value2, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotBetween(String value1, String value2) {
            addCriterion("USEAGE not between", value1, value2, "useage");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaIsNull() {
            addCriterion("COVERED_AREA is null");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaIsNotNull() {
            addCriterion("COVERED_AREA is not null");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaEqualTo(String value) {
            addCriterion("COVERED_AREA =", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaNotEqualTo(String value) {
            addCriterion("COVERED_AREA <>", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaGreaterThan(String value) {
            addCriterion("COVERED_AREA >", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaGreaterThanOrEqualTo(String value) {
            addCriterion("COVERED_AREA >=", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaLessThan(String value) {
            addCriterion("COVERED_AREA <", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaLessThanOrEqualTo(String value) {
            addCriterion("COVERED_AREA <=", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaLike(String value) {
            addCriterion("COVERED_AREA like", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaNotLike(String value) {
            addCriterion("COVERED_AREA not like", value, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaIn(List<String> values) {
            addCriterion("COVERED_AREA in", values, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaNotIn(List<String> values) {
            addCriterion("COVERED_AREA not in", values, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaBetween(String value1, String value2) {
            addCriterion("COVERED_AREA between", value1, value2, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andCoveredAreaNotBetween(String value1, String value2) {
            addCriterion("COVERED_AREA not between", value1, value2, "coveredArea");
            return (Criteria) this;
        }

        public Criteria andServiceLifeIsNull() {
            addCriterion("SERVICE_LIFE is null");
            return (Criteria) this;
        }

        public Criteria andServiceLifeIsNotNull() {
            addCriterion("SERVICE_LIFE is not null");
            return (Criteria) this;
        }

        public Criteria andServiceLifeEqualTo(String value) {
            addCriterion("SERVICE_LIFE =", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeNotEqualTo(String value) {
            addCriterion("SERVICE_LIFE <>", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeGreaterThan(String value) {
            addCriterion("SERVICE_LIFE >", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeGreaterThanOrEqualTo(String value) {
            addCriterion("SERVICE_LIFE >=", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeLessThan(String value) {
            addCriterion("SERVICE_LIFE <", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeLessThanOrEqualTo(String value) {
            addCriterion("SERVICE_LIFE <=", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeLike(String value) {
            addCriterion("SERVICE_LIFE like", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeNotLike(String value) {
            addCriterion("SERVICE_LIFE not like", value, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeIn(List<String> values) {
            addCriterion("SERVICE_LIFE in", values, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeNotIn(List<String> values) {
            addCriterion("SERVICE_LIFE not in", values, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeBetween(String value1, String value2) {
            addCriterion("SERVICE_LIFE between", value1, value2, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andServiceLifeNotBetween(String value1, String value2) {
            addCriterion("SERVICE_LIFE not between", value1, value2, "serviceLife");
            return (Criteria) this;
        }

        public Criteria andOtherCasesIsNull() {
            addCriterion("OTHER_CASES is null");
            return (Criteria) this;
        }

        public Criteria andOtherCasesIsNotNull() {
            addCriterion("OTHER_CASES is not null");
            return (Criteria) this;
        }

        public Criteria andOtherCasesEqualTo(String value) {
            addCriterion("OTHER_CASES =", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesNotEqualTo(String value) {
            addCriterion("OTHER_CASES <>", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesGreaterThan(String value) {
            addCriterion("OTHER_CASES >", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesGreaterThanOrEqualTo(String value) {
            addCriterion("OTHER_CASES >=", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesLessThan(String value) {
            addCriterion("OTHER_CASES <", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesLessThanOrEqualTo(String value) {
            addCriterion("OTHER_CASES <=", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesLike(String value) {
            addCriterion("OTHER_CASES like", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesNotLike(String value) {
            addCriterion("OTHER_CASES not like", value, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesIn(List<String> values) {
            addCriterion("OTHER_CASES in", values, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesNotIn(List<String> values) {
            addCriterion("OTHER_CASES not in", values, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesBetween(String value1, String value2) {
            addCriterion("OTHER_CASES between", value1, value2, "otherCases");
            return (Criteria) this;
        }

        public Criteria andOtherCasesNotBetween(String value1, String value2) {
            addCriterion("OTHER_CASES not between", value1, value2, "otherCases");
            return (Criteria) this;
        }

        public Criteria andNotesIsNull() {
            addCriterion("NOTES is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("NOTES is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("NOTES =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("NOTES <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("NOTES >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("NOTES >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("NOTES <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("NOTES <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("NOTES like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("NOTES not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("NOTES in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("NOTES not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("NOTES between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("NOTES not between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathIsNull() {
            addCriterion("SPLIT_CHART_PATH is null");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathIsNotNull() {
            addCriterion("SPLIT_CHART_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathEqualTo(String value) {
            addCriterion("SPLIT_CHART_PATH =", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathNotEqualTo(String value) {
            addCriterion("SPLIT_CHART_PATH <>", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathGreaterThan(String value) {
            addCriterion("SPLIT_CHART_PATH >", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathGreaterThanOrEqualTo(String value) {
            addCriterion("SPLIT_CHART_PATH >=", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathLessThan(String value) {
            addCriterion("SPLIT_CHART_PATH <", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathLessThanOrEqualTo(String value) {
            addCriterion("SPLIT_CHART_PATH <=", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathLike(String value) {
            addCriterion("SPLIT_CHART_PATH like", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathNotLike(String value) {
            addCriterion("SPLIT_CHART_PATH not like", value, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathIn(List<String> values) {
            addCriterion("SPLIT_CHART_PATH in", values, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathNotIn(List<String> values) {
            addCriterion("SPLIT_CHART_PATH not in", values, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathBetween(String value1, String value2) {
            addCriterion("SPLIT_CHART_PATH between", value1, value2, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andSplitChartPathNotBetween(String value1, String value2) {
            addCriterion("SPLIT_CHART_PATH not between", value1, value2, "splitChartPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathIsNull() {
            addCriterion("PARCEL_MAP_PATH is null");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathIsNotNull() {
            addCriterion("PARCEL_MAP_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathEqualTo(String value) {
            addCriterion("PARCEL_MAP_PATH =", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathNotEqualTo(String value) {
            addCriterion("PARCEL_MAP_PATH <>", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathGreaterThan(String value) {
            addCriterion("PARCEL_MAP_PATH >", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathGreaterThanOrEqualTo(String value) {
            addCriterion("PARCEL_MAP_PATH >=", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathLessThan(String value) {
            addCriterion("PARCEL_MAP_PATH <", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathLessThanOrEqualTo(String value) {
            addCriterion("PARCEL_MAP_PATH <=", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathLike(String value) {
            addCriterion("PARCEL_MAP_PATH like", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathNotLike(String value) {
            addCriterion("PARCEL_MAP_PATH not like", value, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathIn(List<String> values) {
            addCriterion("PARCEL_MAP_PATH in", values, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathNotIn(List<String> values) {
            addCriterion("PARCEL_MAP_PATH not in", values, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathBetween(String value1, String value2) {
            addCriterion("PARCEL_MAP_PATH between", value1, value2, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andParcelMapPathNotBetween(String value1, String value2) {
            addCriterion("PARCEL_MAP_PATH not between", value1, value2, "parcelMapPath");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnIsNull() {
            addCriterion("REFERENCE_COLUMN is null");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnIsNotNull() {
            addCriterion("REFERENCE_COLUMN is not null");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnEqualTo(String value) {
            addCriterion("REFERENCE_COLUMN =", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnNotEqualTo(String value) {
            addCriterion("REFERENCE_COLUMN <>", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnGreaterThan(String value) {
            addCriterion("REFERENCE_COLUMN >", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnGreaterThanOrEqualTo(String value) {
            addCriterion("REFERENCE_COLUMN >=", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnLessThan(String value) {
            addCriterion("REFERENCE_COLUMN <", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnLessThanOrEqualTo(String value) {
            addCriterion("REFERENCE_COLUMN <=", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnLike(String value) {
            addCriterion("REFERENCE_COLUMN like", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnNotLike(String value) {
            addCriterion("REFERENCE_COLUMN not like", value, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnIn(List<String> values) {
            addCriterion("REFERENCE_COLUMN in", values, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnNotIn(List<String> values) {
            addCriterion("REFERENCE_COLUMN not in", values, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnBetween(String value1, String value2) {
            addCriterion("REFERENCE_COLUMN between", value1, value2, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andReferenceColumnNotBetween(String value1, String value2) {
            addCriterion("REFERENCE_COLUMN not between", value1, value2, "referenceColumn");
            return (Criteria) this;
        }

        public Criteria andPrintTypeIsNull() {
            addCriterion("PRINT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPrintTypeIsNotNull() {
            addCriterion("PRINT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPrintTypeEqualTo(String value) {
            addCriterion("PRINT_TYPE =", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeNotEqualTo(String value) {
            addCriterion("PRINT_TYPE <>", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeGreaterThan(String value) {
            addCriterion("PRINT_TYPE >", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PRINT_TYPE >=", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeLessThan(String value) {
            addCriterion("PRINT_TYPE <", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeLessThanOrEqualTo(String value) {
            addCriterion("PRINT_TYPE <=", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeLike(String value) {
            addCriterion("PRINT_TYPE like", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeNotLike(String value) {
            addCriterion("PRINT_TYPE not like", value, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeIn(List<String> values) {
            addCriterion("PRINT_TYPE in", values, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeNotIn(List<String> values) {
            addCriterion("PRINT_TYPE not in", values, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeBetween(String value1, String value2) {
            addCriterion("PRINT_TYPE between", value1, value2, "printType");
            return (Criteria) this;
        }

        public Criteria andPrintTypeNotBetween(String value1, String value2) {
            addCriterion("PRINT_TYPE not between", value1, value2, "printType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
}