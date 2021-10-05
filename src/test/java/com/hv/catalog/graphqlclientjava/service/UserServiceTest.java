package com.hv.catalog.graphqlclientjava.service;

import com.hv.catalog.graphql.generated.types.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

  private UserService service;

  @BeforeEach
  void setUp() {
    service = new UserService();
  }

  @AfterEach
  void tearDown() {
    service = null;
  }

  @Test
  void getUsers() {
    List<User> users = service.getUsers();
    Assertions.assertNotNull(users);
    Assertions.assertFalse(users.isEmpty());
    System.out.println(users);
  }
}