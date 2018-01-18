package com.pccw.ott.util;

public class Constants {
	public static Long ADMIN_ROLE_ID = (long) 1;

	public static String FIELD_NAME_SEPARATOR = ",";

	public static String EMAIL_NOTIFY_TARGET_TYPE_APPROVER = "approver";
	public static String EMAIL_NOTIFY_TARGET_TYPE_INITIATOR = "initiator";
	public static String EMAIL_NOTIFY_TARGET_TYPE_CCLIST = "cclist";

	public static String SEARCH_STRATEGY_LIKE = "LIKE";
	public static String SEARCH_STRATEGY_EQUAL = "EQUAL";
	public static String SEARCH_STRATEGY_GREATER_THAN = "GREATER_THAN";
	public static String SEARCH_STRATEGY_LESS_THAN = "LESS_THAN";
	public static String SEARCH_STRATEGY_GREATER_THAN_OR_EQUAL = "GREATER_THAN_OR_EQUAL";
	public static String SEARCH_STRATEGY_LESS_THAN_OR_EQUAL = "LESS_THAN_OR_EQUAL";

	public static String USER_DO_LOGOUT_URL = "/accountmanagement/user/doLogout.html";
	public static String USER_DO_LOGIN_URL = "/accountmanagement/user/doLogin.html";
	public static String MAIN_PAGE_URL = "/main.html";
	public static String NO_PERMISSION_URL = "/accountmanagement/permission/goToNoPermissionPage.html";
	public static String NO_PERMISSION_PAGE_URL = "/common/no_permission.jsp";

	public static String USER_DELETE_URL = "/accountmanagement/user/deleteUser.html";
	public static String USER_ADD_URL = "/accountmanagement/user/addUser.html";
	public static String USER_UPDATE_URL = "/accountmanagement/user/updateUser.html";
	public static String USER_GO_TO_LIST_USER_PAGE_URL = "/accountmanagement/user/goToListUserPage.html";
	public static String USER_LIST_USER_URL = "/accountmanagement/user/listUser.html";
	public static String USER_LIST_USER_ROLES_URL = "/accountmanagement/user/listUserRoles.html";
	public static String USER_EDIT_ROLES_URL = "/accountmanagement/user/userEditRoles.html";
	public static String USER_GET_USER_PARAM_URL = "/accountmanagement/getUserParambyId.html";

	public static String ROLE_ADD_URL = "/accountmanagement/role/addRole.html";
	public static String ROLE_DELETE_URL = "/accountmanagement/role/deleteRole.html";
	public static String ROLE_UPDATE_URL = "/accountmanagement/role/updateRole.html";
	public static String ROLE_GO_TO_LIST_ROLE_PAGE_URL = "/accountmanagement/role/goToListRolePage.html";
	public static String ROLE_LIST_ROLE_URL = "/accountmanagement/role/listRole.html";
	public static String ROLE_LIST_ROLE_PERMISSIONS_URL = "/accountmanagement/role/listRolePermissions.html";
	public static String ROLE_EDIT_PERMISSIONS_URL = "/accountmanagement/role/roleEditPermissions.html";

	public static String PERMISSION_ADD_URL = "/accountmanagement/permission/addPermission.html";
	public static String PERMISSION_DELETE_URL = "/accountmanagement/permission/deletePermission.html";
	public static String PERMISSION_UPDATE_URL = "/accountmanagement/permission/updatePermission.html";
	public static String PERMISSION_GO_TO_LIST_PERMISSION_PAGE_URL = "/accountmanagement/permission/goToListPermissionPage.html";
	public static String PERMISSION_LIST_PERMISSION_URL = "/accountmanagement/permission/listPermission.html";
	public static String PERMISSION_GET_ALL_TABLE_FIELDS_URL = "/accountmanagement/permission/getAllTableFields.html";

	public static String LIST_TABLE_FIELDS_URL = "/list_table_fields.html";
	public static String LIST_TABLE_DATA_URL = "/list_table_data.html";
	public static String UPDATE_TABLE_DATA_URL = "/update_table_data.html";
	public static String DELETE_TABLE_DATE_URL = "/delete_table_data.html";
	public static String INSERT_TABLE_DATE_URL = "/insert_table_data.html";

	public static String AUDIT_TRAIL_GO_TO_LIST_AUDIT_TRAIL_PAGE_URL = "/audit_trail/goToListAuditTrailPage.html";
	public static String AUDIT_TRAIL_LIST_AUDIT_TRAIL_URL = "/audit_trail/listAuditTrail.html";
	public static String AUDIT_TRAIL_LIST_SEARCHABLE_TABLE_FILED = "/audit_trail/list_bmtTableField.html";

