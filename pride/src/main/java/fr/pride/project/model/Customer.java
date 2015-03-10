package fr.pride.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The persistent class for the customers database table.
 * 
 */
@Table(name = "customers", indexes = {
		@Index(name = "idx_name", columnList = "name"),
		@Index(name = "idx_email", columnList = "email", unique = true),
		@Index(name = "idx_active", columnList = "active"),
		@Index(name = "idx_address", columnList = "postcode") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c ORDER BY c.email"),
		@NamedQuery(name = "Customer.findAllActive", query = "SELECT c FROM Customer c WHERE c.active=true ORDER BY c.email "),
		@NamedQuery(name = "Customer.findByLogin", query = "SELECT c FROM Customer c WHERE c.email = :LOGIN"),
		@NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c.email) FROM Customer c"),
		@NamedQuery(name = "Customer.countById", query = "SELECT COUNT(c.email) FROM Customer c where c.id = :ID"),
		@NamedQuery(name = "Customer.countByLogin", query = "SELECT COUNT(c.email) FROM Customer c WHERE c.email = :LOGIN") })
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = -1154160115447779484L;

	private boolean active;

	private String email;
	
	private String name;

	private Address address;
	
	@XmlElementWrapper(name="invoices")
	@XmlElement(name="invoice")
	private Set<Invoice> invoices = new HashSet<Invoice>();

	public Customer() {
	}

	/**
	 * @return the active
	 */
	@Column(name = "active", nullable = false)
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the address
	 */
	@Embedded
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the name
	 */
	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the invoices
	 */
	/** bi-directional many-to-one association to Invoice */
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * @param invoices
	 *            the invoices to set
	 */
	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setCustomer(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setCustomer(null);

		return invoice;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}