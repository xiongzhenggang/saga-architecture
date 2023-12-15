//package com.xzg.orchestrator.kit.orchestration.saga.dao;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
///**
// * <p>
// * <b>项目名称： </b>:saga-architecture
// * <b>Class name</b>: SagaEntity
// * </p>
// * <p>
// * <b>Class description</b>:
// *
// * </p>
// * <p>
// * <b>Author</b>: xiongzhenggang
// * </p>
// * <b>Change History</b>:<br/>
// * <p>
// *
// * <pre>
// * Date          Author       Revision     Comments
// * ----------    ----------   --------     ------------------
// * 11/28/2023   xiongzhenggang        1.0          Initial Creation
// *
// * </pre>
// *
// * @author xiongzhenggang
// * @date 11/28/2023
// * </p>
// */
//@Data
//@Table(name="saga_instance")
//@Entity
//public class SagaEntity {
//    @Id
//    @GeneratedValue
//    private String id;
//    private String sagaType;
//    private String lastRequestId;
//    private String stateName;
//    private String sagaDataType;
//    private String sagaDataJSON;
////    private Set<DestinationAndResource> destinationsAndResources;
//    private Boolean endState = false;
//    private Boolean compensating = false;
//    private Boolean failed = false;
//}
