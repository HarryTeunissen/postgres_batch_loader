package com.ic.persistence.mapping;


/**
 * Abstract TableReference.
 * @author HTeunissen
 *
 */
public class TableReference {

	private final transient String tableName ;
	private final transient String qualifiedName ;
	
	/**
	 * 
	 * @param schemaName
	 * @param tableName
	 */
	public TableReference(String schemaName, String tableName) {
		super();
		this.tableName = tableName;
		this.qualifiedName = (schemaName == null || schemaName.equals("") ) ? tableName : schemaName + "." + tableName ;
	}

	/**
	 * 
	 * @return Table name
	 */
	public String getName() {
		return tableName ;
	}
	
	/**
	 * 
	 * @return fully qualified Table Name
	 */
	public String getQualifiedName() {
		return qualifiedName ;
	}
	
	/**
	 * 
	 * @return a temporary TableReference
	 */
	public TableReference createTempReference() {
		return new TableReference(null, tableName + "_temp") ;
	}

	
}
