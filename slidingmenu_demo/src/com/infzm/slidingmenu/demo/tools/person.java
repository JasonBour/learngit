package com.infzm.slidingmenu.demo.tools;

	public class person {
		 private Integer id;
		 private  String name;
		 private String phone;
		 private String address; 
		 @Override
		 public String toString(){
			return "{name="+name+",phone="+phone+",address="+address+"}";
			}
		 
		 public person(Integer id) {

			this.id = id;
		}
		public person(String name, String phone) {

			this.name = name;
			this.phone = phone;
		}
		public person(){}
		public person( String name, String phone,String address) {

			this.address=address;
			this.name = name;
			this.phone = phone;
		}
		
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setAddress(String address){
			
			this.address= address;
		}
		public String getAddress(){
			
			
			return address ;
		}
}
