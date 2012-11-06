package org.dcampodonico.neo4j.integration;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.impl.util.FileUtils;

public class Neo4jTemplateIntegrationTest {

  @Test
  public void testGetObjectRetrivesTheRequestedObject() throws IOException {
    GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase( "target/neo4j/db" );
    Transaction tx = graphDB.beginTx();
    Index<Node> index = graphDB.index().forNodes("test");
    try{
      Node userNode = graphDB.createNode();
      userNode.setProperty("id", "123");
      userNode.setProperty("name", "john");
      index.add(userNode, "id", "123");
      tx.success();
    } finally {
      tx.finish();
    }
    Neo4jTemplate template = new Neo4jTemplate(index);
    UserMapper mapper = new UserMapper();
    User aUser = template.getObjectById("123", mapper);
    assertEquals("123", aUser.getId());
    assertEquals("john", aUser.getName());
    graphDB.shutdown();
    FileUtils.deleteRecursively(new File("target/neo4j/db"));
  }


  @Test
  public void testAfterSaveUserShouldBeRetrievedById() throws Exception {
    GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase( "target/neo4j/db" );
    Index<Node> index = graphDB.index().forNodes("test");
    Neo4jTemplate template = new Neo4jTemplate(index);
    UserMapper mapper = new UserMapper();
    User john = new User("123", "john");
    template.saveObject(john, mapper);
    User aUser = template.getObjectById("123", mapper);
    assertEquals(john.getId(), aUser.getId());
    assertEquals(john.getName(), aUser.getName());
    graphDB.shutdown();
    FileUtils.deleteRecursively(new File("target/neo4j/db"));
  }


}
