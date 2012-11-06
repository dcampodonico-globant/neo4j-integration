package org.dcampodonico.neo4j.integration;

import static org.apache.commons.lang3.Validate.notNull;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

public class Neo4jTemplate {
  private Index<Node> index;
  private String idPropertyName = "id";

  public Neo4jTemplate(Index<Node> index) {
    this.index = notNull(index, "The index cannot be null");
  }

  public String getIdPropertyName() {
    return idPropertyName;
  }

  public void setIdPropertyName(String idPropertyName) {
    this.idPropertyName = idPropertyName;
  }

  public <T> T getObjectById(String value, ObjectMapper<T> mapper) {
    return mapper.mapNode(index.get(idPropertyName, value).getSingle());
  }

  public <T> void saveObject(T object, ObjectMapper<T> mapper) {
    GraphDatabaseService graphDB = index.getGraphDatabase();
    Transaction tx = graphDB.beginTx();
    try {
      Node referenceNode = graphDB.getReferenceNode();
      Node objectNode = graphDB.createNode();
      mapper.mapObject(object, objectNode);
      referenceNode.createRelationshipTo(objectNode, RelTypes.ROOT_REFERENCE);
      index.add(objectNode, idPropertyName, objectNode.getProperty(idPropertyName));
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private static enum RelTypes implements RelationshipType
  {
    ROOT_REFERENCE
  }
}
