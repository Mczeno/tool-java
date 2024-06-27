package io.github.mczeno.tool;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TestObject
 *
 * @author Chongming Zhou
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {

    @NotNull(message = "id can not be null")
    private Long id;

    @NotEmpty(message = "code can not be empty")
    private String code;

    private String remark;

}
