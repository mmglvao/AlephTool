package br.mat.mmgalvao.aleph.dbserver.interfaces;

import java.rmi.RemoteException;

import br.mat.mmgalvao.common.aleph.DataSet;

public interface DbServerGeneric {
         public void crateConnection(String datasource);
         public DataSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types);
         public String getDatabaseProductVersion();
        public String getDatabaseProductName();
         public DataSet getImportedKeys(String catalog, String schema, String table);
         public  DataSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) ;
	public DataSet execute(String query,Object...params) ;
        public DataSet getExportedKeys(String catalog, String schema, String table);
        public DataSet getIndexInfo(String datasource,String catalog, String schema, String table, boolean unique, boolean approximate);
        public void close();
        
}
