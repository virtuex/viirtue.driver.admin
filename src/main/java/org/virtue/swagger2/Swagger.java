package org.virtue.swagger2;

import io.swagger.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author
 * @Description
 */
@Configuration
public class Swagger {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.virtue"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("驾考宝典API接口")
                .version("1.0")
                .build();
    }
//    @Api：修饰整个类，描述Controller的作用
//    @ApiOperation：描述一个类的一个方法，或者说一个接口
//    @ApiParam：单个参数描述
//    @ApiModel：用对象来接收参数
//    @ApiProperty：用对象接收参数时，描述对象的一个字段
//    @ApiResponse：HTTP响应其中1个描述
//    @ApiResponses：HTTP响应整体描述
//    @ApiIgnore：使用该注解忽略这个API
//    @ApiError ：发生错误返回的信息
//    @ApiImplicitParam：一个请求参数
//    @ApiImplicitParams：多个请求参数

//    @ApiOperation("获取用户信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
//            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
//    })
//    @ApiResponses({
//            @ApiResponse(code=400,message="请求参数没填好"),
//            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })

}