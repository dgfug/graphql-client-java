package com.hv.catalog.graphqlclientjava.service;

import com.hv.catalog.graphql.generated.types.Histories;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoryServiceTest {
  private HistoryService service;

  @BeforeEach
  void setUp() {
    service = new HistoryService();
  }

  @AfterEach
  void tearDown() {
    service = null;
  }

  @Test
  void getHistoryList() {
    List<Histories> historyList = service.getHistoryList();
    Assertions.assertFalse(historyList.isEmpty());
  }
}