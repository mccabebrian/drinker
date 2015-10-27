package com.brianmccabe.yournews;

public class Classifieds {

	public String title, description, contact, price, town, location, image;
	
	
	public Classifieds(){
		
	}
	
	public Classifieds(String title, String description, String contact,
			String price, String town, String location, String image) {
		this.title = title;
		this.description = description;
		this.contact = contact;
		this.price = price;
		this.town = town;
		this.location = location;
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	} 
	
}
