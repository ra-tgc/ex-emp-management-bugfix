package jp.co.sample.emp_management.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ページネーションを行うクラス.
 * 
 * @author masaki.taguchi
 *
 */
public class Pagenation {
	/** 1ページの最大表示数 */
	private int maxContentSize = 1;
	/** ページ数 */
	private int pageSize = 1;
	/** ページネーション対象のリスト */
	@SuppressWarnings("rawtypes")
	private List contentList;
	/** ページ番号リスト */
	private List<Integer> pageNumList = new ArrayList<>();

	/**
	 * コンストラクタ.
	 * 
	 * @param list           ページネーション対象のリスト
	 * @param maxContentSize 1ページの最大表示数
	 */
	@SuppressWarnings("rawtypes")
	public Pagenation(List list, int maxContentSize) {
		this.contentList = list;
		this.maxContentSize = maxContentSize;
		if (list.size() != 0) {
			pageSize = ((list.size() - 1) / maxContentSize) + 1;
		}
		for (int i = 1; i <= pageSize; i++) {
			pageNumList.add(i);
		}
	}

	/**
	 * ページ番号のコンテンツリストを取得する.
	 * 
	 * @param num ページ番号
	 * @return ページ番号のコンテンツリスト
	 */
	@SuppressWarnings("rawtypes")
	public List getNumPageContent(int num) {
		if (num > pageSize) {
			return null;
		}
		int fromSize = (num - 1) * maxContentSize;
		int toSize = num * maxContentSize;

		if (toSize > contentList.size()) {
			toSize = contentList.size();
		}

		List subList = contentList.subList(fromSize, toSize);

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

	@SuppressWarnings("rawtypes")
	public List getContentList() {
		return contentList;
	}

	@SuppressWarnings("rawtypes")
	public void setContentList(List contentList) {
		this.contentList = contentList;
	}

	public List<Integer> getPageNumList() {
		return pageNumList;
	}

	public void setPageNumList(List<Integer> pageNumList) {
		this.pageNumList = pageNumList;
	}

	@Override
	public String toString() {
		return "Pagenation [maxContentSize=" + maxContentSize + ", pageSize=" + pageSize + ", contentList="
				+ contentList + ", pageNumList=" + pageNumList + "]";
	}

}
