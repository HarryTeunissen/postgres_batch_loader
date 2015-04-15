package com.ic.persistence.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.mapping.impl.ObjectMapperServiceImpl;


public class TestObectMapperService {

	private ObjectMapperService svc;
	
	@Before
	public void setup() {
		svc  = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(ObjectMapperService.class).to(ObjectMapperServiceImpl.class) ;
			}
		}).getInstance(ObjectMapperService.class) ;
	}

	@Test
	public void test() throws ObjectMappingException {
		ObjectMapper<TestClass> mapper = svc.bindObject(TestClass.class).build() ;
		
		assertEquals(3, mapper.getAllColumns().size());
		
		assertNotNull(mapper.getTableReference());
		assertEquals("TestTable", mapper.getTableReference().getName()) ;
		
		assertNotNull(mapper.getJoinColumn());
		assertEquals("natural_column", mapper.getJoinColumn().getName());
		
		
	}

	@Table(name = "TestTable")
	public static class TestClass {

		private Integer idColumn;
		private String naturalKeyColumn;
		private String valueColumn;

		@Id
		@Column(name="id")
		public Integer getIdColumn() {
			return idColumn;
		}

		
		public void setIdColumn(Integer idColumn) {
			this.idColumn = idColumn;
		}

		@NaturalId
		@Column(name="natural_column")
		public String getNaturalKeyColumn() {
			return naturalKeyColumn;
		}

		public void setNaturalKeyColumn(String naturalKeyColumn) {
			this.naturalKeyColumn = naturalKeyColumn;
		}

		@Column(name="value_column")
		public String getValueColumn() {
			return valueColumn;
		}

		public void setValueColumn(String valueColumn) {
			this.valueColumn = valueColumn;
		}

	}

}
