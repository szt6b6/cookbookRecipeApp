package entity;

public class Ingredient {
	private String  nameIng;
	private String  amountIng;//only for 1 person
	private String  unitIng;
	private String  descriptionIng;
	private int serverAmountIng = 1;//set default equals to 1
	private String totalAmountIng;

	public Ingredient(String name, String description, String amount, String unit, int serverAmount) {
		this.nameIng = name;
		this.amountIng = String.valueOf(Integer.valueOf(amount) / serverAmount);
		this.unitIng = unit;
		this.descriptionIng = description;
		this.serverAmountIng = serverAmount;
		this.totalAmountIng = amount;
	}

	public String getNameIng() {
		return nameIng;
	}

	public void setNameIng(String nameIng) {
		this.nameIng = nameIng;
	}

	public String getAmountIng() {
		return amountIng;
	}

	public void setAmountIng(String amountIng) {
		this.amountIng = amountIng;
	}

	public String getUnitIng() {
		return unitIng;
	}

	public void setUnitIng(String unitIng) {
		this.unitIng = unitIng;
	}

	public String getDescriptionIng() {
		return descriptionIng;
	}

	public void setDescriptionIng(String descriptionIng) {
		this.descriptionIng = descriptionIng;
	}

	public int getServerAmount() {
		return serverAmountIng;
	}

	public void setServerAmount(int serverAmount) {
		this.serverAmountIng = serverAmount;
	}

	public String getTotalAmountIng() {
		return totalAmountIng;
	}

	public void setTotalAmountIng(String totalAmountIng) {
		this.totalAmountIng = totalAmountIng;
	}

	public void setFieldValue(Double newValue) {
		// TODO Auto-generated method stub
		
	}



	
}
