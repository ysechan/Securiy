/*
 * package com.project.repository;
 * 
 * import java.time.LocalDateTime; import java.time.format.DateTimeFormatter;
 * import java.util.List; import java.util.stream.Collectors;
 * 
 * import org.springframework.data.domain.PageRequest; import
 * org.springframework.data.elasticsearch.core.ElasticsearchOperations; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.elasticsearch.core.mapping.IndexCoordinates; import
 * org.springframework.data.elasticsearch.core.query.Query; import
 * org.springframework.data.elasticsearch.core.query.StringQuery; import
 * org.springframework.stereotype.Component;
 * 
 * import com.project.Entity.ElasticEntity;
 * 
 * @Component public class ElasticRepoCustomImpl implements ElasticRepoCustom {
 * 
 * @Autowired private ElasticsearchOperations elasticsearchOperations; // Spring
 * Boot 3.X 에서는 ElasticsearchRestTemplate 빈이 자동으로 등록되지 않는 경우가 있어서, // 대신에 같은 기능을
 * 수행하는 ElasticsearchOperations 를 사용!
 * 
 * // 기본값
 * 
 * @Override public List<ElasticEntity> findAllByIndexPattern(String
 * indexPattern) { String queryJson =
 * "{ \"range\" : { \"time\" : { \"gte\" : \"now+9h-10m\", \"lte\" : \"now+9h\", \"format\" : \"yyyy-MM-dd'T'HH:mm:ss.SSS\"}}}"
 * ;
 * 
 * Query query = new StringQuery(queryJson); query.setPageable(PageRequest.of(0,
 * 500)); // size를 설정하여 최대 10000개 조회
 * 
 * return elasticsearchOperations.search( query, ElasticEntity.class,
 * IndexCoordinates.of(indexPattern) ).map(searchHit ->
 * searchHit.getContent()).toList(); }
 * 
 * // 날짜 설정 버전
 * 
 * @Override public List<ElasticEntity> findAllByDatetime(String choiceDate,
 * String choiceDateEnd) { // choiceDate를 LocalDateTime으로 파싱 LocalDateTime
 * startDate = LocalDateTime.parse(choiceDate,
 * DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); LocalDateTime endDate =
 * LocalDateTime.parse(choiceDateEnd,
 * DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
 * 
 * // 새로운 포맷으로 변환 String formattedStartDate =
 * startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
 * String formattedEndDate =
 * endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
 * 
 * String queryJson = String.
 * format("{ \"range\" : { \"time\" : { \"gte\" : \"%s\", \"lte\" : \"%s\", \"format\" : \"yyyy-MM-dd'T'HH:mm:ss.SSS\"}}}}"
 * , formattedStartDate, formattedEndDate);
 * 
 * Query query = new StringQuery(queryJson); query.setPageable(PageRequest.of(0,
 * 500)); // size를 설정하여 최대 10000개 조회
 * 
 * return elasticsearchOperations.search( query, ElasticEntity.class,
 * IndexCoordinates.of("last_log-*") ).map(searchHit ->
 * searchHit.getContent()).toList(); } }
 */