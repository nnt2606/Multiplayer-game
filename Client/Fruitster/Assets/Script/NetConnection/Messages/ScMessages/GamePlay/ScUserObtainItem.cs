using System;
using System.Net;
using Script.Messages.ScMessages;

namespace Script.Messages.CsMessages.GamePlay
{
    [Serializable]
    public class ScUserObtainItem : ScMessage
    {
        public HttpStatusCode statusCode;
        public string userID;
        public string itemID;
        public int addPoint;
        public override void OnMessage(Session session)
        {
            if (statusCode == HttpStatusCode.OK)
            {
                PlaySceneScript.Instance.AddScore(userID, addPoint);
                PlaySceneScript.Instance.DestroyById(itemID);
            }
        }
    }
}