package org.konstr.votingsystem.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.MappedSuperclass;

/**
 * Created by Yury Vasilkou
 * Date: 27-Feb-17.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotBlank
    @SafeHtml
    private String name;

    public NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
