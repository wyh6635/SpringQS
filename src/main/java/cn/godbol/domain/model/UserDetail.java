package cn.godbol.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Li on 2016/10/14.
 */
@Entity
@Table(name = "tb_userdetail")
@Getter
@Setter

public class UserDetail extends BaseModel {

    //中文名
    @Column(length = 50)
    private String c_name;
    @Column(length = 50)
    private String telephone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