	public static String APPROVER_GET_APPROVER_PARAM_URL = "/approver/getApproverParambyId.html";
	public static String APPROVER_GO_TO_LIST_APPROVER_PAGE = "/approver/listApproverPage.html";
	public static String APPROVER_ADD_URL = "/approver/addApprover.html";
	public static String APPROVER_EDIT_URL = "/approver/updateApprover.html";
	public static String APPROVER_DELETE_URL = "/approver/deleteApprover.html";
	public static String APPROVER_SEARCH_URL = "/approver/searchApprover.html";
	public static String APPROVER_LIST_APPROVER_URL = "/approver/listApprover.html";
	public static String APPROVER_HISTORY_GO_TO_LIST_APPROVER_HISTORY_PAGE = "/approver/listApproverHistoryPage.html";
	public static String APPROVER_HISTORY_LIST_APPROVER_HISTORY_URL = "/approver/listApproverHistory.html";
	public static String APPROVER_HISTORY_SEARCH_URL = "/approver/searchApproverHistory.html";

	public static String EMAILNOTIFY_GO_TO_LIST_EMAILNOTIFY_PAGE = "/emailNotify/goToListEmailNotifyPage.html";
	public static String EMAILNOTIFY_ADD_URL = "/emailNotify/addEmailNotify.html";
	public static String EMAILNOTIFY_EDIT_URL = "/emailNotify/updateEmailNotify.html";
	public static String EMAILNOTIFY_DELETE_URL = "/emailNotify/deleteEmailNotify.html";
	public static String EMAILNOTIFY_LIST_EMAILNOTIFY_URL = "/emailNotify/listEmailNotify.html";
	public static String EMAILNOTIFY_GET_USER_URL = "/accountmanagement/user/findUserByJobType.html";

	public static String GENERAL_CC_LIST_GO_TO_LIST_URL = "/emailNotify/goToListGeneralCCListPage.html";
	public static String GENERAL_CC_LIST_ADD_URL = "/emailNotify/addGeneralCCList.html";
	public static String GENERAL_CC_LIST_EDIT_URL = "/emailNotify//updateGeneralCCList.html";
	public static String GENERAL_CC_LIST_DELETE_URL = "/emailNotify//deleteGeneralCCList.html";

	public static String TESTING_DEVICE_LIST_GO_TO_LIST_PAGE_URL = "/testingDeviceListManagement/goToTestingDevicesListPage.html";
	public static String TESTING_DEVICE_LIST_ADD_URL = "/testingDeviceListManagement/addTestingDeviceList.html";
	public static String TESTING_DEVICE_LIST_UPDATE_URL = "/testingDeviceListManagement/updateTestingDeviceList.html";
	public static String TESTING_DEVICE_LIST_DELETE_URL = "/testingDeviceListManagement/deleteTestingDeviceList.html";
	public static String TESTING_DEVICE_LIST_LIST_URL = "/testingDeviceListManagement/listTestingDeviceList.html";
	public static String TESTING_DEVICE_LIST_GET_FSAS = "/testingDeviceListManagement/getFSAsByTdlistId.html";
	public static String TESTING_DEVICE_LIST_IS_LIST_USE_IN_FILETER_LIST = "/testingDeviceListManagement/isTheListUsedInFilterListRelease.html";
	public static String TESTING_DEVICE_LIST_FIND_BY_LISTNAME = "/testingDeviceListManagement/findTestingDeviceListByListName.html";
	public static String TESTING_DEVICE_LIST_DOWNLOAD_FSA_FILE = "/testingDeviceListManagement/downloadFsaFile.html";
	public static String TESTING_DEVICE_LIST_IS_FSA_FILE_EXIST = "/testingDeviceListManagement/isFsaFileExist.html";
	public static String TESTING_DEVICE_LIST_READY_FOR_APPROVAL = "/testingDeviceListManagement/readyForApproval.html";
	public static String TESTING_DEVICE_LIST_BATCH_READY_FOR_APPROVAL = "/testingDeviceListManagement/batchReadyForApproval.html";
	public static String TESTING_DEVICE_LIST_APPROVAL = "/testingDeviceListManagement/approval.html";
	public static String TESTING_DEVICE_LIST_CONFIRM = "/testingDeviceListManagement/confirm.html";
	public static String TESTING_DEVICE_LIST_FSA_FIND_BY_FSA = "/testingDeviceListManagement/findTestingDeviceListFSAByFsa.html";

