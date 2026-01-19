public class Customer extends User{
    private String customerID;


                                                                                            //Constructors--------------
    public Customer(){
        // No-arg constructor
    }

    public Customer(String userName, String password, String email, String customerID){ //Customer registration constructor
        super(userName, password, email);
        this.customerID = customerID;
    }

                                                                                            //Getters-------------------

    public String getCustomerID(){
        return customerID;
    }


                                                                                            //Setters-------------------

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    @Override
    public String toString(){
        return super.toString() + " Customer ID: " + customerID;
    }

}
