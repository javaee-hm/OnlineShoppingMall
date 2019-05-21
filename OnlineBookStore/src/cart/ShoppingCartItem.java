/*
 * 
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc. Use is
 * subject to license terms.
 *  
 */

package cart;

import Bean.BeanBook;

public class ShoppingCartItem {
	BeanBook item;

    int quantity;

    public ShoppingCartItem(BeanBook anItem) {
        item = anItem;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public BeanBook getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
