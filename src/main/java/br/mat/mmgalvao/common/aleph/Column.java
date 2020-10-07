 package br.mat.mmgalvao.common.aleph;
 
 import java.io.Serializable;
 
 public class Column implements Serializable {
   private static final long serialVersionUID = 1L;
   String name;
   int pos;
   boolean pk;
   int maxLength;
   int scale;
   int precsion;
   int nullAble;
   String typeName;
   boolean autogerado;
   
   public boolean isPk() {
     return this.pk;
   }
   public void setPk(boolean pk) {
     this.pk = pk;
   }
   
   public int getNullAble() {
     return this.nullAble;
   }
   
   public String getName() {
     return this.name;
   }
   public void setName(String name) {
     this.name = name;
   }
   public int getPos() {
     return this.pos;
   }
   public void setPos(int pos) {
     this.pos = pos;
   }
   public int getMaxLength() {
     return this.maxLength;
   }
   public void setMaxLength(int maxLength) {
     this.maxLength = maxLength;
   }
   public int getScale() {
     return this.scale;
   }
   public void setScale(int scale) {
     this.scale = scale;
   }
   public int getPrecsion() {
     return this.precsion;
   }
   public void setPrecsion(int precsion) {
     this.precsion = precsion;
   }
   public int isNullAble() {
     return this.nullAble;
   }
   public void setNullAble(int nullAble) {
     this.nullAble = nullAble;
   }
   public boolean isAutogerado() {
     return this.autogerado;
   }
   public void setAutogerado(boolean autogerado) {
     this.autogerado = autogerado;
   }
   public String getTypeName() {
     return this.typeName;
   }
   public void setTypeName(String typeName) {
     this.typeName = typeName;
   }
   
   public String toString() {
     return "Column [name=" + this.name + ", pos=" + this.pos + ", pk=" + this.pk + ", maxLength=" + this.maxLength + ", scale=" + this.scale + ", precsion=" + this.precsion + ", nullAble=" + this.nullAble + ", typeName=" + this.typeName + ", autogerado=" + this.autogerado + "]";
   }
 }
