package com.system.bike_rental_system.pojo;

import com.system.bike_rental_system.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPojo {
    private Integer id;
    private String categoryName;
    private String categoryDescription;

    public CategoryPojo(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.categoryDescription = category.getCategoryDescription();
    }
}
