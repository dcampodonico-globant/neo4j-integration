package org.dcampodonico.neo4j.integration;

import org.neo4j.graphdb.Node;

public class UserMapper implements ObjectMapper<User> {

  public User mapNode(Node aNode) {
    String id = (String) aNode.getProperty("id");
    String name = (String) aNode.getProperty("name");
    return new User(id, name);
  }

  public void mapObject(User object, Node objectNode) {
    objectNode.setProperty("id", object.getId());
    objectNode.setProperty("name", object.getName());
  }

}
