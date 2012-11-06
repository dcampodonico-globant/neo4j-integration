package org.dcampodonico.neo4j.integration;

import org.neo4j.graphdb.Node;

public interface ObjectMapper<T> {

  public T mapNode(Node aNode);

  public void mapObject(T object, Node objectNode);
}
