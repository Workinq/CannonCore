package kr.kieran.cannoncore.utils.enums;

public enum CommandType {

    CORE("kr/kieran/cannoncore", "permission", "no-permission", ConfigType.MAIN_CONFIG),
    TNTSOUND("tntsound", "permission", "no-permission", ConfigType.TNTSOUND_CONFIG),
    XRAY("xray", "permission", "no-permission", ConfigType.XRAY_CONFIG),
    BUTTON("button", "button-permission", "no-permission", ConfigType.REMOTEBUTTON_CONFIG),
    FIRE("fire", "fire-permission", "no-permission", ConfigType.REMOTEBUTTON_CONFIG),
    TICKCOUNTER("tickcounter", "permission", "no-permission", ConfigType.TICKCOUNTER_CONFIG);

    private String name;
    private String permissionPath;
    private String noPermissionMsgPath;
    private ConfigType configType;

    CommandType(String name, String permissionPath, String noPermissionMsgPath, ConfigType configType) {
        this.name = name;
        this.permissionPath = permissionPath;
        this.noPermissionMsgPath = noPermissionMsgPath;
        this.configType = configType;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public String getName() {
        return name;
    }

    public String getPermissionPath() {
        return permissionPath;
    }

    public String getNoPermissionMsgPath() {
        return noPermissionMsgPath;
    }

}
