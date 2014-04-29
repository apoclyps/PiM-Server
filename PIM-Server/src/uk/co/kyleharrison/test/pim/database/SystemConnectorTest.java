package uk.co.kyleharrison.test.pim.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.eaio.uuid.UUID;

import uk.co.kyleharrison.pim.cassandra.SystemConnector;
import uk.co.kyleharrison.pim.model.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemConnectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Ainitialisation() {
		SystemConnector systemConnector = new SystemConnector();
		assertNotNull("Instantising System Connector", systemConnector);
	}
	
	@Test
	public void BcreateProduct(){
		Product currentProduct = new Product();
		assertNotNull("Creating Current Product", currentProduct);
		long barcode = 75960607909302011L;
		currentProduct.setBarcode(barcode);
		assertEquals("currentProduct", currentProduct.getBarcode(),barcode);
		
		UUID id = currentProduct.generateTimeUUID();
		assertNotNull("Time UUID", id);
	}
	
	@Test
	public void CproductInsertion(){
		SystemConnector systemConnector = new SystemConnector();
		assertNotNull("Instantising System Connector", systemConnector);
		
		Product currentProduct = new Product();
		assertNotNull("Creating Current Product", currentProduct);
		
		UUID id = currentProduct.generateTimeUUID();
		assertNotNull("Time UUID", id);
		
		long barcode = 75960607909302011L;
		currentProduct.setBarcode(barcode);
		assertEquals("Current Product Barcode", currentProduct.getBarcode(),barcode);
		
		currentProduct.setName("X-men Legacy - Issue 20");
		assertNotNull("Current Product Name ", currentProduct.getName());
		
		boolean insertionSuccess = systemConnector.insertItem(currentProduct);
		assert( insertionSuccess == true);
	}
	
	@Test
	public void DproductRetrieval(){
		SystemConnector systemConnector = new SystemConnector();
		assertNotNull("Instantising System Connector", systemConnector);
		
		Product currentProduct = new Product();
		assertNotNull("Creating Current Product", currentProduct);
		
		long barcode = 75960607909302011L;
		currentProduct.setBarcode(barcode);
		assertEquals("Current Product Barcode", currentProduct.getBarcode(),barcode);
		
		String name = "X-men Legacy - Issue 20";
		currentProduct.setName(name);
		assertNotNull("Current Product Name ", currentProduct.getName());
		
		Product retrievalItem = systemConnector.retrieveItem(currentProduct.getBarcode());
		assertNotNull("Product Retrieval", retrievalItem);
	
		assertNotNull("Time UUID",retrievalItem.getId() );
		assertEquals("Barcode", barcode, retrievalItem.getBarcode());
		assertEquals("Name", name, retrievalItem.getName());
		System.out.println(retrievalItem.getId());
	}
	
	
	public void FSimpleDelete(){
		SystemConnector systemConnector = new SystemConnector();
		Product currentProduct = new Product();
		currentProduct.setId(new UUID("3f9087a0-cfdf-11e3-ad4d-6c626d297ffa"));
		long barcode = 75960607909302011L;
		currentProduct.setBarcode(barcode);
		systemConnector.deleteItem(currentProduct);
	}

	@Test
	public void EproductDeletion(){
		SystemConnector systemConnector = new SystemConnector();
		assertNotNull("Instantising System Connector", systemConnector);
		
		Product currentProduct = new Product();
		assertNotNull("Creating Current Product", currentProduct);
		
		long barcode = 75960607909302011L;
		currentProduct.setBarcode(barcode);
		assertEquals("Current Product Barcode", currentProduct.getBarcode(),barcode);
			
		Product retrievalItem = systemConnector.retrieveItem(currentProduct.getBarcode());
		assertNotNull("Product Retrieval", retrievalItem);
		
		assertNotNull("Time UUID",retrievalItem.getId() );
		System.out.println(retrievalItem.getId());
		assertEquals("Barcode", barcode, retrievalItem.getBarcode());

		boolean deletionSuccess = systemConnector.deleteItem(retrievalItem);
		assert( deletionSuccess == true);
	}
	
}
