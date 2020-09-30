package com.epam.microservices.shop;

import com.epam.microservices.shop.ContractVerifierBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;

public class ContractVerifierTest extends ContractVerifierBase {

	@Test
	public void validate_shouldReturnOrderDetails() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();

		// when:
			ResponseOptions response = given().spec(request)
					.get("/orders/1");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['deliveryAddress']").isEqualTo("address");
			assertThatJson(parsedJson).field("['externalShopId']").isEqualTo(1L);
			assertThatJson(parsedJson).field("['totalAmount']").isEqualTo(10.0);
			assertThatJson(parsedJson).field("['orderStatus']").isEqualTo("WAITING_VERIFICATION");
			assertThatJson(parsedJson).field("['orderIdentifier']").isEqualTo("1");
	}

}
