using System;
using System.Net;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScCreateRoom: ScMessage
    {
        
        public HttpStatusCode statusCode;
        public string roomID;
        
        public override void OnMessage(Session session)
        {
            if (statusCode == HttpStatusCode.OK)
            {
                UserProperties.UserRoom.RoomID = roomID;
                SingletonDontDestroy.Instance.DoAction(() =>SceneManager.LoadScene("RoomScene"));
            }
            else
            {
                Debug.Log(statusCode);
            }
        }
    }
}