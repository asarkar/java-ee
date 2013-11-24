package name.abhijitsarkar.webservices.jaxws.aws.client;

import java.util.List;

import javax.xml.ws.Holder;

import name.abhijitsarkar.util.logging.AppLogger;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.AWSECommerceService;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.AWSECommerceServicePortType;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.Item;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.ItemSearch;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.ItemSearchRequest;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.Items;
import name.abhijitsarkar.webservices.jaxws.aws.client.generated.OperationRequest;

import org.apache.log4j.Logger;

public class ProductAdvertisingAPIClient {
	private static final String ACCESS_KEY_ID = "AKIAJLGCOB6CWDELK4FA";
	private static final String ASSOCIATES_ID = "websecertipra-20";

	private static final Logger logger = AppLogger
			.getInstance(ProductAdvertisingAPIClient.class);

	public static void main(String[] args) {
		new ProductAdvertisingAPIClient().searchItem();
	}

	private void searchItem() {
		AWSECommerceService service = new AWSECommerceService();
		AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

		ItemSearchRequest request = new ItemSearchRequest();
		request.setSearchIndex("Books");
		request.setKeywords("java webservices");
		ItemSearch search = new ItemSearch();
		search.getRequest().add(request);
		search.setAWSAccessKeyId(ACCESS_KEY_ID);
		search.setAssociateTag(ASSOCIATES_ID);

		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.itemSearch(search.getMarketplaceDomain(),
				search.getAWSAccessKeyId(), search.getAssociateTag(),
				search.getXMLEscaping(), search.getValidate(),
				search.getShared(), search.getRequest(), operationRequest,
				items);

		// Unpack the response to print the book titles.
		Items retval = items.value.get(0); // first and only Items element
		List<Item> item_list = retval.getItem(); // list of Item subelements
		for (Item item : item_list)
			// each Item in the list
			logger.debug(item.getItemAttributes().getTitle());
	}
}
