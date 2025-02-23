package io.kay.website.api.model;

import io.kay.website.api.model.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("PersonalInformation")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-23T07:46:22.767303406+01:00[Europe/Vienna]", comments = "Generator version: 7.11.0")
public class PersonalInformation   {
  private String firstName;
  private String lastName;
  private Integer age;
  private String email;
  private String phoneNumber;
  private City originalFrom;
  private City currentlyLivingIn;
  private @Valid List<String> languages = new ArrayList<>();
  private @Valid List<String> interests = new ArrayList<>();

  public PersonalInformation() {
  }

  @JsonCreator
  public PersonalInformation(
    @JsonProperty(required = true, value = "firstName") String firstName,
    @JsonProperty(required = true, value = "lastName") String lastName,
    @JsonProperty(required = true, value = "age") Integer age,
    @JsonProperty(required = true, value = "email") String email,
    @JsonProperty(required = true, value = "currentlyLivingIn") City currentlyLivingIn,
    @JsonProperty(required = true, value = "languages") List<String> languages,
    @JsonProperty(required = true, value = "interests") List<String> interests
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.email = email;
    this.currentlyLivingIn = currentlyLivingIn;
    this.languages = languages;
    this.interests = interests;
  }

  /**
   **/
  public PersonalInformation firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  
  @ApiModelProperty(example = "Max", required = true, value = "")
  @JsonProperty(required = true, value = "firstName")
  @NotNull public String getFirstName() {
    return firstName;
  }

  @JsonProperty(required = true, value = "firstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   **/
  public PersonalInformation lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  
  @ApiModelProperty(example = "Mustermann", required = true, value = "")
  @JsonProperty(required = true, value = "lastName")
  @NotNull public String getLastName() {
    return lastName;
  }

  @JsonProperty(required = true, value = "lastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * minimum: 0
   **/
  public PersonalInformation age(Integer age) {
    this.age = age;
    return this;
  }

  
  @ApiModelProperty(example = "33", required = true, value = "")
  @JsonProperty(required = true, value = "age")
  @NotNull  @Min(0)public Integer getAge() {
    return age;
  }

  @JsonProperty(required = true, value = "age")
  public void setAge(Integer age) {
    this.age = age;
  }

  /**
   **/
  public PersonalInformation email(String email) {
    this.email = email;
    return this;
  }

  
  @ApiModelProperty(example = "sample@email.com", required = true, value = "")
  @JsonProperty(required = true, value = "email")
  @NotNull public String getEmail() {
    return email;
  }

  @JsonProperty(required = true, value = "email")
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   **/
  public PersonalInformation phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  
  @ApiModelProperty(example = "+43618999997", value = "")
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @JsonProperty("phoneNumber")
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   **/
  public PersonalInformation originalFrom(City originalFrom) {
    this.originalFrom = originalFrom;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("originalFrom")
  @Valid public City getOriginalFrom() {
    return originalFrom;
  }

  @JsonProperty("originalFrom")
  public void setOriginalFrom(City originalFrom) {
    this.originalFrom = originalFrom;
  }

  /**
   **/
  public PersonalInformation currentlyLivingIn(City currentlyLivingIn) {
    this.currentlyLivingIn = currentlyLivingIn;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "currentlyLivingIn")
  @NotNull @Valid public City getCurrentlyLivingIn() {
    return currentlyLivingIn;
  }

  @JsonProperty(required = true, value = "currentlyLivingIn")
  public void setCurrentlyLivingIn(City currentlyLivingIn) {
    this.currentlyLivingIn = currentlyLivingIn;
  }

  /**
   **/
  public PersonalInformation languages(List<String> languages) {
    this.languages = languages;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "languages")
  @NotNull public List<String> getLanguages() {
    return languages;
  }

  @JsonProperty(required = true, value = "languages")
  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public PersonalInformation addLanguagesItem(String languagesItem) {
    if (this.languages == null) {
      this.languages = new ArrayList<>();
    }

    this.languages.add(languagesItem);
    return this;
  }

  public PersonalInformation removeLanguagesItem(String languagesItem) {
    if (languagesItem != null && this.languages != null) {
      this.languages.remove(languagesItem);
    }

    return this;
  }
  /**
   **/
  public PersonalInformation interests(List<String> interests) {
    this.interests = interests;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "interests")
  @NotNull public List<String> getInterests() {
    return interests;
  }

  @JsonProperty(required = true, value = "interests")
  public void setInterests(List<String> interests) {
    this.interests = interests;
  }

  public PersonalInformation addInterestsItem(String interestsItem) {
    if (this.interests == null) {
      this.interests = new ArrayList<>();
    }

    this.interests.add(interestsItem);
    return this;
  }

  public PersonalInformation removeInterestsItem(String interestsItem) {
    if (interestsItem != null && this.interests != null) {
      this.interests.remove(interestsItem);
    }

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalInformation personalInformation = (PersonalInformation) o;
    return Objects.equals(this.firstName, personalInformation.firstName) &&
        Objects.equals(this.lastName, personalInformation.lastName) &&
        Objects.equals(this.age, personalInformation.age) &&
        Objects.equals(this.email, personalInformation.email) &&
        Objects.equals(this.phoneNumber, personalInformation.phoneNumber) &&
        Objects.equals(this.originalFrom, personalInformation.originalFrom) &&
        Objects.equals(this.currentlyLivingIn, personalInformation.currentlyLivingIn) &&
        Objects.equals(this.languages, personalInformation.languages) &&
        Objects.equals(this.interests, personalInformation.interests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, age, email, phoneNumber, originalFrom, currentlyLivingIn, languages, interests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInformation {\n");
    
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    originalFrom: ").append(toIndentedString(originalFrom)).append("\n");
    sb.append("    currentlyLivingIn: ").append(toIndentedString(currentlyLivingIn)).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    interests: ").append(toIndentedString(interests)).append("\n");
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

