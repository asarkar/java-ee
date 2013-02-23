package com.amazon.webservices.awsecommerceservice._2011_08_01.client;

import java.util.List;

import javax.xml.ws.Holder;

import org.apache.log4j.Logger;

import com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceService;
import com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType;
import com.amazon.webservices.awsecommerceservice._2011_08_01.Item;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearch;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchRequest;
import com.amazon.webservices.awsecommerceservice._2011_08_01.Items;
import com.amazon.webservices.awsecommerceservice._2011_08_01.OperationRequest;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

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
//		service.setHandlerResolver();
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
