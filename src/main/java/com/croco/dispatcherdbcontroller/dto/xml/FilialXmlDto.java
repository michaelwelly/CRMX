package com.croco.dispatcherdbcontroller.dto.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.Map;

@XmlRootElement(name = "Filial")
@Setter
public class FilialXmlDto {
    private Long id;
    private Map<String, Object> locations;
    private String titleStr;
    private String descriptionTxt;
    private Map<String, Object> attributesJson;

    @XmlElement(name = "Id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "Locations")
    public Map<String, Object> getLocations() {
        return locations;
    }

    @XmlElement(name = "TitleStr")
    public String getTitleStr() {
        return titleStr;
    }

    @XmlElement(name = "DescriptionTxt")
    public String getDescriptionTxt() {
        return descriptionTxt;
    }

    @XmlElement(name = "AttributesJson")
    public Map<String, Object> getAttributesJson() {
        return attributesJson;
    }
}