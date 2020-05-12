package com.example.webFlux.business.user.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "user_table")
public class UserModel implements Serializable {
    //标明主键
    @Id
    private String uuid;

    //    @NotBlank(message = "账号不能为空")
    private String account;

    //    @NotBlank(message = "密码不能为空")
    private String password;

}
