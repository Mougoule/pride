package fr.pride.project.model.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public final class PaginatedResponse {

	// paramètres calculées
	private int countResult;
	private int lastPageNumber;
	private int nextPageNumber;

	// paramètres de l'utilisateur
	private int pageNumber;
	private int pageSize;

	// réponse de page
	private Object pageData;

	/* contructeur invisible */
	private PaginatedResponse() {

	}

	/**
	 * Monteur de la classe
	 * 
	 */
	public static class Builder {

		// paramètres calculées
		private int lastPageNumber;
		private int countResult;

		// paramètres de l'utilisateur
		private int pageNumber;
		private int pageSize;

		// réponse de page
		private Object pageData;

		/** 
		 * construction et validation de la réponse paginée 
		 */
		public PaginatedResponse buildPage() {
			PaginatedResponse paginatedResponse = new PaginatedResponse();
			paginatedResponse.countResult = this.countResult;
			paginatedResponse.lastPageNumber = this.lastPageNumber;
			paginatedResponse.pageData = this.pageData;
			paginatedResponse.pageNumber = this.pageNumber;
			paginatedResponse.pageSize = this.pageSize;
			paginatedResponse.nextPageNumber = pageNumber < lastPageNumber ? pageNumber + 1 : lastPageNumber;
			return paginatedResponse;
		}

		/**
		 * @param lastPageNumber
		 *            the lastPageNumber to set
		 */
		public Builder setLastPageNumber(int lastPageNumber) {
			this.lastPageNumber = lastPageNumber;
			return this;
		}

		/**
		 * @param countResult
		 *            the countResult to set
		 */
		public Builder setCountResult(int countResult) {
			this.countResult = countResult;
			return this;
		}

		/**
		 * @param pageNumber
		 *            the pageNumber to set
		 */
		public Builder setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}

		/**
		 * @param pageSize
		 *            the pageSize to set
         * @return instance du builder
		 */
		public Builder setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		/**
		 * @param pageData
		 *            the pageData to set
         * @return instance du builder
		 */
		public Builder setPageData(Object pageData) {
			this.pageData = pageData;
			return this;
		}

	}

	/**
	 * @return the lastPageNumber
	 */
	public int getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @return the countResult
	 */
	public int getCountResult() {
		return countResult;
	}

	/**
	 * @return the nextPageNumber
	 */
	public int getNextPageNumber() {
		return nextPageNumber;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the pageData
	 */
	public Object getPageData() {
		return pageData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
