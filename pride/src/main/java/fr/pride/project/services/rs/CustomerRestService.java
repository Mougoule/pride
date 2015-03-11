package fr.pride.project.services.rs;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.pride.project.services.business.CustomerBusinessService;

/**
 * Implementation du service {@link CustomerRestService} Il se base dans les
 * meilleurs pratiques du développement du <b>pragmatic restful</b> :
 * http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api
 * 
 *
 */
@Path("/customers")
public class CustomerRestService {

	/* Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerRestService.class);

	@Autowired
	private CustomerBusinessService customerBusinessService;	
	
	/**
     * création d'un nouvelle client
     * @param customer client à créer
     * @return information du client créé
     */
  /*  @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("")
    public Response createCustomer(Customer input) {
		Response response;
		try {
			Customer customer = customerBusinessService.createCustomer(input);
			response = RestServiceHelper.handleSuccessfulResponse(customer);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}*/

	/**
     * Mise à jour du client
     * @param customer client � modifier
     * @return info du client modifi�
     */
   /* @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{input}")
    public Response updateCustomer(@PathParam("input") String customerId, Customer customer) {
		Response response;
		try {
			customer.setEmail(customerId);
			Customer updateCustomer = customerBusinessService
					.updateCustomer(customer);
			return RestServiceHelper.handleSuccessfulResponse(updateCustomer);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}*/

    /**
     * Desactivation du client
     * @param customerId client a desactiver
     * @return contenu du client desactivé
     */
    /*@DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{input}")
    public Response deactivateCustomer(@PathParam("input") String input) {
		Response response;
		try {
			Customer modifiedCustomer = customerBusinessService
					.deactivateCustomer(input);
			response = RestServiceHelper
					.handleSuccessfulResponse(modifiedCustomer);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}*/

	/**
     * recherche du client par id
     * @param customerId identification du client à chercher ou <code>null</code>
     * @return information du client
     */
   /* @GET
    @Path("/{input}")
    @Produces("application/json")
    public Response getCustomerByMail(@PathParam("input") String input) {
		Response response;
		try {
			Customer bean = customerBusinessService.getCustomerByMail(input);
			response = RestServiceHelper.handleSuccessfulResponse(bean);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		} catch (NumberFormatException e) {
			BaseException be = new BusinessException(CustomError.ERROR_CUSTOMER_INVALID_DATA, "Could not read Id : " + input,
					e.getMessage());
			response = RestServiceHelper.handleFailureResponse(be);
		}
		return response;
	}*/

    /**
     * recherche des factures du client
     * @param customerId idenitfication du client
     * @return liste de factures du client ou vide s'ils n'existent pas ou le client est invalide
     */
    /*@GET
    @Path("/{input}/invoices")
    @Produces("application/json")
    public Response getInvoicesByCustomerMail(@PathParam("input") String input) {
		Response response;
		try {
			Collection<Invoice> responseList = customerBusinessService
					.getInvoicesByCustomer(input);
			response = RestServiceHelper.handleSuccessfulResponse(responseList);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		} catch (NumberFormatException e) {
			BaseException be = new BusinessException(CustomError.ERROR_CUSTOMER_INVALID_DATA, "Could not read Id : " + input,
					e.getMessage());
			response = RestServiceHelper.handleFailureResponse(be);
		}
		return response;
	}*/

    /**
     * recherche d'une facture du client
     * @param customerId identification du client
     * @param invoiceId identification de la facture
     * @return liste une facture du client
     */
    /*@GET
    @Path("/{customerId}/invoices/{invoiceId}")
    @Produces("application/json")
    public Response getInvoicesByIdAndCustomer(@PathParam("customerId") String customer, @PathParam("invoiceId") String invoice) {
		Response response;
		try {
			Long customerId = Long.parseLong(customer);
			Long invoiceId = Long.parseLong(invoice);
			Invoice invoiceResponse = customerBusinessService
					.getInvoiceByIdAndIdCustomer(customerId, invoiceId);
			response = RestServiceHelper
					.handleSuccessfulResponse(invoiceResponse);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		} catch (NumberFormatException e) {
			BaseException be = new BusinessException(CustomError.ERROR_CUSTOMER_INVALID_DATA, "Could not read Id : "
					+ customer + ", " + invoice, e.getMessage());
			response = RestServiceHelper.handleFailureResponse(be);
		}
		return response;
	}*/

}
