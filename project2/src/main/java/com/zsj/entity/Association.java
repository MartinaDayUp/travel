package com.zsj.entity;

import lombok.Data;

@Data
public class Association {
    private Long cat;

    public Long getCat() {
        return cat;
    }

    public void setCat(Long cat) {
        this.cat = cat;
    }
}