	public static String FLASH_GO_TO_LIST_FLASH_PAGE_URL = "/flashManagement/goToListFlashPage.html";
	public static String FLASH_LIST_URL = "/flashManagement/listFlash.html";
	public static String FLASH_UPLOAD_URL = "/flashManagement/addFlash.html";
	public static String FLASH_EDIT_URL = "/flashManagement/editFlash.html";
	public static String FLASH_CLOSE_URL = "/flashManagement/closeFlash.html";
	public static String FLASH_GET_FLASHID = "/flashManagement/getFlashId.html";
	public static String FLASH_GET_FILE_CHECKSUM = "/flashManagement/getFileCheckSum.html";
	public static String FLASH_UPLOAD_FILE_URL = "/flashManagement/uploadFile.html";
	public static String FLASH_APPROVAL = "/flashManagement/approval.html";
	public static String FLASH_CONFIRM = "/flashManagement/confirm.html";
	public static String FLASH_REQUEST_FOR_APPROVAL = "/flashManagement/readyForApproval.html";
	public static String FLASH_REQUEST_TRANSFERRING_TO_PRODUCTION = "/flashManagement/requestTransferringToProduction.html";
	public static String FLASH_GO_TO_LIST_FLASH_FILE_TRANSFER_URL = "/flashManagement/goToListFlashFileTransferPage.html";
	public static String FLASH_TRANSFER_FLASH_FILE = "/flashManagement/toTransferFlashFileFromQAToProduction.html";
	public static String FLASH_GET_ENVTYPE_PLATFORM_FLASHID = "/flashManagement/getFlashEnvTypeAndPlatformAndFlashId.html";
	public static String FLASH_FIND_QA_APPROVED_FLASH_LIST = "/flashManagement/findQAApprovedFlashList.html";
	public static String FLASH_FIND_CURFLASH = "/flashManagement/findCurMWFlash.html";  //by Colin
	public static String FLASH_LIST_FOR_FILE_TRANSFER = "/flashManagement/listFlashForFileTransfer.html";
	public static String TRANSFER_OR_UPDATE_TO_PRODUCTION = "/flashManagement/transferOrUpdateToProduction.html";
	public static String FIND_SUB_CUST_CAT = "/flashManagement/findSubCustCat.html";
	public static String FIND_SAVED_SUB_CUST_CAT = "/flashManagement/findSavedSubCustCat.html";
	public static String FLASH_REQUEST_TRANSFER_TO_PRODUCTION = "/flashManagement/requestTransferringToProduction.html";
	
	// add by Seldom 2016.7.11
	public static String FLASH_GO_TO_LIST_BBL_PAGE_URL = "/flashManagement/goToListBblPage.html";
	public static String BBL_LIST_URL = "/flashManagement/listBbl.html";
	public static String BBL_UPLOAD_URL = "/flashManagement/addBbl.html";
	public static String BBL_EDIT_URL = "/flashManagement/editBbl.html";
	public static String BBL_CLOSE_URL = "/flashManagement/closeBbl.html";
	public static String BBL_REQUEST_TRANSFER_TO_PRODUCTION = "/flashManagement/requestTransferringBblToProduction.html";
	public static String FLASH_GO_TO_LIST_OS_PAGE_URL = "/flashManagement/goToListOsPage.html";
	public static String OS_LIST_URL = "/flashManagement/listOs.html";
	public static String OS_UPLOAD_URL = "/flashManagement/addOs.html";
	public static String OS_EDIT_URL = "/flashManagement/editOs.html";
	public static String OS_CLOSE_URL = "/flashManagement/closeOs.html";
	public static String OS_REQUEST_TRANSFER_TO_PRODUCTION = "/flashManagement/requestTransferringOsToProduction.html";
	// add by Seldom end.

