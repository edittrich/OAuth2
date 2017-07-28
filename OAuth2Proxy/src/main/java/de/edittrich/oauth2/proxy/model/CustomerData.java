package de.edittrich.oauth2.proxy.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CustomerData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-27T14:19:28.420Z")

public class CustomerData   {
  @JsonProperty("customer_id")
  private String customerId = null;

  @JsonProperty("confirmation_code")
  private String confirmationCode = null;

  @JsonProperty("access_token")
  private String accessToken = null;

  @JsonProperty("refresh_token")
  private String refreshToken = null;

  @JsonProperty("last_changed")
  private DateTime lastChanged = null;

  public CustomerData customerId(String customerId) {
    this.customerId = customerId;
    return this;
  }

   /**
   * Get customerId
   * @return customerId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public CustomerData confirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
    return this;
  }

   /**
   * Get confirmationCode
   * @return confirmationCode
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  public CustomerData accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

   /**
   * Get accessToken
   * @return accessToken
  **/
  @ApiModelProperty(value = "")


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public CustomerData refreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

   /**
   * Get refreshToken
   * @return refreshToken
  **/
  @ApiModelProperty(value = "")


  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public CustomerData lastChanged(DateTime lastChanged) {
    this.lastChanged = lastChanged;
    return this;
  }

   /**
   * Get lastChanged
   * @return lastChanged
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLastChanged() {
    return lastChanged;
  }

  public void setLastChanged(DateTime lastChanged) {
    this.lastChanged = lastChanged;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerData customerData = (CustomerData) o;
    return Objects.equals(this.customerId, customerData.customerId) &&
        Objects.equals(this.confirmationCode, customerData.confirmationCode) &&
        Objects.equals(this.accessToken, customerData.accessToken) &&
        Objects.equals(this.refreshToken, customerData.refreshToken) &&
        Objects.equals(this.lastChanged, customerData.lastChanged);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, confirmationCode, accessToken, refreshToken, lastChanged);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerData {\n");
    
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    confirmationCode: ").append(toIndentedString(confirmationCode)).append("\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    lastChanged: ").append(toIndentedString(lastChanged)).append("\n");
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

