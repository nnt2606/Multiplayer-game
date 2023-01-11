using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsRegister : CsMessage
    {
        public string userName;
        public string password;
        public string retypePassword;

        public CsRegister(string userName, string password, string retypePassword)
        {
            this.userName = userName;
            this.password = password;
            this.retypePassword = retypePassword;
            
            UserProperties.MainPlayer.userName = userName;
        }
    }
}