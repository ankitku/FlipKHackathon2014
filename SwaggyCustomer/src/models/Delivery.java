package models;

public class Delivery {
	
	String contactNo , name, deviceId, listingId ;

	public String getContactNo() {
		return contactNo;
	}

	public String getName() {
		return name;
	}

	public String getDeviceId() {
		return deviceId;
	}
	
	public String getListingId() {
		return listingId;
	}
	
	public void setListingId(String id) {
		listingId = id;
	}

}
