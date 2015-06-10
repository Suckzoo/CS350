package safehome;

/**
 * Created by suckzoo on 2015-06-06.
 */
public class User {
    private String ID = "team2";
    private String PW = "12345678";
    private String controlPW = "0987";

    public boolean loginInterface(String inputID, String inputPW)
    {
        if(inputID.equals(ID) && inputPW.equals(PW))
        {
            return true;
        }
        return false;
    }
    public boolean loginControlPanel(String inputControlPW)
    {
        if(inputControlPW.equals(controlPW))
        {
            return true;
        }
        return false;
    }
    public boolean changeInterfacePassword(String originalPassword, String newPassword)
    {
        if(originalPassword.equals(PW) && newPassword.length()>=8)
        {
            PW = new String(newPassword);
            return true;
        }
        return false;
    }
    public boolean changeControlPanelPassword(String originalPassword, String newPassword)
    {
        int i;
        if(newPassword.length()!=4) return false;
        for(i=0;i<4;i++)
        {
            if(!('0'<=newPassword.charAt(i) && newPassword.charAt(i)<='9'))
            {
                return false;
            }
        }
        if(originalPassword.equals(controlPW))
        {
            controlPW = new String(newPassword);
            return true;
        }
        return false;
    }
}
