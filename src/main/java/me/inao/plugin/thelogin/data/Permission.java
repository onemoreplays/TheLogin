package me.inao.plugin.thelogin.data;

public enum Permission {
    COMMAND_HELP("thelogin.command.help");
    private final String permission;
    Permission(String value){
        this.permission = value;
    }

    public String get(){
        return this.permission;
    }
}
