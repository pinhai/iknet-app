package com.iknet.commons.util;

import java.io.Serializable;

public class SimplePage implements Pageable, Serializable {

	private static final long serialVersionUID = -6283758828350113183L;
	protected int totalCount = 0;
	protected int pageSize = 100;
	protected int pageNo = 1;
	// protected long startRowNum;
	// protected long endRowNum;
	public static final int DEF_COUNT = 100;

	protected long startLimit;

	public SimplePage() {
	}

	public SimplePage(int pageNo, int pageSize, int totalCount) {
		if (totalCount <= 0)
			this.totalCount = 0;
		else {
			this.totalCount = totalCount;
		}
		if (pageSize <= 0)
			this.pageSize = 100;
		else {
			this.pageSize = pageSize;
		}
		if (pageNo <= 0)
			this.pageNo = 1;
		else {
			this.pageNo = pageNo;
		}
		if ((this.pageNo - 1) * this.pageSize >= totalCount)
			this.pageNo = (totalCount / this.pageSize);
	}

	public void adjustPage() {
		if (this.totalCount <= 0) {
			this.totalCount = 0;
		}
		if (this.pageSize <= 0) {
			this.pageSize = 100;
		}
		if (this.pageNo <= 0) {
			this.pageNo = 1;
		}
		if ((this.pageNo - 1) * this.pageSize >= this.totalCount)
			this.pageNo = (this.totalCount / this.pageSize);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public int getTotalPage() {
		int totalPage = this.totalCount / this.pageSize;
		if ((this.totalCount % this.pageSize != 0) || (totalPage == 0)) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return this.pageNo <= 1;
	}

	public boolean isLastPage() {
		return this.pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return this.pageNo;
		}
		return this.pageNo + 1;
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return this.pageNo;
		}
		return this.pageNo - 1;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public long getStartLimit() {
		if (isFirstPage()) {
			return 0L;
		}
		return (this.pageNo - 1) * this.pageSize;
	}

	public void setStartLimit(long startLimit) {
		this.startLimit = startLimit;
	}

	// public long getStartRowNum() {
	// if (isFirstPage()) {
	// return 0L;
	// }
	// return (this.pageNo - 1) * this.pageSize;
	// }
	//
	// public void setStartRowNum(long startRowNum) {
	// this.startRowNum = startRowNum;
	// }
	//
	// public long getEndRowNum() {
	// return getStartRowNum() + this.pageSize + 1L;
	// }
	//
	// public void setEndRowNum(long endRowNum) {
	// this.endRowNum = endRowNum;
	// }

}