	public static String PRODUCTION_RELEASE_MANAGEMENT_PAGE = "/releaseManagement/production/releaseManagementPage.html";
	public static String PRODUCTION_LIST_SCHEDULE = "/releaseManagement/production/listSchedule.html";
	public static String PRODUCTION_FIND_SUBCUSTCAT = "/releaseManagement/production/findSubCustCat.html";
	public static String PRODUCTION_FIND_FLASH = "/releaseManagement/production/findFlash.html";
	public static String PRODUCTION_FIND_CURFLASH = "/releaseManagement/production/findCurFlash.html";
	public static String PRODUCTION_CALCULATE_FSA_RANGE = "/releaseManagement/production/calculateFsaRange.html";
	public static String PRODUCTION_NEW_SCHEDULE = "/releaseManagement/production/newSchedule.html";
	public static String PRODUCTION_FIND_BATCH_DATA = "/releaseManagement/production/findBatchData.html";
	public static String PRODUCTION_UPDATE_SCHEDULE = "/releaseManagement/production/updateSchedule.html";
	public static String PRODUCTION_SET_SCHEDULE_TO_DRAFT = "/releaseManagement/production/setScheduleToDraft.html";
	public static String PRODUCTION_READY_FOR_APPROVAL = "/releaseManagement/production/readyForApproval.html";
	public static String PRODUCTION_APPROVE_OR_REJECT_PAGE = "/releaseManagement/production/approveOrRejectPage.html";
	public static String PRODUCTION_APPROVE_OR_REJECT = "/releaseManagement/production/approveOrReject.html";
	// public static String PRODUCTION_STOP_OR_RESUME =
	// "/releaseManagement/production/stopOrResume.html";
	public static String PRODUCTION_DELETE_SCHEDULE = "/releaseManagement/production/deleteSchedule.html";
	public static String PRODUCTION_STOP = "/releaseManagement/production/stopSchedule.html";
	public static String PRODUCTION_RESUME = "/releaseManagement/production/resumeSchedule.html";

	public static String FILTER_LIST_RELEASE_MANAGEMENT_PAGE = "/releaseManagement/filterList/releaseManagementPage.html";
	public static String FILTER_LIST_LIST_OVERRIDE = "/releaseManagement/filterList/listOverride.html";
	public static String FILTER_LIST_FIND_DEVICE_LIST = "/releaseManagement/filterList/findDeviceList.html";
	public static String FILTER_LIST_FIND_FSA_NO = "/releaseManagement/filterList/findFsaNo.html";
	public static String FILTER_LIST_IS_FSA_EFFECTIVE = "/releaseManagement/filterList/isFsaEffective.html";
	public static String FILTER_LIST_NEW_OVERRIDE = "/releaseManagement/filterList/newOverride.html";
	public static String FILTER_LIST_FIND_OVERRIDE = "/releaseManagement/filterList/findOverride.html";
	public static String FILTER_LIST_UPDATE_OVERRIDE = "/releaseManagement/filterList/updateOverride.html";
	public static String FILTER_LIST_SET_OVERRIDE_TO_APPROVED = "/releaseManagement/filterList/setOverrideToApproved.html";
	public static String FILTER_LIST_READY_FOR_APPROVAL = "/releaseManagement/filterList/readyForApproval.html";
	public static String FILTER_LIST_BATCH_READY_FOR_APPROVAL = "/releaseManagement/filterList/batchReadyForApproval.html";
	public static String FILTER_LIST_APPROVE_OR_REJECT_PAGE = "/releaseManagement/filterList/approveOrRejectPage.html";
	public static String FILTER_LIST_APPROVE_OR_REJECT = "/releaseManagement/filterList/approveOrReject.html";
	public static String FILTER_LIST_DELETE_OVERRIDE = "/releaseManagement/filterList/deleteOverride.html";
	public static String FILTER_LIST_UPLOAD_FSA_FILE = "/releaseManagement/filterList/uploadFsaFile.html";
	public static String FILTER_LIST_DOWNLOAD_FSA_FILE = "/releaseManagement/filterList/downloadFsaFile.html";
	public static String FILTER_LIST_CHECK_IF_HAVE_OTHER_OVERRIDE_TO_CANCEL_BY_TESTERS = "/releaseManagement/filterList/checkIfHaveOtherOverrideToCancelByTesters.html";
	public static String FILTER_LIST_CANCEL_OVERRIDE = "/releaseManagement/filterList/cancelOverride.html";

	public static String CHECK_UPGRADE_DETAILS_MANAGEMENT_PAGE = "/releaseManagement/checkUpgradeDetails/goToCheckUpgradeDetailsPage.html";
	public static String CHECK_UPGRADE_DETAILS_LIST = "/releaseManagement/checkUpgradeDetails/listUpgradeDetails.html";
	public static String CHECK_UPGRADE_DETAILS_CHECK_IS_AN_ACTIVE_FSA = "/releaseManagement/checkUpgradeDetails/checkIsAnActiveFSA.html";

	public static String TEST_APACHE_URL = "/test_apache.html";
	public static String TEST_JBOSS_URL = "/test_jboss.html";
	public static String TEST_DATABASE = "/test_database.html";
	public static String TEST_DB = "/testDB.html";
	
	public static String DEFAULT_PASSWORD = "admin";
	
	public static String PROXY = CustomizedPropertyConfigurer.getContextProperty("api.proxy");

}
