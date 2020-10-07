 package br.mat.mmgalvao.common.aleph.dao;
import br.mat.mmgalvao.common.aleph.Column;
import br.mat.mmgalvao.common.aleph.ColumnInfo;
import br.mat.mmgalvao.common.aleph.DataSet;
import java.io.Serializable;
 import java.io.UnsupportedEncodingException;
 import java.math.BigInteger;
 import java.sql.Connection;
 import java.sql.DatabaseMetaData;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;

 import java.util.ArrayList;
 import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
 
 public class DbServerDAO {
             DatabaseMetaData metada=null;
   public DbServerDAO(Connection con) {
     this.con = con;
                 try {
                     this.metada=con.getMetaData();
                 } catch (SQLException ex) {
                     Logger.getLogger(DbServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                 }
   }

    public String getDatabaseProductName() {
        try{
            return metada.getDatabaseProductName();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage(),ex);
        }
    }

    public String getDatabaseProductVersion()  {
        try{    
        return metada.getDatabaseProductVersion();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage(),ex);
        }
    }

    public DataSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)  {
        DataSet dt = new DataSet();
        try {
        
        getData(metada.getTables(catalog, schemaPattern, tableNamePattern, types),dt);
        return dt;
        }
        catch(SQLException sqle){
            
     
                  
                 System.out.println(String.valueOf(sqle.getErrorCode()));
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
      
        }
        return dt;
    }
    public DataSet getImportedKeys(String catalog, String schema, String table) {
        DataSet dt = new DataSet();
        try {
        
        getData(metada.getImportedKeys(catalog, schema, table),dt);
        return dt;
        }
        catch(SQLException sqle){
            
     
                  
                 System.out.println(String.valueOf(sqle.getErrorCode()));
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
      
        } 
        return dt;
       
    }

    public  DataSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)  {
       
         DataSet dt = new DataSet();
        try {
        
            getData(metada.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern),dt);
            return dt;
        }
        catch(SQLException sqle){
            
     
                  
                 System.out.println(String.valueOf(sqle.getErrorCode()));
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
      
        } 
        return dt;
    }

    public DataSet getExportedKeys(String catalog, String schema, String table){
        DataSet dt = new DataSet();
        try {
        
        getData(metada.getExportedKeys(catalog, schema, table),dt);
        
        }
        catch(SQLException sqle){
            
     
                  sqle.printStackTrace();
                 System.out.println(String.valueOf(sqle.getErrorCode()));
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
      
        } 
       return dt;
    }

    public DataSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) {
        DataSet dt = new DataSet();
        try {
        
        getData(metada.getIndexInfo(catalog, schema, table, unique, approximate),dt);
        
        }
        catch(SQLException sqle){
            
     
                  
                 System.out.println(String.valueOf(sqle.getErrorCode()));
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
      
        } 
       return dt;
        
    }
    
   
    Connection con;
    public void close(){
                 try {   
                    
                     if(this.con!=null){
                         
                        this.con.close();
                        this.con=null;
                     }
                    
                 } catch (SQLException ex) {
                     Logger.getLogger(DbServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                 }
    }
   public List<ColumnInfo> getMetadataTable(String db, String schema, String table) throws SQLException {
     List<ColumnInfo> result = new ArrayList<>();
     ResultSet rs = this.con.getMetaData().getColumns(db, 
         schema, 
         table, 
         "%");
     getCollumnInfo(rs, result);
     return result;
   }
   private void getCollumnInfo(ResultSet rs, List<ColumnInfo> result) throws SQLException {
     while (rs.next()) {
       result.add(analiseColmun(rs, result));
     }
   }
 
   
   private ColumnInfo analiseColmun(ResultSet rs, List<ColumnInfo> result) {
     return null;
   }
   
   public DataSet execute(String query, Object... params) {
     DataSet dt = new DataSet();
              PreparedStatement st=null;
     try {
       st = this.con.prepareCall(query);
       createParams(st, params);
       if (st.execute()) {
         getData(st.getResultSet(), dt);
       }
       dt.setSucess(true);
               // this.con.commit();
       this.con.commit();
     }
     catch (SQLException sqle) {
                  
                 System.out.println(String.valueOf(sqle.getErrorCode()) + query);
       System.out.println(sqle.getMessage());
       dt.setSucess(false);
       dt.setErrorCode(sqle.getErrorCode());
       try {
		this.con.rollback();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     } 
finally{
    try {
   if( st.getResultSet()!=null) {
       
           st.getResultSet().close();
       
   }
    
        st.close();
    } catch (SQLException ex) {
        Logger.getLogger(DbServerDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 
     
     return dt;
   }

   private void createParams(PreparedStatement st, Object[] params) throws SQLException {
     int i = 1; 
     for (Object p:params) { 
       st.setObject(i++, p);
      }
   
   }

   
   private void getData(ResultSet rs, DataSet dt) throws SQLException {
     
     getMetadata(rs.getMetaData(), dt);
     getData(rs, rs.getMetaData(), dt);
   }
 
   
   private void getMetadata(ResultSetMetaData metaData, DataSet dt) throws SQLException {
       Column [] columns=new Column[ metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
       columns[i-1]=crateColumnInfo(metaData, i);
     }
                dt.setColumns(columns);
   }
   
   private Column crateColumnInfo(ResultSetMetaData d, int i) throws SQLException {
     Column col = new Column();
     col.setNullAble(d.isNullable(i));
     col.setPrecsion(d.getPrecision(i));
     col.setScale(d.getScale(i));
     col.setPos(i);
     col.setMaxLength(d.getColumnDisplaySize(i));
     col.setName(d.getColumnName(i));
     col.setTypeName(d.getColumnClassName(i));
     return col;
   }
 
   
   private void getData(ResultSet rs, ResultSetMetaData d, DataSet dt) throws SQLException {
     List<Object[]> ls = new ArrayList<>();
     
     while (rs.next()) {
       Object[] ls1 =new Object[ d.getColumnCount()];
       for (int i = 1; i <= d.getColumnCount(); i++) {
         Object obj = rs.getObject(i);
         if (obj instanceof String) {
           String str1 = (String)obj;
           obj = str1.trim();
           if (str1.startsWith("!#SERIAL#!")) {
             int l = "!#SERIAL#!       2164800000000000000014E490000000000000002430000000000000050".length();
             try {
               obj = (new String((new BigInteger(str1.substring(l), 16)).toByteArray(), "ISO-8859-1")).trim();
             } catch (UnsupportedEncodingException e) {
               
               e.printStackTrace();
             } 
           } 
         } 

                  //System.out.println(i+"-"+d.getColumnCount()+"-"+obj.getClass());
         ls1[i-1]=obj;
       }  
                ls.add(ls1);
     } 

                dt.setData(ls.toArray(new Object[0][]));
   }
   
   public static void main(String[] args) throws SQLException {
     Connection conn = null;
     ResultSet rs = null;
     String url = "jdbc:sqlserver://127.0.0.1:1433;database=Desenv_RH;integratedSecurity=true";
     String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
     String userName = "sa";
     String password	 = "gabi3010";
try {       Class.forName(driver);
       conn = DriverManager.getConnection(url,userName,password);
       
                DbServerDAO dao=new DbServerDAO(conn);
                System.out.println(dao.getExportedKeys("Desenv_RH","dbo","USUARIOS"));
       /*System.out.println((new DbServerDAO(conn)).execute( "SELECT top 1 REFERENCIA,\r\n" + 
    					"      DOMINIO\r\n" + 
    					"	  ,FIELD_NAME\r\n" + 
    					"      ,FIELD_TYPE\r\n" + 
    					"      ,FIELD_LEN\r\n" + 
    					"      ,FIELD_DEC\r\n" + 
    					"      ,DESCRICAO\r\n" + 
    					"      ,DESCR_TELA\r\n" + 
    					"      ,MASCARA\r\n" + 
    					"      ,VALIDACAO\r\n" +   			 
    					"      ,CHAVE\r\n" + 
    					"      ,PRE_PUT\r\n" + 
    					"      ,POS_GET\r\n" + 
    					"      ,[UNIQUE]\r\n" + 
    					"      ,NOTNULL\r\n" + 
    					"      ,EXPR_DEFAU\r\n" + 
    					"      ,HELP\r\n" + 
    					"     \r\n" + 
    					"      \r\n" + 
    					"  FROM dbo.PRODIGY_CAMPOS where  [unique] is not null", new Object[0]));
       /*
       DatabaseMetaData dbm = conn.getMetaData();
      System.out.println(0);
      System.out.println(1);
       System.out.println(2);
       System.out.println(dbm.getDatabaseProductName());
      System.out.println(NumberFormat.getInstance(new Locale("pt", "BR")));
    System.out.println(dbm.getDatabaseProductVersion());
      rs = dbm.getIndexInfo(null, "dbo", "POSICAO", false, false);
       ResultSetMetaData rsmd = rs.getMetaData();
/       int cols = rsmd.getColumnCount();
       while (rs.next()) {
         for (int i = 1; i <= cols; i++) {
          System.out.println(String.valueOf(rsmd.getColumnName(i)) + "=" + rs.getObject(i));
        }
        System.out.println("_________________________________");
     }

       rs.close();*/
     }
    catch (com.microsoft.sqlserver.jdbc.SQLServerException e) {
     e.printStackTrace();
 
     System.out.println(e.getErrorCode());
     System.out.println(e.getSQLState());
       System.out.println(e.getClass());
    
     }
    catch (Exception ex) {
       ex.printStackTrace();
    } 
     
    if (conn != null) {
      conn.close();
      if (rs != null)
        rs.close(); 
     } 
  }
}

