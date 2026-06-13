package br.edu.ufrgs.dto;

import java.util.List;

// dto/PagedResponse.java
public class PagedResponse<T> {
    public static class Builder<T> {
	    private List<T> content;
	    private int page;
	    private int size;
	    private long totalElements;
	    private int totalPages;
	    private boolean last;

	    public Builder<T> content(List<T> content) {
			this.content = content;
			return this;
	    }

	    public Builder<T> page(int page) {
			this.page = page;
			return this;
	    }

	    public Builder<T> size(int size) {
			this.size = size;
			return this;
	    }

	    public Builder<T> totalElements(long totalElements) {
			this.totalElements = totalElements;
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
			return new PagedResponse<>(content, page, size, totalElements, totalPages, last);
	    }
	}

	public static <T> Builder<T> builder() {
	    return new Builder<>();
	}
	
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    // Default constructor (needed for JSON serialization)
    public PagedResponse() {}

    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    // Getters and Setters
    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}
