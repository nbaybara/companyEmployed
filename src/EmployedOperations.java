
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

 
public class EmployedOperations {
     private Connection con=null;
     private Statement statement= null;
     private PreparedStatement preparedStatement=null;
     
     public void deleteEmployee(int id){
         
         String query ="DELETE from calisanlar where id = ?";
         try {
             preparedStatement = con.prepareStatement(query);
             preparedStatement.setInt(1,id);
             preparedStatement.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(EmployedOperations.class.getName()).log(Level.SEVERE, null, ex);
         }

         
     }
     
     public void updateEmployee(int id, String new_name,String new_surname,String new_dep,String new_salary){
    String query ="Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
   
         try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,new_name);
            preparedStatement.setString(2,new_surname);
            preparedStatement.setString(3,new_dep);
            preparedStatement.setString(4,new_salary);
            preparedStatement.setInt(5,id);
            preparedStatement.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(EmployedOperations.class.getName()).log(Level.SEVERE, null, ex);
             
         }
             
     }
     
     public void addNewEmp(String name,String surname,String department,String salary){
         String query ="Insert into calisanlar(ad,soyad,departman,maas) VALUES(?,?,?,?)";
         try {
             preparedStatement = con.prepareStatement(query);
             preparedStatement.setString(1,name);
             preparedStatement.setString(2,surname);
             preparedStatement.setString(3,department);
             preparedStatement.setString(4,salary);
             preparedStatement.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(EmployedOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     
     public ArrayList<Employee> getEmployeed(){
     
         ArrayList<Employee> output = new ArrayList<Employee>();
         try {
             statement = con.createStatement();
             String query="Select * From calisanlar";
             ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 int id=rs.getInt("id");
                 String name=rs.getString("ad");
                 String surname=rs.getString("soyad");
                 String department=rs.getString("departman");
                 String salary=rs.getString("maas");
                 output.add(new Employee(id,name,surname,department,salary));
                 
             }
             return output;
         } catch (SQLException ex) {
             Logger.getLogger(EmployedOperations.class.getName()).log(Level.SEVERE, null, ex);
             return null;
         }
         
     }
     
     public boolean login(String username, String password){
         String query ="Select * From  adminler where username = ? and password = ?";
         try {
             preparedStatement = con.prepareStatement(query);
             preparedStatement.setString(1,username);
             preparedStatement.setString(2,password);
             ResultSet rs=preparedStatement.executeQuery();
             
             //if(rs.next()==false){return false;}else{return true;}
             return rs.next();
             
         } catch (SQLException ex) {
             Logger.getLogger(EmployedOperations.class.getName()).log(Level.SEVERE, null, ex);
             return false;

         }
      
     }

    public EmployedOperations(){
        String url="jdbc:mysql://"+Database.host+":"+ Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=utf8";
        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }


        try {
            con = DriverManager.getConnection(url, Database.username, Database.password);
            System.out.println("Bağlantı Başarılı...");


        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }
    }
    public  static void main(String[] args){
        EmployedOperations Operations = new EmployedOperations();
    }


    
}
