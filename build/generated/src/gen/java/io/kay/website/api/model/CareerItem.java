package io.kay.website.api.model;

import io.kay.website.api.model.Company;
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



@JsonTypeName("CareerItem")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class CareerItem   {
  private Company company;
  private LocalDate start;
  private LocalDate end;
  private String jobDescription;
  private String tasks;

  public CareerItem() {
  }

  @JsonCreator
  public CareerItem(
    @JsonProperty(required = true, value = "company") Company company,
    @JsonProperty(required = true, value = "start") LocalDate start,
    @JsonProperty(required = true, value = "jobDescription") String jobDescription,
    @JsonProperty(required = true, value = "tasks") String tasks
  ) {
    this.company = company;
    this.start = start;
    this.jobDescription = jobDescription;
    this.tasks = tasks;
  }

  /**
   **/
  public CareerItem company(Company company) {
    this.company = company;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "company")
  @NotNull @Valid public Company getCompany() {
    return company;
  }

  @JsonProperty(required = true, value = "company")
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * Date in format dd-MM-yyyy
   **/
  public CareerItem start(LocalDate start) {
    this.start = start;
    return this;
  }

  
  @ApiModelProperty(example = "Fri May 24 01:00:00 CET 26", required = true, value = "Date in format dd-MM-yyyy")
  @JsonProperty(required = true, value = "start")
  @NotNull public LocalDate getStart() {
    return start;
  }

  @JsonProperty(required = true, value = "start")
  public void setStart(LocalDate start) {
    this.start = start;
  }

  /**
   * Date in format dd-MM-yyyy
   **/
  public CareerItem end(LocalDate end) {
    this.end = end;
    return this;
  }

  
  @ApiModelProperty(example = "Mon Apr 22 01:00:00 CET 37", value = "Date in format dd-MM-yyyy")
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
  public CareerItem jobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
    return this;
  }

  
  @ApiModelProperty(example = "Backend Developer", required = true, value = "")
  @JsonProperty(required = true, value = "jobDescription")
  @NotNull public String getJobDescription() {
    return jobDescription;
  }

  @JsonProperty(required = true, value = "jobDescription")
  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }

  /**
   **/
  public CareerItem tasks(String tasks) {
    this.tasks = tasks;
    return this;
  }

  
  @ApiModelProperty(example = "Writing OpenAPI specifications", required = true, value = "")
  @JsonProperty(required = true, value = "tasks")
  @NotNull public String getTasks() {
    return tasks;
  }

  @JsonProperty(required = true, value = "tasks")
  public void setTasks(String tasks) {
    this.tasks = tasks;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CareerItem careerItem = (CareerItem) o;
    return Objects.equals(this.company, careerItem.company) &&
        Objects.equals(this.start, careerItem.start) &&
        Objects.equals(this.end, careerItem.end) &&
        Objects.equals(this.jobDescription, careerItem.jobDescription) &&
        Objects.equals(this.tasks, careerItem.tasks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(company, start, end, jobDescription, tasks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CareerItem {\n");
    
    sb.append("    company: ").append(toIndentedString(company)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
    sb.append("    jobDescription: ").append(toIndentedString(jobDescription)).append("\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
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

