package sru.edu.Group1.WorkOrders.webtests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.controller.WorkOrdersController;

@SpringBootTest
public class Webtests {

@Autowired
private WorkOrdersController workorders;
	
@Test
public void contextLoads() {
	
}

@Test
public void workOrdersContextLoads() throws Exception {
	assertThat(workorders).isNotNull();
}


}
