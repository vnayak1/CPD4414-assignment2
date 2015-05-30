/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {

    public OrderQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Cafeteria");
        order.addPurchase(new Purchase(1, 8));
        order.addPurchase(new Purchase(2, 4));
        orderQueue.add(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testIfNoCustomer() throws OrderQueue.IfNoPurchase {
        boolean newFlag = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("","");
        order.addPurchase(new Purchase(1, 8));
        order.addPurchase(new Purchase(2, 4));
        try {
            orderQueue.add(order);
        } catch (OrderQueue.IfNoCustomer e) {
            newFlag = true;
        }
        assertTrue(newFlag);
    }
    
        @Test
        public void testIfNoPurchases() throws OrderQueue.IfNoCustomer {
        boolean newFlag = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("someNormal", "Order");
        try {
            orderQueue.add(order);
        } catch (OrderQueue.IfNoPurchase e) {
            newFlag = true;
        }
        assertTrue(newFlag);
    }
        @Test
        public void testIfOrderAvailableThenNextDisplayException() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase
        {
        OrderQueue orderqueue = new OrderQueue();
        Order order = new Order("SomeValues","OtherValues");
        order.addPurchase(new Purchase(1, 8));
        orderqueue.add(order);
        Order order_new = new Order("SomeValues","OtherValues");
        order_new.addPurchase(new Purchase(2, 4));
        orderqueue.add(order_new);
        
        Order result = orderqueue.nextOrder();
            assertEquals(result, order);
            assertNull(result.getTimeProcessed());
        }
        
         @Test
        public void testIfOrderNotAvailableThenNextIsNullException() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase
        {
        OrderQueue orderqueue = new OrderQueue();
        Order result = orderqueue.nextOrder();
            
            assertNull(result);
        }
        
        @Test
        public void testIfTimeReceivedSetThenResetToNow() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase
        {
        OrderQueue orderqueue = new OrderQueue();
        
        Order order = new Order("SomeValues","OtherValues");
        order.addPurchase(new Purchase(1, 8));
        orderqueue.add(order);
        Order order_new = new Order("SomeValues","OtherValues");
        order_new.addPurchase(new Purchase(2, 4));
        orderqueue.add(order_new);
        
        Order ordernext = orderqueue.nextOrder();
        orderqueue.process(ordernext);
         
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);

        
        }
    

}
