package com.lombardrisk.util.feed;

import java.util.List;

import javax.xml.bind.JAXBException;

/**
 * Interface for feed logic.
 * Created by Lawrence Xu on 5/22/2017.
 */
public interface IFeedLogic<T> {

    /**
     * Convert list of objects to XML.
     * @param list list of objects
     * @return XML string
     * @throws JAXBException when there is JAXB error
     */
    String convertListToXML(List<T> list) throws JAXBException;
}
