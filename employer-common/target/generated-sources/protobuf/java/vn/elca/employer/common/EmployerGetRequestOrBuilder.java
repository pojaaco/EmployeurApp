// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: EmployerService.proto

package vn.elca.employer.common;

public interface EmployerGetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:EmployerGetRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Fund fund = 1;</code>
   */
  int getFundValue();
  /**
   * <code>.Fund fund = 1;</code>
   */
  vn.elca.employer.common.Fund getFund();

  /**
   * <code>.google.protobuf.StringValue number = 2;</code>
   */
  boolean hasNumber();
  /**
   * <code>.google.protobuf.StringValue number = 2;</code>
   */
  com.google.protobuf.StringValue getNumber();
  /**
   * <code>.google.protobuf.StringValue number = 2;</code>
   */
  com.google.protobuf.StringValueOrBuilder getNumberOrBuilder();

  /**
   * <code>.google.protobuf.StringValue name = 3;</code>
   */
  boolean hasName();
  /**
   * <code>.google.protobuf.StringValue name = 3;</code>
   */
  com.google.protobuf.StringValue getName();
  /**
   * <code>.google.protobuf.StringValue name = 3;</code>
   */
  com.google.protobuf.StringValueOrBuilder getNameOrBuilder();

  /**
   * <code>.google.protobuf.StringValue numberIde = 4;</code>
   */
  boolean hasNumberIde();
  /**
   * <code>.google.protobuf.StringValue numberIde = 4;</code>
   */
  com.google.protobuf.StringValue getNumberIde();
  /**
   * <code>.google.protobuf.StringValue numberIde = 4;</code>
   */
  com.google.protobuf.StringValueOrBuilder getNumberIdeOrBuilder();

  /**
   * <code>.google.protobuf.StringValue startDate = 5;</code>
   */
  boolean hasStartDate();
  /**
   * <code>.google.protobuf.StringValue startDate = 5;</code>
   */
  com.google.protobuf.StringValue getStartDate();
  /**
   * <code>.google.protobuf.StringValue startDate = 5;</code>
   */
  com.google.protobuf.StringValueOrBuilder getStartDateOrBuilder();

  /**
   * <code>.google.protobuf.StringValue endDate = 6;</code>
   */
  boolean hasEndDate();
  /**
   * <code>.google.protobuf.StringValue endDate = 6;</code>
   */
  com.google.protobuf.StringValue getEndDate();
  /**
   * <code>.google.protobuf.StringValue endDate = 6;</code>
   */
  com.google.protobuf.StringValueOrBuilder getEndDateOrBuilder();
}
