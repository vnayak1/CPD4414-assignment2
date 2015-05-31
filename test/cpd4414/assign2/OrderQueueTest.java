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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
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
        public void testIfTimeReceivedSetThenResetToNow() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase, OrderQueue.EmptyTimeReceived
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
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);        
        }
        
        @Test
        public void testIfTimeReceivedNotSetException() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase
        {
            boolean newflag = false;
        OrderQueue orderqueue = new OrderQueue();
        
        Order order = new Order("SomeValues","OtherValues");
        order.addPurchase(new Purchase(1, 8));
        
        try{
        orderqueue.process(order);
        }
        catch(OrderQueue.EmptyTimeReceived e){
         newflag=true;
        }
            assertTrue(newflag);
            
        }
        
        @Test
        public void testIfTimeReceivedSetThenTimeFullfiledToNow() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase, OrderQueue.EmptyTimeReceived, OrderQueue.EmptyTimeprocessed
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
        
        orderqueue.methodfulfill(ordernext);
        
        long expResult = new Date().getTime();
        long result = order.getTimeFulfilled().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);        
        }
        
    @Test
    public void testIfTimeReceivedNotSetThenThrowException() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase, OrderQueue.EmptyTimeprocessed {
        boolean newflag = false;
        OrderQueue orderqueue = new OrderQueue();

        Order order = new Order("SomeValues", "OtherValues");
        order.addPurchase(new Purchase(1, 8));

        try {
            orderqueue.methodfulfill(order);
        } catch (OrderQueue.EmptyTimeReceived e) {
            newflag = true;
        }
        assertTrue(newflag);

    }
    
         @Test
        public void testIfTimeProcessedNotSetThenException() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase, OrderQueue.EmptyTimeReceived
        {
            boolean newflag = false;
        OrderQueue orderqueue = new OrderQueue();
        
        Order order = new Order("SomeValues","OtherValues");
        order.addPurchase(new Purchase(1, 8));
        orderqueue.add(order);
        
        try{
        orderqueue.methodfulfill(order);
        }   
        catch(OrderQueue.EmptyTimeprocessed e){
         newflag=true;
        }
            assertTrue(newflag);
            
        }
        
        @Test
        public void testEmptyStringForNoOrderRepoert(){
        
        OrderQueue orderqueue = new OrderQueue();
        String expresult ="";
        String result = orderqueue.generateReport();
            assertEquals(expresult, result);
        
        
        
        }
        @Test
        public void testGeneratereportByOrder() throws OrderQueue.IfNoCustomer, OrderQueue.IfNoPurchase, OrderQueue.EmptyTimeReceived, OrderQueue.EmptyTimeprocessed, ParseException{
        
        OrderQueue orderqueue = new OrderQueue();
        
        Order order = new Order("Cust1","Name1");
        order.addPurchase(new Purchase(1, 8));
        orderqueue.add(order);
        Order order_new = new Order("Cust2","Name2");
        order_new.addPurchase(new Purchase(2, 4));
        orderqueue.add(order_new);        
        Order ordernext = orderqueue.nextOrder();
        orderqueue.process(ordernext); 
        
        orderqueue.methodfulfill(ordernext);
        
            JSONObject expresult = new JSONObject();
            JSONArray orders = new JSONArray();
            JSONObject o1 = new JSONObject();
            o1.put("customerId", "Cust1");
            o1.put("customerName","Name1");
            o1.put("timeReceived", new Date().toString());
            o1.put("timeProcessed",new Date().toString());
            o1.put("timeFulfilled", new Date().toString());
            JSONArray pList = new JSONArray();
            JSONObject p1 = new JSONObject();
            p1.put("ProductId", 1);
            p1.put("quantity",8);
            pList.add(p1);
            o1.put("purchases", pList);
            o1.put("notes",  null);
            orders.add(o1);
            JSONObject o2 = new JSONObject();
            o2.put("customerId", "Cust2");
            o2.put("customerName","Name2");
            o2.put("timeReceived", new Date().toString());
            o2.put("timeProcessed",null);
            o2.put("timeFulfilled", null);
            JSONArray pList1 = new JSONArray();
            JSONObject p2 = new JSONObject();
            p2.put("ProductId", 2);
            p2.put("quantity",4);
            pList1.add(p2);
            o2.put("purchases", pList1);
            o2.put("notes",  null);
            orders.add(o2);
            expresult.put("orders",orders);

                String resultString = orderqueue.generateReport();
          
                System.out.println(resultString);
                
              //  JSONObject newsetof = (JSONObject) JSONValue.parseWithException(expresult.toJSONString());
                JSONObject result = (JSONObject) JSONValue.parseWithException(resultString);
                System.out.println(result);
                assertEquals(expresult.toJSONString(),result.toJSONString()); 
        
        }
    

}
