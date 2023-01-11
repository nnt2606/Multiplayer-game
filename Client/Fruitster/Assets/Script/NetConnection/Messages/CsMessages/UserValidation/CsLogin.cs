using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsLogin : CsMessage
    {
        public string userName;
        public string password;

        public CsLogin(string userName, string password)
        {
            this.userName = userName;
            this.password = password;
            UserProperties.MainPlayer.userName = userName;
        }
    }
}