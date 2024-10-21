package com.travelport.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.travelport.config.DatabaseConfig;
import com.travelport.config.JpaRepositoryConfig;
import com.travelport.config.WebAppConfig;
import com.travelport.config.WebAppInitializer;
import com.travelport.persistence.impl.RoleDaoImpl;
import javax.sql.DataSource;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WebAppConfig.class,
    WebAppInitializer.class,
    JpaRepositoryConfig.class,
    DatabaseConfig.class,
    RoleController.class,
    RoleDaoImpl.class
})
class RoleControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private DataSource dataSource;

  MockMvc mockMvc;

  @Test
  void postRoleTest() throws Exception {
    //Set up
    executeInitialData();
    //DO the test
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(post("/roles")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .content("""
            {
              "name": "admin",
              "user": {
                "id": 1
              }
            }
            """))
        .andExpect(status().isOk());//assets

    //Verify
    //read database records
  }

  private void executeInitialData() throws SQLException {
    var conn = dataSource.getConnection();
    var statement = conn.prepareStatement("INSERT INTO users (id, name, country) VALUES (?, ?, ?)");
    statement.setInt(1, 1);
    statement.setString(2, "John Doe");
    statement.setString(3, "Spain");

    statement.executeUpdate();
  }


}