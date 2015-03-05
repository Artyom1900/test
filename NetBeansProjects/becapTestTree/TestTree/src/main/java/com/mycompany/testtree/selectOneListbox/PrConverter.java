/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testtree.selectOneListbox;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import model.ProductInstance;

@FacesConverter("prConverter")
public class PrConverter implements Converter{
    
    @Inject       
    private SelectOneView selectOneView;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("value= "+value);
        int id = Integer.parseInt(value);
        List<ProductInstance> list = selectOneView.getListProductInstance();
        int j=0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getId()==id) {
                j=i;
                break;
            }
        }
        return selectOneView.getListProductInstance().get(j);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String str = String.valueOf(((ProductInstance) value).getId());
        System.out.println("value.getClass= "+value.getClass()+" str= "+str);
        return str;
    }
    
}
