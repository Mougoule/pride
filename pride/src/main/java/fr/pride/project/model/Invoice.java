package fr.pride.project.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The persistent class for the invoices database table.
 * 
 */
@Table(name = "invoices", indexes = {
		@Index(name = "idx_customer", columnList = "id_customer"),
		@Index(name = "idx_date", columnList = "date"),
		@Index(name = "idx_value", columnList = "value") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i ORDER BY i.customer"),
		@NamedQuery(name = "Invoice.findByCustomerLogin", query = "SELECT i FROM Invoice i WHERE i.customer.email = :LOGIN"),
		@NamedQuery(name = "Invoice.findByCustomerId", query = "SELECT i FROM Invoice i WHERE i.customer.id = :ID"),
		@NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :ID"),
		@NamedQuery(name = "Invoice.findByIdAndCustomer", query = "SELECT i FROM Invoice i WHERE i.id = :ID AND i.customer.id = :CUSTOMER_ID") })
@XmlRootElement(name="invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice extends AbstractEntity {

	private static final long serialVersionUID = -3190350596813466072L;

	private Date date;

	private BigDecimal value;
	
	@XmlTransient
	private Customer customer;

	/**
	 * @return the date
	 */
	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	@Column(name = "value", nullable = false, length = 10)
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * bi-directional many-to-one association to Customer
	 * 
	 * @return the customer
	 */
	@JsonIgnore
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_customer")
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}