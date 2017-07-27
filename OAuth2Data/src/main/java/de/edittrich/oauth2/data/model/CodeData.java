package de.edittrich.oauth2.data.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CodeData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-27T15:59:07.701Z")

public class CodeData   {
  @JsonProperty("state")
  private String state = null;

  @JsonProperty("confirmation_code")
  private String confirmationCode = null;

  public CodeData state(String state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(value = "")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public CodeData confirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
    return this;
  }

   /**
   * Get confirmationCode
   * @return confirmationCode
  **/
  @ApiModelProperty(value = "")


  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodeData codeData = (CodeData) o;
    return Objects.equals(this.state, codeData.state) &&
        Objects.equals(this.confirmationCode, codeData.confirmationCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, confirmationCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeData {\n");
    
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    confirmationCode: ").append(toIndentedString(confirmationCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

