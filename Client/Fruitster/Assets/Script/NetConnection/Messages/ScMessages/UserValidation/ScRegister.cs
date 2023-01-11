using System;
using System.Net;
using Script.Messages.ScMessages;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class ScRegister : ScMessage
    {
        
        public HttpStatusCode statusCode;
        public string uid;
        
        public override void OnMessage(Session session)
        {
            if (statusCode==HttpStatusCode.OK)
            {
                UserProperties.MainPlayer.userID = uid;
            }
            else
            {
                UserProperties.RegisterFailed = true;
            }
        }
    }
}