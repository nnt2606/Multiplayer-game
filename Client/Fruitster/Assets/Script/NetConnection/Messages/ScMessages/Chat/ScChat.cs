using System;
using System.Net;
using Script.Model;
using UnityEngine;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScChat : ScMessage
    {
        public string userID;
        public string message;
        
        public ScChat(string userID, string message)
        {
            this.userID = userID;
            this.message = message;
        }

        public override void OnMessage(Session session)
        {
            RoomSceneScript.Instance.RemoteChat(userID, message);
        }
    }
}