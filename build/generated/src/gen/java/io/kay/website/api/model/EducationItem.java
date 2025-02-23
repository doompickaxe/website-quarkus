package io.kay.website.api.model;

import io.kay.website.api.model.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("EducationItem")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class EducationItem   {
  private String schoolName;
  private City city;
  private LocalDate start;
  private LocalDate end;
  private String degree;
  private String description;
  public enum EducationTypeEnum {

    ELEMENTARY_SCHOOL(String.valueOf("ELEMENTARY_SCHOOL")), SECONDARY_SCHOOL(String.valueOf("SECONDARY_SCHOOL")), COLLEGE(String.valueOf("COLLEGE")), UNIVERSITY(String.valueOf("UNIVERSITY"));


    private String value;

    EducationTypeEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Convert a String into String, as specified in the
     * <a href="https://download.oracle.com/otndocs/jcp/jaxrs-2_0-fr-eval-spec/index.html">See JAX RS 2.0 Specification, section 3.2, p. 12</a>
     */
    public static EducationTypeEnum fromString(String s) {
        for (EducationTypeEnum b : EducationTypeEnum.values()) {
            // using Objects.toString() to be safe if value type non-object type
            // because types like 'int' etc. will be auto-boxed
            if (java.util.Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected string value '" + s + "'");
    }

    @JsonCreator
    public static EducationTypeEnum fromValue(String value) {
        for (EducationTypeEnum b : EducationTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

  private EducationTypeEnum educationType;

  public EducationItem() {
  }

  @JsonCreator
  public EducationItem(
    @JsonProperty(required = true, value = "schoolName") String schoolName,
    @JsonProperty(required = true, value = "city") City city,
    @JsonProperty(required = true, value = "start") LocalDate start,
    @JsonProperty(required = true, value = "degree") String degree,
    @JsonProperty(required = true, value = "educationType") EducationTypeEnum educationType
  ) {
    this.schoolName = schoolName;
    this.city = city;
    this.start = start;
    this.degree = degree;
    this.educationType = educationType;
  }

  /**
   **/
  public EducationItem schoolName(String schoolName) {
    this.schoolName = schoolName;
    return this;
  }

  
  @ApiModelProperty(example = "Making people smart", required = true, value = "")
  @JsonProperty(required = true, value = "schoolName")
  @NotNull public String getSchoolName() {
    return schoolName;
  }

  @JsonProperty(required = true, value = "schoolName")
  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  /**
   **/
  public EducationItem city(City city) {
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
   * Date in format dd-MM-yyyy
   **/
  public EducationItem start(LocalDate start) {
    this.start = start;
    return this;
  }

  
  @ApiModelProperty(example = "Mon Apr 22 01:00:00 CET 37", required = true, value = "Date in format dd-MM-yyyy")
  @JsonProperty(required = true, value = "start")
  @NotNull public LocalDate getStart() {
    return start;
  }

  @JsonProperty(required = true, value = "start")
  public void setStart(LocalDate start) {
    this.start = start;
  }

  /**
   * Date in format dd-MM-yyyy. In case this field is missing it indicates that I am still attending this institution. 
   **/
  public EducationItem end(LocalDate end) {
    this.end = end;
    return this;
  }

  
  @ApiModelProperty(example = "Mon Apr 22 01:00:00 CET 37", value = "Date in format dd-MM-yyyy. In case this field is missing it indicates that I am still attending this institution. ")
  @JsonProperty("end")
  public LocalDate getEnd() {
    return end;
  }

  @JsonProperty("end")
  public void setEnd(LocalDate end) {
    this.end = end;
  }

  /**
   **/
  public EducationItem degree(String degree) {
    this.degree = degree;
    return this;
  }

  
  @ApiModelProperty(example = "Bachelor of Science", required = true, value = "")
  @JsonProperty(required = true, value = "degree")
  @NotNull public String getDegree() {
    return degree;
  }

  @JsonProperty(required = true, value = "degree")
  public void setDegree(String degree) {
    this.degree = degree;
  }

  /**
   * What I&#39;ve learned during that time
   **/
  public EducationItem description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(value = "What I've learned during that time")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public EducationItem educationType(EducationTypeEnum educationType) {
    this.educationType = educationType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "educationType")
  @NotNull public EducationTypeEnum getEducationType() {
    return educationType;
  }

  @JsonProperty(required = true, value = "educationType")
  public void setEducationType(EducationTypeEnum educationType) {
    this.educationType = educationType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EducationItem educationItem = (EducationItem) o;
    return Objects.equals(this.schoolName, educationItem.schoolName) &&
        Objects.equals(this.city, educationItem.city) &&
        Objects.equals(this.start, educationItem.start) &&
        Objects.equals(this.end, educationItem.end) &&
        Objects.equals(this.degree, educationItem.degree) &&
        Objects.equals(this.description, educationItem.description) &&
        Objects.equals(this.educationType, educationItem.educationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schoolName, city, start, end, degree, description, educationType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EducationItem {\n");
    
    sb.append("    schoolName: ").append(toIndentedString(schoolName)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
    sb.append("    degree: ").append(toIndentedString(degree)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    educationType: ").append(toIndentedString(educationType)).append("\n");
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

