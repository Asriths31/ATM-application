package atm;
import java.sql.*;
import java.util.*;



public class atm {
  public static void main(String[] args) throws Exception {

    int ch=welpage();
    if (ch == 1) {
      while (true) {
        try {
          operation();
        } catch (Exception e) {
          System.out.println("enter the correct acc no");
        }
      }
    } else if (ch == 2) {
      addaccount();
    }
  else if(ch==3){
    System.out.println("Thanks for Visiting");
    System.out.println("------------------------------");
    System.exit(0);
  }}
  
    static int welpage(){
           while(true) {Scanner sc = new Scanner(System.in);
    System.out.println("Enter the Choice");
    System.out.println("1.Banking");
    System.out.println("2.New Bank Account");
    System.out.println("3.Exit");
    int ch = sc.nextInt();
    return ch;}

    }

  public static void addaccount() throws Exception {
    String url = "jdbc:mysql://localhost:3306/dbms";
    String user = "root";
    String password = "admin";
    Connection con = DriverManager.getConnection(url, user, password);
    String query = "insert into holders(pin,full_name,balance) values(?,?,?)";

    Scanner sc = new Scanner(System.in);
    System.out.println("-----Welcome To Our Bank-----");
    System.out.println("Enter your Name");
    String name = sc.nextLine();
    System.out.println("Set The pin");
    int pin = sc.nextInt();
    System.out.println("deposit some amount");
    int amt = sc.nextInt();
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, pin);
    st.setString(2, name);
    st.setInt(3, amt);
    st.executeUpdate();
    String query1="select acc_no from holders where full_name=? and pin =?";
    PreparedStatement st1=con.prepareStatement(query1);
    st1.setString(1, name); 
    st1.setInt(2,pin);
    ResultSet rs=st1.executeQuery();
    rs.next();
    int acc_no=rs.getInt("acc_no");
    System.out.println("Your Account Number is"+":"+acc_no);
    System.out.println("----------------------------------");

  }

  public static void getdetails(String name) {
    System.out.println("HI!" + "  "+name);
  }

  public static void getmenu() {
    System.out.println("------------Select Any Option-------------");
    System.out.println("1.withdrawl");
    System.out.println("2.Deposit");
    System.out.println("3.Balance Enquiry");
    // System.out.println("4.Add Account");
    System.out.println("4.Exit");

  }

  public static int options(int opt, int bal, int acc) throws Exception {
    Scanner sc = new Scanner(System.in);
    String url = "jdbc:mysql://localhost:3306/dbms";
    String user = "root";
    String password = "admin";
    Connection con = DriverManager.getConnection(url, user, password);

    if (opt == 1) {
      System.out.println("Enter the amount");
      int amt = sc.nextInt();
      if (bal >= amt) {
        bal = bal - amt;
        System.out.println("Tramsaction completed");
        System.out.println("------------------------------------");

      } else {
        System.out.println("Insufficient Funds");
        System.out.println("------------------------------------");

      }

    } else if (opt == 2) {
      System.out.println("Enter the amount to be Deposited");
      int amt = sc.nextInt();
      bal = bal + amt;

    } else if (opt == 3) {
      System.out.println("Amount in your Account is" + " " + bal);
      System.out.println("------------------------------------");
    }
    // else if(opt==4){
    // addaccount();
    // }
    else if (opt == 4) {
       System.out.println("Thanks for Visiting");
    System.out.println("------------------------------");
      System.exit(0);
    }
    String query1 = "update holders set balance=? where acc_no=?";
    PreparedStatement st1 = con.prepareStatement(query1);
    st1.setInt(1, bal);
    st1.setInt(2, acc);
    st1.executeUpdate();
    // System.out.println(bal);2345
    return bal;
  }

  static void operation() throws Exception {
    String url = "jdbc:mysql://localhost:3306/dbms";
    String user = "root";
    String password = "admin";
    Scanner sc = new Scanner(System.in);
    System.out.println("enter the account number");
    int acc = sc.nextInt();

    Connection con = DriverManager.getConnection(url, user, password);
    String query = "select * from holders where acc_no=?";
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, acc);
    ResultSet rs = st.executeQuery();
    rs.next();
    int opin = rs.getInt("pin");
    String name = rs.getString("full_name");
    int balance = rs.getInt("balance");
    System.out.println("enter the pin");
    int epin = sc.nextInt();
    while (true) {
      if (epin == opin) {
        // String name=rs.getString(4);
        // int balance=rs.getInt("balance");
        getdetails(name);
        getmenu();
        System.out.println("enter the option");
        int opt = sc.nextInt();
        balance = options(opt, balance, acc);
        // String query1="update holders set balance=? where acc_no="+acc;
        // PreparedStatement st1=con.prepareStatement(query1);
        // st1.setInt(1, rem_bal);
        // st1.executeUpdate();
        st.close();
      } else {
        System.out.println("enter the correct pin");
        epin=sc.nextInt();
      }
      con.close();

    }

  }
}