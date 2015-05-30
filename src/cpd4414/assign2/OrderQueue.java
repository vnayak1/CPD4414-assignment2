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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();
    List<Order> ListOfOrder = new ArrayList<>();

    public void add(Order order) throws IfNoCustomer, IfNoPurchase {

        if (order.getCustomerId().isEmpty() && order.getCustomerName().isEmpty()) {
            throw new IfNoCustomer();
        }
        if (order.getListOfPurchases().isEmpty()) {
            throw new IfNoPurchase();
        }
        orderQueue.add(order);
        order.setTimeReceived(new Date());
    }

    public Order nextOrder() {

        return orderQueue.peek();
    }

    void process(Order ordernext) {

        if (ordernext.equals(nextOrder())) {

//            boolean newflaghere = true;
//            for (Purchase pr : ordernext.getListOfPurchases()) {
//                if (Inventory.getQuantityForId(pr.getProductId()) < pr.getQuantity()) {
//                    newflaghere = false;
//                }
//            }
//            if (newflaghere) {
                ListOfOrder.add(orderQueue.remove());
                ordernext.setTimeProcessed(new Date());
   //         }
        }

    }

    public class IfNoCustomer extends Exception {

        public IfNoCustomer() {
            super("There is no customer");
        }
    }

    public class IfNoPurchase extends Exception {

        public IfNoPurchase() {
            super("There is no purchase");
        }
    }
}
