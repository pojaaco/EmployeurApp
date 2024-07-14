// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: EmployerService.proto

package vn.elca.employer.common;

public interface EmployerProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:EmployerProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.google.protobuf.Int64Value id = 1;</code>
   */
  boolean hasId();
  /**
   * <code>.google.protobuf.Int64Value id = 1;</code>
   */
  com.google.protobuf.Int64Value getId();
  /**
   * <code>.google.protobuf.Int64Value id = 1;</code>
   */
  com.google.protobuf.Int64ValueOrBuilder getIdOrBuilder();

  /**
   * <code>.Fund fund = 2;</code>
   */
  int getFundValue();
  /**
   * <code>.Fund fund = 2;</code>
   */
  vn.elca.employer.common.Fund getFund();

  /**
   * <code>.google.protobuf.StringValue number = 3;</code>
   */
  boolean hasNumber();
  /**
   * <code>.google.protobuf.StringValue number = 3;</code>
   */
  com.google.protobuf.StringValue getNumber();
  /**
   * <code>.google.protobuf.StringValue number = 3;</code>
   */
  com.google.protobuf.StringValueOrBuilder getNumberOrBuilder();

  /**
   * <code>string name = 4;</code>
   */
  java.lang.String getName();
  /**
   * <code>string name = 4;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>string numberIde = 5;</code>
   */
  java.lang.String getNumberIde();
  /**
   * <code>string numberIde = 5;</code>
   */
  com.google.protobuf.ByteString
      getNumberIdeBytes();

  /**
   * <code>string startDate = 6;</code>
   */
  java.lang.String getStartDate();
  /**
   * <code>string startDate = 6;</code>
   */
  com.google.protobuf.ByteString
      getStartDateBytes();

  /**
   * <code>.google.protobuf.StringValue endDate = 7;</code>
   */
  boolean hasEndDate();
  /**
   * <code>.google.protobuf.StringValue endDate = 7;</code>
   */
  com.google.protobuf.StringValue getEndDate();
  /**
   * <code>.google.protobuf.StringValue endDate = 7;</code>
   */
  com.google.protobuf.StringValueOrBuilder getEndDateOrBuilder();

  /**
   * <code>repeated .EmployeeProto employees = 8;</code>
   */
  java.util.List<vn.elca.employer.common.EmployeeProto> 
      getEmployeesList();
  /**
   * <code>repeated .EmployeeProto employees = 8;</code>
   */
  vn.elca.employer.common.EmployeeProto getEmployees(int index);
  /**
   * <code>repeated .EmployeeProto employees = 8;</code>
   */
  int getEmployeesCount();
  /**
   * <code>repeated .EmployeeProto employees = 8;</code>
   */
  java.util.List<? extends vn.elca.employer.common.EmployeeProtoOrBuilder> 
      getEmployeesOrBuilderList();
  /**
   * <code>repeated .EmployeeProto employees = 8;</code>
   */
  vn.elca.employer.common.EmployeeProtoOrBuilder getEmployeesOrBuilder(
      int index);
}
