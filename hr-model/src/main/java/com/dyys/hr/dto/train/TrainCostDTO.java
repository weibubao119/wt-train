package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 项目费用dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "项目费用dto")
public class TrainCostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "培训项目ID")
	@NotNull(message = "培训项目ID不能为空", groups = {Insert.class,Update.class})
	private Long programsId;

	@ApiModelProperty(value = "培训科目常量id")
	@NotNull(message = "科目常量id不能为空", groups = {Insert.class,Update.class})
	private Integer subjectsId;

	@ApiModelProperty(value = "费用")
	@NotNull(message = "费用不能为空", groups = {Insert.class,Update.class})
	private Float amount;

	@ApiModelProperty(value = "附件列表")
	@Valid
	private List<FileDTO> fileList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}