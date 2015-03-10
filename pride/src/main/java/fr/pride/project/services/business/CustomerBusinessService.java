package fr.pride.project.services.business;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import fr.pride.project.model.Address;
import fr.pride.project.model.Customer;
import fr.pride.project.model.Invoice;
import fr.pride.project.services.business.CustomerBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;
import fr.pride.project.services.rs.CustomerRestService;

/**
 * Implementation of {@link CustomerBusinessService}
 * 
 */
@EnableTransactionManagement
@Service
public class CustomerBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerBusinessService.class);
	
	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	/**
	 * @see CustomerBusinessService#createCustomer(Customer)
	 */
	@Transactional
	public Customer createCustomer(Customer input) throws BaseException {
		LOGGER.info("création d'un utilisateur");
		Assert.notNull(input);
		Customer response;
		Long amt = (Long) em.createNamedQuery("Customer.countByLogin")
				.setParameter("LOGIN", input.getEmail()).getSingleResult();
		if (amt == 0) {
			input.setId(null);
			LOGGER.info("creation de l'utilisateur avec login : {}",
					input.getEmail());

			em.persist(input);
			response = input;
		} else {
			// Mails already exists
			throw new BusinessException(
					CustomError.ERROR_CUSTOMER_ALREADY_EXISTS, 
					"a user already exists for mail : " + input.getEmail());
		}

		return response;
	}

	/**
	 * @see CustomerBusinessService#updateCustomer(Customer)
	 */
	@Transactional
	public Customer updateCustomer(Customer input) throws BaseException {
		Assert.notNull(input);
		Assert.notNull(input.getEmail());

		Customer response;
		Long amt = (Long) em.createNamedQuery("Customer.countByLogin")
				.setParameter("LOGIN", input.getEmail()).getSingleResult();
		if (amt == 1) {
			// response
			Customer existingCustomer = em
					.createNamedQuery("Customer.findByLogin", Customer.class)
					.setParameter("LOGIN", input.getEmail()).getSingleResult();
			// Mise à jour de l'addresse?
			if (input.getAddress() != null) {
				if (existingCustomer.getAddress() == null) {
					existingCustomer.setAddress(new Address());
				}
				if (input.getAddress().getCity() != null) {
					existingCustomer.getAddress().setCity(
							input.getAddress().getCity());
				}
				if (input.getAddress().getStreet() != null) {
					existingCustomer.getAddress().setStreet(
							input.getAddress().getStreet());
				}
				if (input.getAddress().getPostcode() != null) {
					existingCustomer.getAddress().setPostcode(
							input.getAddress().getPostcode());
				}
			}
			existingCustomer.setActive(input.isActive());
			// mise à jour de l'addresse?
			if (input.getName() != null) {
				existingCustomer.setName(input.getName());
			}
			LOGGER.info("mise à jour de l'utilisateur avec login : {}",
					input.getEmail());
			response = em.merge(existingCustomer);
		} else {
			throw new BusinessException(CustomError.ERROR_GENERIC_NOT_FOUND,
					"updateCustomer", "invalid id");
		}
		return response;
	}

	/**
	 * @see CustomerBusinessService#countCustomers()
	 */
	public int countCustomers() throws BaseException {
		return ((Number) em.createNamedQuery("Customer.countAll")
				.getSingleResult()).intValue();
	}

	/**
	 * @see CustomerBusinessService#getAllCustomers()
	 */
	public Collection<Customer> getAllCustomers() throws BaseException {
		LOGGER.info("recherche des utilisateurs");
		List<Customer> response = em.createNamedQuery("Customer.findAll",
				Customer.class).getResultList();
		return response;
	}

	/**
	 * @see CustomerBusinessService#getAllCustomers(int, int)
	 */
	public Collection<Customer> getAllCustomers(int pageNumber, int pageSize)
			throws BaseException {
		LOGGER.info("recherche des utilisateurs");
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll",
				Customer.class);
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Customer> response = query.getResultList();
		return response;
	}

	/**
	 * @see CustomerBusinessService#getCustomerById(Long)
	 */
	public Customer getCustomerById(Long code) throws BaseException {
		LOGGER.info("recherche de l'utilisateur avec l'id {}", code);
		Assert.notNull(code);
		Customer existingCustomer = null;
		try {
			existingCustomer = em.find(Customer.class, code);
		} catch (EntityNotFoundException e) {
			// does nothing
		}

		return existingCustomer;
	}

	/**
	 * @see CustomerRestService#getInvoicesByCustomerMail(String)
	 */
	public Collection<Invoice> getInvoicesByCustomer(String mail)
			throws BaseException {
		Assert.notNull(mail);
		LOGGER.info("recherche des factures de l'utilisateur avec mail {}", mail);
		Collection<Invoice> response = new LinkedList<Invoice>();
		response.addAll(em
				.createNamedQuery("Invoice.findByCustomerLogin", Invoice.class)
				.setParameter("LOGIN", mail).getResultList());
		return response;
	}

	/**
	 * @see CustomerRestService#deactivateCustomer(Long)
	 */
	@Transactional
	public Customer deactivateCustomer(String mail) throws BaseException {
		Assert.notNull(mail);
		LOGGER.info("desactivation de l'utilisateur avec login = {}", mail);
		try {
			Customer existingCustomer = this.getCustomerByMail(mail);
			existingCustomer.setActive(false);
			return em.merge(existingCustomer);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(
					CustomError.ERROR_GENERIC_NOT_FOUND,
					"deactivateCustomer", "invalid id");
		}
	}

	/**
	 * @see CustomerRestService#getInvoiceByIdAndIdCustomer(Long,Long)
	 */
	public Invoice getInvoiceByIdAndIdCustomer(Long customerId, Long invoiceId)
			throws BaseException {
		Assert.notNull(customerId);
		Assert.notNull(invoiceId);
		Invoice response = null;
		if (customerExist(customerId)) {
			try {
				TypedQuery<Invoice> query = em.createNamedQuery(
						"Invoice.findByIdAndCustomer", Invoice.class);
				response = query.setParameter("ID", invoiceId)
						.setParameter("CUSTOMER_ID", customerId)
						.getSingleResult();
			} catch (NoResultException e) {
				// entity non trouvée dans la base
			} catch (NonUniqueResultException e) {
				// plusieurs entitées avec la même id
			}
		}
		return response;
	}

	/*
	 * Vérifie si le client avec l'id specifié existe dans la base. Evidement
	 * c'est plus rapide de faire une <code>count</code> que faire un
	 * <code>findById(id)</code>
	 */
	private boolean customerExist(Long customerId) {
		int result = ((Number) em.createNamedQuery("Customer.countById")
				.setParameter("ID", customerId).getSingleResult()).intValue();
		return result == 1;
	}

	public Customer getCustomerByMail(String input) throws BaseException {
		Assert.notNull(input);
		Customer response = null;
		LOGGER.info("desactivation de l'utilisateur avec login = {}", input);
		try {
			TypedQuery<Customer> query = em.createNamedQuery(
					"Customer.findByLogin", Customer.class);
			response = query.setParameter("LOGIN", input).getSingleResult();
		} catch (EntityNotFoundException e) {
			throw new BusinessException(
					CustomError.ERROR_GENERIC_NOT_FOUND,
					"deactivateCustomer", "invalid id");
		}
		return response;
	}

}
