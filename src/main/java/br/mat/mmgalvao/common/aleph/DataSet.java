 package br.mat.mmgalvao.common.aleph;
import java.io.Serializable;
 import java.sql.Date;
 import java.util.HashMap;
 
 public class DataSet implements Serializable {
   private static final long serialVersionUID = 1L;
   boolean sucess;
   String errorMessage;
   int postAtual = -1; int errorCode;
   public int getPosAtual() {
     return this.postAtual;
   } Column[] columns;
   public void reset() {
     this.postAtual = -1;
   }
   public String toString() {
     String str = "";
     
     for (Object[] str1 : this.data) {
       int i = 0;
       for (Column col : this.columns)
       {
         str = str + col.getName() + "=" + String.valueOf(str1[i++]) + "\n";
       }
       
       str = str + "\n";
       
       str = str + "--------------------\n";
     } 
     return str;
   }
 
 
 
 
 
   
   HashMap<String, Integer> campos = new HashMap<>(); Object[][] data;
   
   public Column[] getColumns() {
     return this.columns;
   }
   
   public void setColumns(Column[] collumns) {
     this.columns = collumns;
     createRefs();
   }
   public void createRefs() {
     int i = 0;
     
     for (Column col : this.columns)
     {
       this.campos.put(col.getName().toLowerCase(), Integer.valueOf(i++)); } 
   }
   
   public Object getValue(int i) {
     return this.data[this.postAtual][i];
   }
   public Integer getIntValue(int i) {
     Object s = getValue(i);
     if (s == null) return 0; 
     if (s instanceof Number) {
       return Integer.valueOf(((Number)s).intValue());
     }
     throw new RuntimeException("tipo nao é numerico");
   }
   
   public Long getLongValue(int i) {
     Object s = getValue(i);
     if (s == null) return 0L; 
     if (s instanceof Number) {
       return Long.valueOf(((Number)s).longValue());
     }
     throw new RuntimeException("tipo nao é numerico");
   }
   
   public Double getDoubleValue(int i) {
     Object s = getValue(i);
     if (s == null) return 0.0; 
     if (s instanceof Number) {
       return Double.valueOf(((Number)s).doubleValue());
     }
     throw new RuntimeException("tipo nao é numerico");
   }
   
   public Date getDateValue(int i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Date) {
       return (Date)s;
     }
     throw new RuntimeException("tipo nao é date");
   }
   public String getStringValue(int i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof String) {
       return (String)s;
     }
     throw new RuntimeException("tipo nao é string");
   }
   public Boolean getBoleanValue(int i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Boolean) {
       return Boolean.valueOf(((Boolean)s).booleanValue());
     }
     throw new RuntimeException("tipo nao é boolean");
   }
   public Object getValue(String name) {
     Integer s = this.campos.get(name.toLowerCase());
     if (s != null) {
       return getValue(s.intValue());
     }
     throw new RuntimeException("Field not find  " + name);
   }
   
   public Long getLongValue(String i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Number) {
       return Long.valueOf(((Number)s).longValue());
     }
     throw new RuntimeException("tipo nao é numerico");
   }
   
   public Double getDoubleValue(String i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Number) {
       return Double.valueOf(((Number)s).doubleValue());
     }
     throw new RuntimeException("tipo nao é numerico");
   }
   
   public Date getDateValue(String i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Date) {
       return (Date)s;
     }
     throw new RuntimeException("tipo nao é date");
   }
   public String getStringValue(String i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof String) {
       return (String)s;
     }
     throw new RuntimeException("tipo nao é string");
   }
   public Boolean getBoleanValue(String i) {
     Object s = getValue(i);
     if (s == null) return null; 
     if (s instanceof Boolean) {
       return Boolean.valueOf(((Boolean)s).booleanValue());
     }
     throw new RuntimeException("tipo nao é boolean");
   }
   
   public Object[][] getData() {
     return this.data;
   }
   
   public void setData(Object[][] data) {
     this.data = data;
   }
   public boolean isSucess() {
     return this.sucess;
   }
   public void setSucess(boolean sucess) {
     this.sucess = sucess;
   }
   public String getErrorMessage() {
     return this.errorMessage;
   }
   public void setErrorMessage(String errorMessage) {
     this.errorMessage = errorMessage;
   }
   public int getErrorCode() {
     return this.errorCode;
   }
   public void setErrorCode(int errorCode) {
     this.errorCode = errorCode;
   }
   
   public boolean next() {
     if (this.postAtual < (getData()).length - 1) {
       if (this.campos == null || this.campos.size() == 0) {
         if (this.campos == null) this.campos = new HashMap<>(); 
         createRefs();
       } 
       this.postAtual++;
       
       return true;
     } 
     return false;
   }
 }

