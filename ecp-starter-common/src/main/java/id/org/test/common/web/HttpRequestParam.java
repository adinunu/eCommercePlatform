package id.org.test.common.web;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class HttpRequestParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8274171936930231029L;

	private Integer displayStart;
	private Integer pageSize;
	private String sortColumn;
	private String sortDirection;
	private Sort sort;

	public HttpRequestParam() {
	}

	public HttpRequestParam(Integer displayStart, Integer pageSize, String sortColumn, String sortDirection) {
		this.displayStart = displayStart;
		this.pageSize = pageSize;
		this.sortColumn = sortColumn;
		this.sortDirection = sortDirection;
	}

	public HttpRequestParam getRequestParam() {
		if (this.displayStart == null) {
			setDisplayStart(1);
		} else {
			setDisplayStart(this.displayStart);
		}
		if (this.pageSize == null) {
			setPageSize(10);
		} else {
			setPageSize(this.pageSize);
		}

		if (this.sortColumn == null) {
			setSortColumn("id");
		} else {
			setSortColumn(this.sortColumn);
		}

		if (sortDirection == null) {
			setSortDirection("ASC");
		} else {
			setSortDirection(sortDirection);
		}
		this.setSort(new Sort(Sort.Direction.fromString(this.sortDirection), this.sortColumn));
		return this;
	}
}
