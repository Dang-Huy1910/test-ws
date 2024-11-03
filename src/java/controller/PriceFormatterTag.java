/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PriceFormatterTag extends SimpleTagSupport {
    private double price;

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // Format price in VND
        String formattedPrice = String.format("%.0f VND", price);
        getJspContext().getOut().write(formattedPrice);
    }
}
