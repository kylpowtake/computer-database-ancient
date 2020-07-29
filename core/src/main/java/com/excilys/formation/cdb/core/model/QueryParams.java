package com.excilys.formation.cdb.core.model;

public class QueryParams {
	private String orderBy;
	private String offSet;
	private String limit;
	private String search;

	public QueryParams(String orderBy, String offSet, String limit, String search) {
		super();
		this.orderBy = orderBy;
		this.offSet = offSet;
		this.limit = limit;
		this.search = search;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		try {
			if (!Class.forName(this.getClass().getName()).isInstance(obj)) {
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}
		QueryParams other = (QueryParams) obj;
		if ((this.getOrderBy() == null && other.getOrderBy() != null)
				|| (this.getOrderBy() != null && !this.getOrderBy().equals(other.getOrderBy()))) {
			return false;
		}
		if ((this.getOffSet() == null && other.getOffSet() != null)
				|| (this.getOffSet() != null && !this.getOffSet().equals(other.getOffSet()))) {
			return false;
		}
		if ((this.getLimit() == null && other.getLimit() != null)
				|| (this.getLimit() != null && !this.getLimit().equals(other.getLimit()))) {
			return false;
		}
		if ((this.getOrderBy() == null && other.getOrderBy() != null)
				|| (this.getOrderBy() != null && !this.getOrderBy().equals(other.getOrderBy()))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int value = 14;
		int result = 1;
		result = value * result + ((this.getOrderBy() == null) ? 0 : this.getOrderBy().hashCode());
		result = value * result + ((this.getOffSet() == null) ? 0 : this.getOffSet().hashCode());
		result = value * result + ((this.getLimit() == null) ? 0 : this.getLimit().hashCode());
		result = value * result + ((this.getSearch() == null) ? 0 : this.getSearch().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "orderBy : " + this.getOrderBy() + ", offSet : " + this.getOffSet() + ", limit : " + this.getLimit() + ", search : " + this.getSearch();
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOffSet() {
		return offSet;
	}

	public void setOffSet(String offSet) {
		this.offSet = offSet;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
