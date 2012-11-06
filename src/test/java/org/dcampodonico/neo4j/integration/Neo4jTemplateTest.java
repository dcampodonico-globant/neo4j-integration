package org.dcampodonico.neo4j.integration;




import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

public class Neo4jTemplateTest {

  @SuppressWarnings("unchecked")
  @Test
  public void testTemplateMapsTheNode() {
    Index<Node> index = mock(Index.class);
    Neo4jTemplate template = new Neo4jTemplate(index);
    ObjectMapper<Object> mapper = mock(ObjectMapper.class);
    IndexHits<Node> hits = mock(IndexHits.class);;
    when(index.get("id", "123")).thenReturn(hits );
    Node node = mock(Node.class);;
    when(hits.getSingle()).thenReturn(node );
    when(mapper.mapNode(node)).thenReturn(new Object());
    template.getObjectById("123", mapper );
    verify(mapper).mapNode(node);
  }


  @SuppressWarnings("unchecked")
  @Test
  public void testTemplateGetNodeFromIndex() {
    Index<Node> index = mock(Index.class);
    Neo4jTemplate template = new Neo4jTemplate(index);
    ObjectMapper<Object> mapper = mock(ObjectMapper.class);
    IndexHits<Node> hits = mock(IndexHits.class);;
    when(index.get("id", "123")).thenReturn(hits );
    Node node = mock(Node.class);;
    when(hits.getSingle()).thenReturn(node );
    when(mapper.mapNode(node)).thenReturn(new Object());
    template.getObjectById("123", mapper );
    verify(index).get("id", "123");
  }
}
