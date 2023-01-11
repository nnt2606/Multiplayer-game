using System;
using System.Net;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScLogin : ScMessage
    {
        public HttpStatusCode statusCode;
        public string userID;
        
        public override void OnMessage(Session session)
        {
            if (statusCode==HttpStatusCode.OK)
            {
                UserProperties.MainPlayer.userID = userID;
            }
            else
            {
                UserProperties.LoginFailed = true;
            }
        }
    }
}