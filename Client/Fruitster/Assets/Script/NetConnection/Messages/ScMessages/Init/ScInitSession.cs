using System;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScInitSession : ScMessage
    {
        // public String sessionId;
        public override void OnMessage(Session session)
        {
            // session.OnConnect(sessionId);
            session.OnConnect("ahihi");
        }
    }
}