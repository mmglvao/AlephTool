package br.mat.mmgalvao.aleph.dbserver;
import br.mat.mmgalvao.aleph.dbserver.interfaces.DbServerLocal;
import br.mat.mmgalvao.aleph.dbserver.interfaces.DbServerRemote;
import br.mat.mmgalvao.common.aleph.DataSet;
import br.mat.mmgalvao.common.aleph.dao.DbServerDAO;
import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Session Bean implementation class DbServer
 */

@Stateful(mappedName = "ejb/DbServer")
@TransactionManagement(value=TransactionManagementType.BEAN)
public class DbServer implements DbServerRemote, DbServerLocal {
     static HashMap<String,DataSource> maps=new HashMap<>();
     DbServerDAO dao=null;
     static int count=0;
     @Override
     public void crateConnection(String datasource) {
     
     Connection con;
         try{
         if(dao==null){
            DataSource ds=getDataSource1(datasource);            
            con=ds.getConnection();
            con.setAutoCommit(false);
            dao=new DbServerDAO(con);
           
         }
         }
         catch(SQLException | NamingException  ex){
             throw new RuntimeException(ex.getMessage(),ex);
         }
     }
     @Override
    public DataSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
       
                 
                
                DataSet ds1;
		
		
                     
                    
                        ds1 = dao.getTables(catalog, schemaPattern, tableNamePattern, types);
                    return ds1;
                
	
        
    }

     @Override
    public DataSet getImportedKeys(String catalog, String schema, String table) {
                DataSet ds1;
            
		
                    ds1 = dao.getImportedKeys(catalog, schema, table);
                    return ds1;
		
	
    }

     @Override
    public DataSet getExportedKeys(String catalog, String schema, String table){
       
                 DataSet ds1;
		
	             ds1 = dao.getExportedKeys(catalog, schema, table);
                    return ds1;
		
    }

     @Override
    public DataSet getIndexInfo(String datasource,String catalog, String schema, String table, boolean unique, boolean approximate) {
                 DataSet ds1;
		
		
                    ds1 = dao.getIndexInfo(catalog, schema, table, unique, approximate);
                    return ds1;
    }
    /**
     * Default constructor. 
     */
    public DbServer() {
        // TODO Auto-generated constructor stub
    }
        public static DataSource getDataSource1(String resourceName) throws NamingException{
            DataSource ds=maps.get(resourceName);
             InitialContext ctx;
            if(ds==null){	
                System.out.println("criei novo");
                ctx = new InitialContext();
            
                
                ds = (DataSource)ctx.lookup(resourceName);

                maps.put(resourceName, ds);
            
            }
            return ds;
        }
	@Override
	public DataSet execute(String query, Object[] params){
	
                 DataSet ds1;
	
		
                    ds1 = dao.execute(query,params);
                    return ds1;
		
	}

    @Override
    public String getDatabaseProductVersion() {
       return dao.getDatabaseProductVersion();
    }

    @Override
    public String getDatabaseProductName() {
        return dao.getDatabaseProductName();
    }
    @Remove(retainIfException=false)
    @Override
    public void close() {
        dao.close();
    }

    @Override
    public DataSet getColumns(String cat, String schema, String table, String col) {
    return dao.getColumns(cat,schema, table, col);
    }

  
        

}
