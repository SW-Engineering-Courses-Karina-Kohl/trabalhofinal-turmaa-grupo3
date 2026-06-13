package br.edu.ufrgs.dto;

import java.util.List;

// dto/PagedResponse.java
public class PagedResponse<T> {
    public static class Builder<T> {
	    private List<T> data;
	    private int page;
	    private int pageSize;
	    private long total;
	    private int totalPages;
	    private boolean last;

	    public Builder<T> data(List<T> data) {
			this.data = data;
			return this;
	    }

	    public Builder<T> page(int page) {
			this.page = page;
			return this;
	    }

	    public Builder<T> pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
	    }

	    public Builder<T> total(long total) {
			this.total = total;
			return this;
	    }

	    public Builder<T> totalPages(int totalPages) {
			this.totalPages = totalPages;
			return this;
	    }

	    public Builder<T> last(boolean last) {
			this.last = last;
			return this;
	    }

	    public PagedResponse<T> build() {
			return new PagedResponse<>(data, page, pageSize, total, totalPages, last);
	    }
	}

	public static <T> Builder<T> builder() {
	    return new Builder<>();
	}
	
    private List<T> data;
    private int page;
    private int pageSize;
    private long total;
    private int totalPages;
    private boolean last;

    // Default constructor (needed for JSON serialization)
    public PagedResponse() {}

    public PagedResponse(List<T> data, int page, int pageSize, long total, int totalPages, boolean last) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = totalPages;
        this.last = last;
    }

    // Getters and Setters
    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}
