package jp.co.sample.emp_management.util;

import java.util.ArrayList;
import java.util.List;

public class Pagenation {
	private int maxContentSize = 1;
	private int pageSize = 1;
	@SuppressWarnings("rawtypes")
	private List list;
	private List<Integer> pageNumList = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	public Pagenation(List list, int maxContentSize) {
		this.list = list;
		this.maxContentSize = maxContentSize;
		pageSize = ((list.size() - 1) / maxContentSize) + 1;
		for (int i = 1; i <= pageSize; i++) {
			pageNumList.add(i);
		}
	}

	@SuppressWarnings("rawtypes")
	public List getNumPageContent(int num) {
		if (num > pageSize) {
			return null;
		}
		int fromSize = (num - 1) * maxContentSize;
		int toSize = num * maxContentSize;

		if (toSize > list.size()) {
			toSize = list.size();
		}

		List subList = list.subList(fromSize, toSize);

		return subList;
	}

	public int getMaxContentSize() {
		return maxContentSize;
	}

	public void setMaxContentSize(int maxContentSize) {
		this.maxContentSize = maxContentSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List<Integer> getPageNumList() {
		return pageNumList;
	}

	public void setPageNumList(List<Integer> pageNumList) {
		this.pageNumList = pageNumList;
	}

}
