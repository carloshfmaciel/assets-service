package com.integrador.assets.mongo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.exception.NotFoundException;
import com.mongodb.client.MongoCollection;

@DisplayName("Writing assertions to AssetRepository")
public class AssetRepositoryTest {

	@InjectMocks
	private AssetRepository assetRepository;

	@Mock
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallInsertMethod() {
		when(mongoTemplate.getCollection(anyString())).thenReturn(mock(MongoCollection.class));
		
		assetRepository.insert(mock(Document.class));
		
		verify(mongoTemplate, times(1)).getCollection(anyString());
		verify(mongoTemplate.getCollection(anyString()), times(1)).insertOne(any(Document.class));
	}

	@Test
	void testCallUpdateMethod() {
		Document document = Document.parse("{\"id\":\"123\"}");
		assetRepository.update(document);
		verify(mongoTemplate, times(1)).replace(any(Query.class), any(Document.class), anyString());
	}

	@Test
	void testCallDeleteAllMethod() {
		when(mongoTemplate.getCollection(anyString())).thenReturn(mock(MongoCollection.class));
		
		assetRepository.deleteAll();
		
		verify(mongoTemplate, times(1)).getCollection(anyString());
		verify(mongoTemplate.getCollection(anyString()), times(1)).deleteMany(any(Document.class));
	}

	@Test
	void whenCallFindByIdInformingExistingIdItMustReturnAssetInstance() {
		String result = new String("{\"id\": 123}");
		when(mongoTemplate.findOne(any(Query.class), any(), anyString())).thenReturn(result);

		Asset asset = assetRepository.findById("123");

		verify(mongoTemplate, times(1)).findOne(any(Query.class), any(), anyString());
		assertNotNull(asset);
	}

	@Test
	void whenCallFindByIdInformingNonExistingIdItMustThrowNotFoundException() {
		when(mongoTemplate.findOne(any(Query.class), any(), anyString())).thenReturn(null);
		
		assertThrows(NotFoundException.class, ()-> {
			assetRepository.findById("123");
		});
	}

	@Test
	void whenCallExistsByIdInformingExistingIdItMustReturnTrue() {
		String result = new String("{\"id\": 123}");
		when(mongoTemplate.findOne(any(Query.class), any(), anyString())).thenReturn(result);

		boolean exists = assetRepository.existsById("123");

		assertTrue(exists);
	}

	@Test
	void whenCallExistsByIdInformingNonExistingIdItMustReturnFalse() {
		when(mongoTemplate.findOne(any(Query.class), any(), anyString())).thenReturn(null);

		boolean exists = assetRepository.existsById("123");

		assertFalse(exists);
	}

	@Test
	void whenCallfindByFiltersInformingOnlyFiltersAndPaginationAsParameterItMustReturnCollection() {
		when(mongoTemplate.find(any(Query.class), any(), anyString())).thenReturn(List.of(new String("{\"id\": 123}")));

		List<Asset> result = assetRepository.findByFilters(mock(Criteria.class), null, 
				null, PageRequest.of(1, 30));

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(mongoTemplate, times(1)).find(any(Query.class), any(), anyString());
	}

	@Test
	void whenCallfindByFiltersInformingOnlyFieldsAndPaginationAsParameterItMustReturnCollection() {
		when(mongoTemplate.find(any(Query.class), any(), anyString())).thenReturn(List.of(new String("{\"id\": 123}")));

		List<Asset> result = assetRepository.findByFilters(null, List.of("fieldOne", "fieldTwo"), 
				null, PageRequest.of(1, 30));

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(mongoTemplate, times(1)).find(any(Query.class), any(), anyString());
	}

	@Test
	void whenCallfindByFiltersInformingOnlyPaginationAsParameterItMustReturnCollection() {
		when(mongoTemplate.find(any(Query.class), any(), anyString())).thenReturn(List.of(new String("{\"id\": 123}")));

		List<Asset> result = assetRepository.findByFilters(null, null, 
				null, PageRequest.of(1, 30));

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(mongoTemplate, times(1)).find(any(Query.class), any(), anyString());
	}

	@Test
	void whenCallfindByFiltersInformingOnlyFiltersForWhichOneThereIsNotResultAsParameterItMustThrowNotFoundException() {
		when(mongoTemplate.find(any(Query.class), any(), anyString())).thenReturn(null);

		assertThrows(NotFoundException.class, ()->{
			assetRepository.findByFilters(mock(Criteria.class), null, 
					Sort.by(Order.asc("someField")), PageRequest.of(1, 30));
		});
		
		verify(mongoTemplate, times(1)).find(any(Query.class), any(), anyString());
	}
	
	@Test
	void whenCallCountByFiltersWithNullCriteriaItMustReturnSomeResultSize() {
		when(mongoTemplate.count(any(Query.class), any(), anyString())).thenReturn(1L);

		int countResultSize = assetRepository.countByFilters(null);
		
		assertEquals(1, countResultSize);
		
		verify(mongoTemplate, times(1)).count(any(Query.class), any(), anyString());
	}
	
	@Test
	void whenCallCountByFiltersInformingSomeCriteriaItMustReturnSomeResultSize() {
		when(mongoTemplate.count(any(Query.class), any(), anyString())).thenReturn(1L);

		int countResultSize = assetRepository.countByFilters(mock(Criteria.class));
		
		assertEquals(1, countResultSize);
		
		verify(mongoTemplate, times(1)).count(any(Query.class), any(), anyString());
	}
}
