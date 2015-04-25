
package com.handsome.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "listNoteByUidResponse", namespace = "http://service.handsome.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listNoteByUidResponse", namespace = "http://service.handsome.com/")
public class ListNoteByUidResponse {

    @XmlElement(name = "return", namespace = "")
    private List<com.handsome.pojo.Note> _return;

    /**
     * 
     * @return
     *     returns List<Note>
     */
    public List<com.handsome.pojo.Note> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<com.handsome.pojo.Note> _return) {
        this._return = _return;
    }

}
