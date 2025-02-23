package io.kay.website.api.model;

import io.kay.website.api.model.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("Company")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class Company   {
  private String name;
  private String branch;
  private City city;
  private Integer amountOfEmployees;

  public Company() {
  }

  @JsonCreator
  public Company(
    @JsonProperty(required = true, value = "name") String name,
    @JsonProperty(required = true, value = "branch") String branch,
    @JsonProperty(required = true, value = "city") City city,
    @JsonProperty(required = true, value = "amountOfEmployees") Integer amountOfEmployees
  ) {
    this.name = name;
    this.branch = branch;
    this.city = city;
    this.amountOfEmployees = amountOfEmployees;
  }

  /**
   **/
  public Company name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "Test Company LLC", required = true, value = "")
  @JsonProperty(required = true, value = "name")
  @NotNull public String getName() {
    return name;
  }

  @JsonProperty(required = true, value = "name")
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public Company branch(String branch) {
    this.branch = branch;
    return this;
  }

  
  @ApiModelProperty(example = "Fiction", required = true, value = "")
  @JsonProperty(required = true, value = "branch")
  @NotNull public String getBranch() {
    return branch;
  }

  @JsonProperty(required = true, value = "branch")
  public void setBranch(String branch) {
    this.branch = branch;
  }

  /**
   **/
  public Company city(City city) {
    this.city = city;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "city")
  @NotNull @Valid public City getCity() {
    return city;
  }

  @JsonProperty(required = true, value = "city")
  public void setCity(City city) {
    this.city = city;
  }

  /**
   * Rough estimation of the amount of employees
   **/
  public Company amountOfEmployees(Integer amountOfEmployees) {
    this.amountOfEmployees = amountOfEmployees;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Rough estimation of the amount of employees")
  @JsonProperty(required = true, value = "amountOfEmployees")
  @NotNull public Integer getAmountOfEmployees() {
    return amountOfEmployees;
  }

  @JsonProperty(required = true, value = "amountOfEmployees")
  public void setAmountOfEmployees(Integer amountOfEmployees) {
    this.amountOfEmployees = amountOfEmployees;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Company company = (Company) o;
    return Objects.equals(this.name, company.name) &&
        Objects.equals(this.branch, company.branch) &&
        Objects.equals(this.city, company.city) &&
        Objects.equals(this.amountOfEmployees, company.amountOfEmployees);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, branch, city, amountOfEmployees);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Company {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    amountOfEmployees: ").append(toIndentedString(amountOfEmployees)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

