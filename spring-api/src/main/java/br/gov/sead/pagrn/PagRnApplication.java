package br.gov.sead.pagrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
//@ComponentScan(basePackages = {"br.gov.sead.pagrn.config","br.gov.sead.pagrn.dto", "br.gov.sead.pagrn.errorhandling","br.gov.sead.pagrn.onInit","br.gov.sead.pagrn.repository","br.gov.sead.pagrn.service"})
public class PagRnApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(PagRnApplication.class, args);
    }

}