package uk.co.kyleharrison.pim.model;

import com.eaio.uuid.UUID;

public class Product {
	protected UUID id;
	protected String name;
	protected long barcode;
	protected int quantity;
	protected String mediatype;
	protected boolean success;
	
	public Product() {
		super();
	}

	public Product(String name, long barcode, int quantity) {
		super();
		this.name = name;
		this.barcode = barcode;
		this.quantity = quantity;
	}
	
	public Product(String name, long barcode, int quantity, String mediatype) {
		super();
		this.name = name;
		this.barcode = barcode;
		this.quantity = quantity;
		this.mediatype = mediatype;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID generateTimeUUID(){
		this.id = new UUID();
		return this.id;
	}
		
}
