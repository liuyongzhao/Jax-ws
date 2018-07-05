# 开发需求

1. 实现WS服务
2. 生成WSDL接口文档
3. 实现接口单元测试用例
4. 对接口访问过程进行日志记录

# 实现方案
## SOAP Web service实现方式
### Spring Boot框架实现方法

1、创建spring项目（本例基于maven管理）
使用IDE创建spring工程，采用maven或gradle进行项目构建

2、填加spring-boot-ws依赖
添加spring-boot-ws依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-ws</artifactId>
</dependency>
```

3、定义web service领域对象{name}.xsd
输入具体的信息格式

4、创建领域类
由maven插件自动配置
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>jaxb2-maven-plugin</artifactId>
    <version>1.6</version>
    <executions>
        <execution>
            <id>xjc</id>
            <goals>
                <goal>xjc</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <schemaDirectory>${project.basedir}/src/main/resources/</schemaDirectory>
        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
        <clearOutputDir>false</clearOutputDir>
    </configuration>
</plugin>
```

5、配置web service bean
```java
public class WebServiceConfig extends WsConfigurerAdapter {
@Bean(name = "***")
public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
}
```
6、创建程序的执行文件Application
访问网址：http://localhost:8080/ws/countries.wsdl

###文件夹规划
```txt   
├─src
│  ├─main
│  │  ├─java
│  │  │  ├─com
│  │  │  │  └─example
│  │  │  │      └─demo
│  │  │  │              CountryEndpoint.java
│  │  │  │              CountryRepository.java
│  │  │  │              DemoApplication.java
│  │  │  │              WebServiceConfig.java
│  │  │  │              
│  │  │  └─io
│  │  │      └─spring
│  │  │          └─guides
│  │  │              └─gs_producing_web_service
│  │  │                      Country.java
│  │  │                      Currency.java
│  │  │                      GetCountryRequest.java
│  │  │                      GetCountryResponse.java
│  │  │                      ObjectFactory.java
│  │  │                      package-info.java
│  │  │                      
│  │  └─resources
│  │          application.properties
│  │          countries.xsd
│  │          
│  └─test
│      └─java
│          └─com
│              └─example
│                  └─demo
│                          country.java
│                          DemoApplicationTests.java
│                          test.xml
└─pom.xml
```

### WSDL自动生成插件配置
pom.xml添加wdsl4j依赖
```xml
<dependency>
    <groupId>wsdl4j</groupId>
    <artifactId>wsdl4j</artifactId>
    <version>1.6.1</version>
</dependency>
```

###生成WSDL接口文档
```xml
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" 
           xmlns:sch="http://spring.io/guides/gs-producing-web-service" 
           xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" 
           xmlns:tns="http://spring.io/guides/gs-producing-web-service" 
           targetNamespace="http://spring.io/guides/gs-producing-web-service">
    <wsdl:types>
          <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified" 
                targetNamespace="http://spring.io/guides/gs-producing-web-service">
              <xs:element name="getCountryRequest">
                  <xs:complexType>
                      <xs:sequence>
                             <xs:element name="name" type="xs:string"/>
                      </xs:sequence>
                </xs:complexType>
            </xs:element>
            <xs:element name="getCountryResponse">
               <xs:complexType>
                     <xs:sequence>
                          <xs:element name="country" type="tns:country"/>
                     </xs:sequence>
               </xs:complexType>
           </xs:element>
           <xs:complexType name="country">
               <xs:sequence>
                   <xs:element name="name" type="xs:string"/>
                  <xs:element name="population" type="xs:int"/>
                  <xs:element name="capital" type="xs:string"/>
                  <xs:element name="currency" type="tns:currency"/>
             </xs:sequence>
         </xs:complexType>
         <xs:simpleType name="currency">
             <xs:restriction base="xs:string">
                    <xs:enumeration value="GBP"/>
                   <xs:enumeration value="EUR"/>
                   <xs:enumeration value="PLN"/>
             </xs:restriction>
           </xs:simpleType>
     </xs:schema>
 </wsdl:types>
 <wsdl:message name="getCountryResponse">
        <wsdl:part element="tns:getCountryResponse" name="getCountryResponse"></wsdl:part>
 </wsdl:message>
 <wsdl:message name="getCountryRequest">
        <wsdl:part element="tns:getCountryRequest" name="getCountryRequest"></wsdl:part>
 </wsdl:message>
 <wsdl:portType name="CountriesPort">
 <wsdl:operation name="getCountry">
<wsdl:input message="tns:getCountryRequest" name="getCountryRequest"></wsdl:input>
<wsdl:output message="tns:getCountryResponse" name="getCountryResponse"></wsdl:output>
</wsdl:operation>
</wsdl:portType>
<wsdl:binding name="CountriesPortSoap11" type="tns:CountriesPort">
        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
        <wsdl:operation name="getCountry">
               <soap:operation soapAction=""/>
               <wsdl:input name="getCountryRequest">
                        <soap:body use="literal"/>
               </wsdl:input>
              <wsdl:output name="getCountryResponse">
                       <soap:body use="literal"/>
             </wsdl:output>
        </wsdl:operation>
</wsdl:binding>
<wsdl:service name="CountriesPortService">
           <wsdl:port binding="tns:CountriesPortSoap11" name="CountriesPortSoap11">
                     <soap:address location="http://localhost:8080/ws"/>
          </wsdl:port>
</wsdl:service>
</wsdl:definitions>
```

###测试用例
设置name，查找对应的信息
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
		xmlns:gs="http://spring.io/guides/gs-producing-web-service">
   <soapenv:Header/>
   <soapenv:Body>
      <gs:getCountryRequest>
         <gs:name>Poland</gs:name>
      </gs:getCountryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

