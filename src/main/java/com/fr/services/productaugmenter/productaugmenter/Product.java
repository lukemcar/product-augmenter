package com.fr.services.productaugmenter.productaugmenter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String name;
    private String description;
    private double price;

    public String getSlug()
    {
        String slug = name.toLowerCase();
        slug = slug.replace(" ", "-");
        return slug;
    }
}
